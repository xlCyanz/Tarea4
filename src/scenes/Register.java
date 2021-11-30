package scenes;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import utils.ControllerStage;
import utils.Form;

public class Register {
    private ControllerStage controllerStage;

    public Register(ControllerStage controller) {
        this.controllerStage = controller;
    }

    /**
     * Clase de la escena para registrarse.
     * 
     * @param controllerStage Manejador global del stage.
     * 
     * @return {@code Scene}
     */
    public Scene Scene() {
        controllerStage.changeTitle("Registro");

        VBox container = new VBox();
        container.getStyleClass().add("container");
        container.setPadding(new Insets(20, 20, 20, 20));
        container.setSpacing(10);
        container.setPrefSize(500, 520);

        Text title = new Text("Bienvenido al registro");
        title.getStyleClass().addAll("title-login", "text");

        // Form
        Form form = new Form();

        VBox box = new VBox();
        Label labelFirstname = new Label("Nombre");
        labelFirstname.getStyleClass().addAll("label", "text");

        TextField inputFirstname = new TextField();
        inputFirstname.getStyleClass().add("input");

        box.getChildren().addAll(labelFirstname, inputFirstname);
        box.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        VBox box1 = new VBox();
        Label labelLastname = new Label("Apellido");
        labelLastname.getStyleClass().addAll("label", "text");

        TextField inputLastname = new TextField();
        inputLastname.getStyleClass().add("input");

        box1.getChildren().addAll(labelLastname, inputLastname);
        box1.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        HBox box2 = new HBox(10, box, box1);

        box2.setAlignment(Pos.CENTER);
        HBox.setHgrow(box, Priority.ALWAYS);
        HBox.setHgrow(box1, Priority.ALWAYS);

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


        VBox box3 = new VBox();
        Label labelPassword = new Label("Contraseña");
        labelPassword.getStyleClass().addAll("label", "text");

        PasswordField inputPassword = new PasswordField();
        inputPassword.getStyleClass().add("input");
        box3.getChildren().addAll(labelPassword, inputPassword);
        box3.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        VBox box4 = new VBox();
        Label labelPasswordConfirm = new Label("Confirmar contraseña");
        labelPasswordConfirm.getStyleClass().addAll("label", "text");

        PasswordField inputPasswordConfirm = new PasswordField();
        inputPasswordConfirm.getStyleClass().add("input");
        box4.getChildren().addAll(labelPasswordConfirm, inputPasswordConfirm);
        box4.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        HBox box5 = new HBox(10, box3, box4);
        box5.setAlignment(Pos.CENTER);
        HBox.setHgrow(box3, Priority.ALWAYS);
        HBox.setHgrow(box4, Priority.ALWAYS);

        Button submit = new Button("Registrarme");
        submit.setMinSize(Button.USE_PREF_SIZE, Button.USE_PREF_SIZE);

        Button goBack = new Button("Volver atras");
        goBack.setMinSize(Button.USE_PREF_SIZE, Button.USE_PREF_SIZE);

        HBox buttons = new HBox(10, submit, goBack);
        
        container.getChildren().addAll(title, box2, labelUsername, inputUsername, labelEmail, inputEmail, labelPhoneNumer, inputPhoneNumber, box5, buttons);

        Text error = new Text();
        error.setFill(Color.RED);
        error.getStyleClass().addAll("text");

        Scene RegisterScene = new Scene(container);
        RegisterScene.getStylesheets().add("./css/index.css");

        goBack.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                changeToLoginScene();
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
                String password = inputPassword.getText();
                String passwordConfirm = inputPasswordConfirm.getText();

                Boolean isEmailCorrect = form.checkEmail(email);
                Boolean isPhoneNumberCorrect = form.checkPhoneNumber(phone_number);
                Boolean isPasswordEqual = passwordConfirm.equals(password);

                if (firstname.isEmpty()) {
                    error.setText("Comprueba el nombre introducido.");
                    box.getChildren().add(2, error);
                }
                else if (lastname.isEmpty()) {
                    error.setText("Comprueba el apellido introducido.");
                    box1.getChildren().add(2, error);
                }
                else if (username.isEmpty()) {
                    error.setText("Comprueba el nombre de usuario introducido.");
                    container.getChildren().add(4, error);
                }
                else if (email.isEmpty() || !isEmailCorrect) {
                    error.setText("Comprueba el correo electronico introducido.");
                    container.getChildren().add(6, error);
                }
                else if (phone_number.isEmpty() || !isPhoneNumberCorrect) {
                    error.setText("Comprueba el numero de telefono introducido.");
                    container.getChildren().add(8, error);
                }
                else if (password.isEmpty()) {
                    error.setText("Comprueba la contraseña introducido.");
                    box3.getChildren().add(2, error);
                }
                else if (passwordConfirm.isEmpty() || !isPasswordEqual) {
                    error.setText("Error confirmando la contraseña.");
                    box4.getChildren().add(2, error);
                } else {
                    Boolean registerResponse = form.Register(firstname, lastname, username, email, phone_number, password);
                    if(registerResponse) {
                        changeToLoginScene();
                    } else {
                        error.setText("Hubo un error en el registro, comprueba cambiando tu usuario, correo o telefono.");
                        container.getChildren().add(12, error);
                    }
                }
            }
        });

        return RegisterScene;
    }

    private void changeToLoginScene() {
        Scene LoginScene = new Login(controllerStage).Scene();
        controllerStage.changeScene(LoginScene);
    }
}
