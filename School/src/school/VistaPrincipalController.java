/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package school;

import Modelos.ConexionSQL;
import Modelos.ModelTableView;
import com.mysql.jdbc.Connection;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author abarc
 */
public class VistaPrincipalController implements Initializable {

    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtApellido;
    @FXML
    private TextField txtCalificacion;
    @FXML
    private ComboBox<String> cBMateria;
    @FXML
    private ComboBox<String> cBEstatus;
    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnActualizar;
    @FXML
    private Button bnEliminar;
    @FXML
    private TextField txtBuqueda;
    @FXML
    private ComboBox<String> cBBusqueda;

    ConexionSQL conexion = new ConexionSQL();
    Connection con;

    @FXML
    private TableView<ModelTableView> tView;
    @FXML
    private TableColumn<ModelTableView, String> tCId;
    @FXML
    private TableColumn<ModelTableView, String> tcNombre;
    @FXML
    private TableColumn<ModelTableView, String> tCApellidos;
    @FXML
    private TableColumn<ModelTableView, String> tCMateria;
    @FXML
    private TableColumn<ModelTableView, String> tCCalificacion;
    @FXML
    private TableColumn<ModelTableView, String> tCEstatus;

    ObservableList<String> list1;
    ObservableList<String> list2;
    ObservableList<String> listCBoxBus;

