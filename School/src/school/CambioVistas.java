/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package school;

import java.io.IOException;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javax.swing.JOptionPane;

/**
 *
 * @author abarc
 */
public class CambioVistas {
    public void cambiarScena(MouseEvent event, String pathVista) {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource(pathVista));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setOnCloseRequest((WindowEvent e) -> {
                Platform.exit();
                System.exit(0);
            });
            ((Node) (event.getSource())).getScene().getWindow().hide();
            stage.show();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }
}
