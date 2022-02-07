/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package school;

import Modelos.ConexionSQL;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author abarc
 */
public class RegisControllers implements Initializable {

    @FXML
    private TextField txtUserName;
    @FXML
    private TextField txtPasword;
    @FXML
    private TextField txtPaswordVerific;
    @FXML
    private Label lbCoincidenPasword;

    boolean paswordCorrecta = false;
    
    ConexionSQL conectar = new ConexionSQL();
    Connection con;
    
    CambioVistas cambiovista = new CambioVistas();

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void comprobarPasword(KeyEvent event) {
        String contra = txtPasword.getText();
        if (contra.equals(txtPaswordVerific.getText())) {
            lbCoincidenPasword.setText("Coinciden");
            paswordCorrecta = true;
        } else {
            lbCoincidenPasword.setText("No coinciden");
            paswordCorrecta = false;
        }

    }

    @FXML
    private void Registro(MouseEvent event) {
        String nombreUsuario = txtUserName.getText();
        if (paswordCorrecta) {
            if (!"".equals(nombreUsuario)) {
                insertarDatos();
                cambiovista.cambiarScena(event, "FXMLDocument.fxml");
            }else{
                JOptionPane.showMessageDialog(null, "El nombre de usuario no puede estar en blanco");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Verifique la contraseña, no coinciden");
        }
    }

    public void insertarDatos() {
        con = conectar.getConexion();
        String nombreUsuario = txtUserName.getText();
        String pasword = txtPasword.getText();
        String consulta = "insert into usuarios(pasword,nombreUsuario) values(?,?);";
        try {
            PreparedStatement pre = con.prepareStatement(consulta);
            
            pre.setString(1, pasword);          
            pre.setString(2,nombreUsuario);  
            pre.execute();
            
            JOptionPane.showMessageDialog(null, "Registro correctamente");

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al realizar el registro \n" + ex.getMessage());
        }
    }

}
