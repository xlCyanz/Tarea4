import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import scenes.Login;
import utils.ControllerStage;

public class App extends Application {
    ControllerStage controllerStage = new ControllerStage();

    @Override
    public void start(Stage stage) {
        controllerStage.setStage(stage);
        controllerStage.getStage().getIcons().add(new Image("./img/icon.png"));

        Scene loginScene = new Login(controllerStage).Scene();
        controllerStage.changeScene(loginScene);
    }

    public static void main(String[] args) {
        launch(args);
    }
}