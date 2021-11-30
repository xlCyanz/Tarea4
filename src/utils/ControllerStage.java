package utils;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class ControllerStage {
    private Stage stage;

    /**
     * Asigna un stage al controlador
     * 
     * @param stage stage
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Obten el stage que usa el controlador
     * 
     * @return {@code stage}
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * Cambia la escena del stage.
     * 
     * @param scene Escena
     */
    public void changeScene(Scene scene) {
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Cambia el titulo del stage
     * 
     * @param title Titulo
     */
    public void changeTitle(String title) {
        stage.setTitle(title);
    }
}
