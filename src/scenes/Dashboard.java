package scenes;

import java.util.List;

import org.bson.Document;
import org.json.JSONException;
import org.json.JSONObject;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import utils.ControllerStage;
import utils.Database;
import utils.Client;

public class Dashboard {
    Database database = new Database("mongodb://localhost:27017", "tarea-4-java");

    private TableView<Client> table = new TableView<>();
    private ObservableList<Client> data = FXCollections.observableArrayList();
    private ControllerStage controllerStage;

    public Dashboard(ControllerStage controller) {
        this.controllerStage = controller;
    }

    /**
     * Escena del panel de control.
     * 
     * @return {@code Scene}
     */
    public Scene Scene() {
        controllerStage.changeTitle("Panel de control");

        VBox container = new VBox();
        container.getStyleClass().add("container");
        container.setPadding(new Insets(0, 20, 20, 20));
        container.setSpacing(10);
        container.setPrefSize(600, 400);

        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setEditable(true);

        fetchData();

        Text title = new Text("Panel de control: clientes.");
        title.getStyleClass().addAll("title-login", "text");

        TableColumn<Client, String> firstNameCol = new TableColumn<>("Nombre");
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        firstNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        firstNameCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Client,String>>() {
            @Override
            public void handle(CellEditEvent<Client, String> row) {
                database.updateOne("users", "firstname", row.getOldValue(), row.getNewValue());
                fetchData();
            }
        });

        table.getColumns().add(firstNameCol);

        TableColumn<Client, String> lastNameCol = new TableColumn<>("Apellido");
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        lastNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        lastNameCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Client,String>>() {
            @Override
            public void handle(CellEditEvent<Client, String> row) {
                database.updateOne("users", "lastname", row.getOldValue(), row.getNewValue());
                fetchData();
            }
        });

        table.getColumns().add(lastNameCol);

        TableColumn<Client, String> userNameCol = new TableColumn<>("Nombre de usuario");
        userNameCol.setCellValueFactory(new PropertyValueFactory<>("userName"));
        userNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        userNameCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Client,String>>() {
            @Override
            public void handle(CellEditEvent<Client, String> row) {
                database.updateOne("users", "username", row.getOldValue(), row.getNewValue());
                fetchData();
            }
        });

        table.getColumns().add(userNameCol);

        TableColumn<Client, String> emailCol = new TableColumn<>("Correo Electronico");
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        table.getColumns().add(emailCol);

        TableColumn<Client, String> phoneNumberCol = new TableColumn<>("Numero de telefono");
        phoneNumberCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        table.getColumns().add(phoneNumberCol);

        table.setItems(data);
        table.setFixedCellSize(25);
        table.prefHeightProperty().bind(Bindings.size(table.getItems()).multiply(table.getFixedCellSize()).add(45));

        Button newButton = new Button("Nuevo");
        newButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        newButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                Scene newClientScene = new newClient(controllerStage).Scene();
                controllerStage.changeScene(newClientScene);
            }
        });

        Button updateButton = new Button("Actualizar");
        updateButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        updateButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                fetchData();
            }
        });

        Button deleteButton = new Button("Eliminar");
        deleteButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        deleteButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                final String firstname = table.getSelectionModel().getSelectedItem().getFirstName();
                database.deleteOne("users", "firstname", firstname);
                fetchData();
            }
        });

        Button logoutButton = new Button("Cerrar sesion");
        logoutButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        logoutButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                Scene LoginScene = new Login(controllerStage).Scene();
                controllerStage.changeScene(LoginScene);
            };
        });

        final HBox boxButtons = new HBox(10, newButton, updateButton, deleteButton, logoutButton);
        boxButtons.setAlignment(Pos.CENTER);
        HBox.setHgrow(newButton, Priority.ALWAYS);
        HBox.setHgrow(updateButton, Priority.ALWAYS);
        HBox.setHgrow(deleteButton, Priority.ALWAYS);
        HBox.setHgrow(logoutButton, Priority.ALWAYS);

        container.getChildren().addAll(title, table, boxButtons);
  
        Scene dashboardScene = new Scene(container);
        dashboardScene.getStylesheets().add("./css/index.css");

        return dashboardScene;
    }

    private void fetchData() {
        data.clear();
        List<Document> hola = database.findAllDocument("users");

        for (Document document : hola) {
            try {
                JSONObject mainObject = new JSONObject(document.toJson().toString());

                final String first_name = mainObject.getString("firstname");
                final String last_name = mainObject.getString("lastname");
                final String user_name = mainObject.getString("username");
                final String email = mainObject.getString("email");
                final String phone_number = mainObject.getString("phone_number");

                data.add(new Client(first_name, last_name, user_name, email, phone_number));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        table.refresh();
    }
}
