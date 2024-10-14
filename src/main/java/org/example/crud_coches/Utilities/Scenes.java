package org.example.crud_coches.Utilities;

import com.sun.tools.javac.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class Scenes {
    public static void mostrarEscena (Button boton, String fxml){
        try {
            //Cargamos fxml
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxml));
            Parent root = fxmlLoader.load();
            //Obtenemos el controlador del fxml cargado
            Object controller = fxmlLoader.getController();
            //Creamos nueva escena con el root
            Scene scene = new Scene(root);
            //Obtenemos el stage del botón que ha causado el evento
            Stage stage = (Stage) boton.getScene().getWindow();
            //Seteamos el stage con la escena actual.
            stage.setScene(scene);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
