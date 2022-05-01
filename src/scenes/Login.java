package scenes;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import utils.ControllerStage;
import utils.Form;

public class Login {
    private ControllerStage controllerStage;

    public Login(ControllerStage controller) {
        this.controllerStage = controller;
    }

    /**
     * Escena iniciar sesion.
     * 
     * @return {@code Scene}
     */
    public Scene Scene() {
        controllerStage.changeTitle("Inicio");

        VBox container = new VBox();
        container.getStyleClass().add("container");
        container.setPadding(new Insets(5, 20, 20, 20));
        container.setSpacing(10);
        container.setPrefSize(340, 350);
        
        Text title = new Text("Inicio de Sesion.");
        title.getStyleClass().addAll("title-login", "text");

        // Form
        Form form = new Form();

        Label labelUsername = new Label("Usuario");
        labelUsername.getStyleClass().addAll("label", "text");

        TextField inputUsername = new TextField();
        inputUsername.getStyleClass().add("input");

        Label labelPassword = new Label("Contraseña");
        labelPassword.getStyleClass().addAll("label", "text");

        PasswordField inputPassword = new PasswordField();
        inputPassword.getStyleClass().add("input");

        Button submit = new Button("Iniciar Sesion");
        submit.setMaxWidth(Double.MAX_VALUE);
        submit.getStyleClass().setAll("button");

        Button register = new Button("Registrarme");
        register.setMaxWidth(Double.MAX_VALUE);
        register.getStyleClass().setAll("button");  

        container.getChildren().addAll(title, labelUsername, inputUsername, labelPassword, inputPassword, submit, register);

        Text error = new Text();
        error.setFill(Color.RED);
        error.getStyleClass().addAll("text");

        Scene LoginScene = new Scene(container);
        LoginScene.getStylesheets().addAll("./css/index.css");

        submit.setOnAction(new EventHandler <ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                container.getChildren().remove(error);
                String username = inputUsername.getText();
                String password = inputPassword.getText();

                if (username.isEmpty()) {
                    error.setText("Error con el nombre de usuario.");
                    container.getChildren().add(3, error);
                } else if (password.isEmpty()) {
                    error.setText("Error con la contraseña del usuario.");
                    container.getChildren().add(5, error);
                } else {
                    Boolean loginResponse = form.Login(username, password);
                    
                    if(loginResponse) {
                        Scene dahboardScene = new Dashboard(controllerStage).Scene();
                        controllerStage.changeScene(dahboardScene);
                    } else {
                        error.setText("Hubo un error con tu nombre de usuario o contraseña.");
                        container.getChildren().add(5, error);
                    }
                }
            }
        });

        register.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                Scene registerScene = new Register(controllerStage).Scene();
                controllerStage.changeScene(registerScene);
            }
        });

        return LoginScene;
    }
}
