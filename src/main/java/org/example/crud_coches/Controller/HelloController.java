package org.example.crud_coches.Controller;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.bson.Document;
import org.example.crud_coches.CRUD.CRUDCoches;
import org.example.crud_coches.Clases.Conexion;
import org.example.crud_coches.domain.Coche;

import java.net.URL;
import java.util.ResourceBundle;


public class HelloController implements Initializable {
    //Creamos MongoCollection para poder insertar documentos
    public static MongoCollection<Document> collection=null;
    MongoClient con;
    String[] listaTipos = {"Gasolina","Diesel","Híbrido","Eléctrico"};

    @FXML
    private Button buttonBuscar;

    @FXML
    private Button buttonCancelar;

    @FXML
    private Button buttonEliminar;

    @FXML
    private Button buttonGuardar;

    @FXML
    private Button buttonModificar;

    @FXML
    private Button buttonNuevo;

    @FXML
    private ComboBox<String> cbTipo;

    @FXML
    private TableView<String> tableCoches;

    @FXML
    private TextField txtFieldMarca;

    @FXML
    private TextField txtFieldMatricula;

    @FXML
    private TextField txtFieldModelo;

    public void onButtonBuscarClick() {
    }

    public void onButtonEliminarClick() {
    }

    public void onButtonModificarClick() {
    }

    public void onButtonCancelarClick() {
    }

    public void onButtonGuardarClick() {
    }

    public void onButtonNuevoClick() {
        Coche coche = new Coche(txtFieldMatricula.getText(),txtFieldMarca.getText(),txtFieldModelo.getText(),cbTipo.getSelectionModel().getSelectedItem());
        CRUDCoches.insertarCoche(coche);
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
    }
}