    ObservableList<ModelTableView> obs = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        con = conexion.getConexion();
        mostrarRegistros();
        list1 = FXCollections.observableArrayList("Matematica", "Español", "Ingles", "Geografia");
        cBMateria.setItems(list1);
        list2 = FXCollections.observableArrayList("Aprovado", "Reprobado");
        cBEstatus.setItems(list2);
        listCBoxBus = FXCollections.observableArrayList("id", "Nombre", "Apellido", "Materia", "Calificacion", "Estatus");
        cBBusqueda.setItems(listCBoxBus);
    }

    @FXML
    private void Guardar(MouseEvent event) {
        insertarDatos();
        limpiarCeldas();
        obs.clear();
        mostrarRegistros();
    }

    public void limpiarCeldas() {
        txtNombre.setText("");
        txtApellido.setText("");
        txtCalificacion.setText("");
        cBMateria.setValue(null);
        cBEstatus.setValue(null);
    }

    private void insertarDatos() {

        try {
            String SQL = "insert into alumnos(nombre,Apellidos,materia,calificacion,estatus)"
                    + "values(?,?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(SQL);

            pst.setString(1, txtNombre.getText());
            pst.setString(2, txtApellido.getText());
            String cb = (String) cBMateria.getValue();
            pst.setString(3, cb);
            pst.setDouble(4, Double.parseDouble(txtCalificacion.getText()));
            pst.setString(5, (String) cBEstatus.getValue());
            pst.execute();

            JOptionPane.showMessageDialog(null, "Registro exitoso");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al realizar el registro \n" + ex.getMessage());
        }
    }

    public void mostrarRegistros() {
        String SQL = "select * from alumnos";
        cargarTableView( SQL);
    }

    @FXML
    private void actualizar(MouseEvent event) {
        acualizarDatos();
        limpiarCeldas();
        obs.clear();
        mostrarRegistros();
    }

    private void acualizarDatos() {
        try {
            String SQL = "update alumnos set nombre=?,Apellidos=?,materia=?,calificacion=?,estatus=?"
                    + "where idAlumnos=?";

            int filaSeleconada = tView.getSelectionModel().getSelectedIndex();

            PreparedStatement pst = con.prepareStatement(SQL);
            pst.setString(1, txtNombre.getText());
            pst.setString(2, txtApellido.getText());
            String cb = (String) cBMateria.getValue();
            pst.setString(3, cb);
            pst.setDouble(4, Double.parseDouble(txtCalificacion.getText()));
            pst.setString(5, (String) cBEstatus.getValue());
            pst.setString(6, (String) tView.getItems().get(filaSeleconada).getIdAlumnos());
            pst.execute();

            JOptionPane.showMessageDialog(null, "Registro actualizado exitoso");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al realizar la actualizacion \n" + ex.getMessage());
        }
    }

    @FXML
    private void eliminar(MouseEvent event) {
        eliminarDatos();
        limpiarCeldas();
        obs.clear();
        mostrarRegistros();
    }

    private void eliminarDatos() {
        int filaSeleconada = tView.getSelectionModel().getSelectedIndex();
        try {
            String SQL = "delete from alumnos "
                    + "where idAlumnos=" + (String) tView.getItems().get(filaSeleconada).getIdAlumnos();
            PreparedStatement pst = con.prepareStatement(SQL);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Datos correctamente eliminados");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al eliminar \n" + ex.getMessage());
        }
    }

    @FXML
    private void busqueda(KeyEvent event) {
        if (cBBusqueda.getValue() != null) {
            obs.clear();
            busquedaEnRegistros(txtBuqueda.getText());
        } else {
            JOptionPane.showMessageDialog(null, "Desbes de selecionar un valor para la busqueda");
        }
    }

    public void busquedaEnRegistros(String valor) {
        String valorBusq = cBBusqueda.getValue();
        switch (valorBusq) {
            case "id": {
                String SQL = "select * from alumnos where idAlumnos like '%" + valor + "%'";
                cargarTableView(SQL);
                break;
            }
            case "Nombre": {
                String SQL = "select * from alumnos where nombre like '%" + valor + "%'";
                cargarTableView(SQL);
                break;
            }
            case "Apellido": {
                String SQL = "select * from alumnos where Apellidos like '%" + valor + "%'";
                cargarTableView(SQL);
                break;
            }
            case "Materia": {
                String SQL = "select * from alumnos where materia like '%" + valor + "%'";
                cargarTableView(SQL);
                break;
            }
            case "Calificacion": {
                String SQL = "select * from alumnos where calificacion like '%" + valor + "%'";
                cargarTableView(SQL);
                break;
            }
            case "Estatus": {
                String SQL = "select * from alumnos where estatus like '%" + valor + "%'";
                cargarTableView(SQL);
                break;
            }
            default: {
                String SQL = "select * from usuarios";
                cargarTableView(SQL);
                break;
            }
        }
    }

    @FXML
    private void clickTable(MouseEvent event) {
        int filaSeleconada = tView.getSelectionModel().getSelectedIndex(); // Haci se jala el indiceque se esta selecionando
        tView.getItems().get(filaSeleconada).getNombre(); // De esta forma se tiene acceso a los valores almacenados en la fila
        txtNombre.setText(tView.getItems().get(filaSeleconada).getNombre());
        txtApellido.setText(tView.getItems().get(filaSeleconada).getApellidos());
        txtCalificacion.setText("" + tView.getItems().get(filaSeleconada).getCalificacion());
        cBEstatus.setValue(tView.getItems().get(filaSeleconada).getEstatus());
        cBMateria.setValue(tView.getItems().get(filaSeleconada).getMateria());
    }

    private void cargarTableView(String SQL) {
        try {
            Statement rt = con.createStatement();
            ResultSet rs = rt.executeQuery(SQL);
            while (rs.next()) {
                obs.add(new ModelTableView(rs.getString("idAlumnos"), rs.getString("nombre"), rs.getString("Apellidos"),
                        rs.getString("materia"), rs.getDouble("calificacion"), rs.getString("estatus")));
            }
            tCId.setCellValueFactory(new PropertyValueFactory<>("idAlumnos"));
            tcNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
            tCApellidos.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
            tCMateria.setCellValueFactory(new PropertyValueFactory<>("materia"));
            tCCalificacion.setCellValueFactory(new PropertyValueFactory<>("calificacion"));
            tCEstatus.setCellValueFactory(new PropertyValueFactory<>("estatus"));

            tView.setItems(obs);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al mostrar los registros xistentes  \n" + ex.getMessage());
        }
    }

}
