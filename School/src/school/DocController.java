/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package school;

import Modelos.ConexionSQL;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javax.swing.JOptionPane;

/**
 *
 * @author abarc
 */
public class DocController implements Initializable {
    
    @FXML
    private Label label;
    @FXML
    private Button button;
    @FXML
    private Button btnRegistro;
    @FXML
    private PasswordField txtPasword;
    @FXML
    private TextField txtUser;
    
    ConexionSQL conectar = new ConexionSQL();
    Connection con;
    
    CambioVistas cambiovista = new CambioVistas();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void Entrar(MouseEvent event) {
        if (!"".equals(txtUser.getText())) {
            if (!"".equals(txtPasword.getText())) {
                comprobarUserAndPaswor(event);
            }else
                JOptionPane.showMessageDialog(null,"No as ingresado una contraseña");
        }else{
            JOptionPane.showMessageDialog(null,"No as ingresado un usuario");
        }
    }

    @FXML
    private void Registro(MouseEvent event) {
        cambiovista.cambiarScena( event,  "Registro.fxml");
    }

    private void comprobarUserAndPaswor(MouseEvent event) {
        con = conectar.getConexion();
       try {
            String usuario = txtUser.getText();
            String pasword = txtPasword.getText();
            String SQL = "select COUNT(id) AS I from usuarios where nombreUsuario = '"+usuario+"' and pasword = '"+pasword+"'";
            Statement rt = con.createStatement();
            ResultSet rs = rt.executeQuery(SQL);
            while(rs.next()){
                if (rs.getString("I").equals("1")) {
                    cambiovista.cambiarScena(event,"VistaPrincipal.fxml");
                }else{
                    JOptionPane.showMessageDialog(null, "Error nombre de usuario o contaseña, incorrecto");
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al ejcutar la consutal"+ex.getMessage());
        }
    }
    
}
