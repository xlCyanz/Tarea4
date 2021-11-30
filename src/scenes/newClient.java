package scenes;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import utils.ControllerStage;
import utils.Form;

public class newClient {
    private ControllerStage controllerStage;

    public newClient(ControllerStage controller) {
        this.controllerStage = controller;
    }

    /**
     * Escena de agregar nuevo cliente.
     * 
     * @return {@code Scene}
     */
    public Scene Scene() {
        controllerStage.changeTitle("Panel de control");

        VBox container = new VBox();
        container.getStyleClass().add("container");
        container.setPadding(new Insets(20, 20, 20, 20));
        container.setSpacing(10);
        container.setPrefSize(500, 550);

        Text title = new Text("Panel de control: Nuevo cliente");
        title.getStyleClass().addAll("title-login", "text");

        // Form
        Form form = new Form();

        Label labelFirstname = new Label("Nombre");
        labelFirstname.getStyleClass().addAll("label", "text");

        TextField inputFirstname = new TextField();
        inputFirstname.getStyleClass().add("input");

        Label labelLastname = new Label("Apellido");
        labelLastname.getStyleClass().addAll("label", "text");

        TextField inputLastname = new TextField();
        inputLastname.getStyleClass().add("input");

        Label labelUsername = new Label("Nombre de usuario");
        labelUsername.getStyleClass().addAll("label", "text");

        TextField inputUsername = new TextField();
        inputUsername.getStyleClass().add("input");

        Label labelEmail = new Label("Correo Electronico");
        labelEmail.getStyleClass().addAll("label", "text");

        TextField inputEmail = new TextField();
        inputEmail.getStyleClass().add("input");

        Label labelPhoneNumer = new Label("Numero de telefono");
        labelPhoneNumer.getStyleClass().addAll("label", "text");

        TextField inputPhoneNumber = new TextField();
        inputPhoneNumber.getStyleClass().add("input");

        Button submit = new Button("Agregar nuevo cliente");
        submit.setMinSize(Button.USE_PREF_SIZE, Button.USE_PREF_SIZE);

        Button goBack = new Button("Volver atras");
        goBack.setMinSize(Button.USE_PREF_SIZE, Button.USE_PREF_SIZE);

        HBox buttons = new HBox(10, submit, goBack);

        Text error = new Text();
        error.getStyleClass().addAll("text");
        error.setFill(Color.RED);

        container.getChildren().addAll(title, labelFirstname, inputFirstname, labelLastname, inputLastname, labelUsername, inputUsername, labelEmail, inputEmail, labelPhoneNumer, inputPhoneNumber, buttons);

        Scene newClientScene = new Scene(container);
        newClientScene.getStylesheets().add("./css/index.css");

        goBack.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                changeToDashboardScene();
            }
        });

        submit.setOnAction(new EventHandler <ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                container.getChildren().remove(error);

                String firstname = inputFirstname.getText();
                String lastname = inputLastname.getText();
                String username = inputUsername.getText();
                String email = inputEmail.getText();
                String phone_number = inputPhoneNumber.getText();

                Boolean isEmailCorrect = form.checkEmail(email);
                Boolean isPhoneNumberCorrect = form.checkPhoneNumber(phone_number);

                if (firstname.isEmpty()) {
                    error.setText("Error con el nombre");
                    container.getChildren().add(3, error);
                }
                else if (lastname.isEmpty()) {
                    error.setText("Error con el apellido");
                    container.getChildren().add(5, error);
                }
                else if (username.isEmpty()) {
                    error.setText("Error con el nombre de usuario");
                    container.getChildren().add(7, error);
                }
                else if (email.isEmpty() || !isEmailCorrect) {
                    error.setText("Error con el correo electronico");
                    container.getChildren().add(9, error);
                }
                else if (phone_number.isEmpty() || !isPhoneNumberCorrect) {
                    error.setText("Error con el numero de telefono");
                    container.getChildren().add(11, error);
                } else {
                    Boolean registerResponse = form.Register(firstname, lastname, username, email, phone_number);
                    
                    if (registerResponse) {
                        changeToDashboardScene();
                    } else {
                        error.setText("Hubo un error en el registro, comprueba cambiando tu usuario, correo o telefono.");
                        container.getChildren().add(11, error);
                    }
                }
            }
        });

        return newClientScene;
    }

    public void changeToDashboardScene() {
        Scene dashboardScene = new Dashboard(controllerStage).Scene();
        controllerStage.changeScene(dashboardScene);
    }
}
