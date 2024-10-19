package org.example.crud_coches.Controller;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.bson.Document;
import org.example.crud_coches.CRUD.CRUDCoches;
import org.example.crud_coches.Clases.Conexion;
import org.example.crud_coches.Utilities.Alerts;
import org.example.crud_coches.domain.Coche;

import java.net.URL;
import java.util.ResourceBundle;

import static org.example.crud_coches.CRUD.CRUDCoches.eliminarCoche;
import static org.example.crud_coches.CRUD.CRUDCoches.listar;


public class HelloController implements Initializable {
    //Creamos MongoCollection para poder insertar documentos
    public static MongoCollection<Document> collection=null;

    MongoClient con;
    String[] listaTipos = {"Gasolina","Diesel","Híbrido","Eléctrico"};

    @FXML
    private TableColumn<Coche,String> columnaMatricula;
    @FXML
    private TableColumn<Coche,String> columnaMarca;
    @FXML
    private TableColumn<Coche,String> columnaModelo;
    @FXML
    private TableColumn<Coche,String> columnaTipo;
    @FXML
    private Button buttonLimpiar;
    @FXML
    private Button buttonEliminar;
    @FXML
    private Button buttonGuardar;
    @FXML
    private Button buttonModificar;
    @FXML
    private ComboBox<String> cbTipo;
    @FXML
    private TableView<Coche> tableCoches;
    @FXML
    private TextField txtFieldMarca;
    @FXML
    private TextField txtFieldMatricula;
    @FXML
    private TextField txtFieldModelo;


    public void onButtonEliminarClick() {
        Coche coche = tableCoches.getSelectionModel().getSelectedItem();
        eliminarCoche(coche);
        cargarTabla();
        setearTextFieldsVacios();
        Alerts.alertaGeneral("Coche eliminado correctamente","INFORMATION");
    }

    public void onButtonModificarClick() {
        /*Asumo que la matricula es primary key y no debería cambiar asi que creo un coche con la matricula del coche seleccionado
        * en la tabla y los datos a modificar, Marca,Modelo y Tipo.*/
        Coche cocheReferencia = tableCoches.getSelectionModel().getSelectedItem();
        Coche coche = new Coche(cocheReferencia.getMatricula(), txtFieldMarca.getText(),txtFieldModelo.getText(),cbTipo.getValue());
        CRUDCoches.modificarCoche(coche);
        cargarTabla();
        setearTextFieldsVacios();
        Alerts.alertaGeneral("Coche modificado correctamente","INFORMATION");
    }

    public void onButtonGuardarClick() {
        Coche coche = new Coche(txtFieldMatricula.getText(),txtFieldMarca.getText(),txtFieldModelo.getText(),cbTipo.getSelectionModel().getSelectedItem());
        CRUDCoches.insertarCoche(coche);
        cargarTabla();
        setearTextFieldsVacios();
        Alerts.alertaGeneral("Coche guardado correctamente","INFORMATION");
    }
    public void onButtonLimpiarClick() {
        setearTextFieldsVacios();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Inicializamos los tipos en el comboBox
        cbTipo.getItems().addAll(listaTipos);
        //Inicializamos la conexion
        con = Conexion.conectar();
        //Creamos la base de datos
        MongoDatabase database = con.getDatabase("Taller");
        //Creamos la coleccion estática para trabajar con ella en CRUDCoches y la asignamos a la variable collection
        database.createCollection("coches");
        collection = database.getCollection("coches");
        cargarTabla();
    }
    public void cargarTabla() {
        //Cargamos los datos en la tabla
        columnaMatricula.setCellValueFactory(new PropertyValueFactory<>("matricula"));
        columnaMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        columnaModelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        columnaTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        ObservableList<Coche> observableList = listar();
        tableCoches.setItems(observableList);
    }

    public void onTableClick() {
        //Metodo para setear en los textFields y el comboBox lo seleccionado en la tabla
        Coche coche = tableCoches.getSelectionModel().getSelectedItem();
        txtFieldMarca.setText(coche.getMarca());
        txtFieldMatricula.setText(coche.getMatricula());
        txtFieldModelo.setText(coche.getModelo());
        cbTipo.setValue(coche.getTipo());
    }
    public void setearTextFieldsVacios() {
        //Metodo para limpiar los textFields y el comboBox
        txtFieldModelo.setText("");
        txtFieldMatricula.setText("");
        txtFieldMarca.setText("");
        cbTipo.setValue("");
    }
}