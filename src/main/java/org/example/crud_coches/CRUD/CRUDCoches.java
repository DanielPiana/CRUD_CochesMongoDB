package org.example.crud_coches.CRUD;

import com.google.gson.Gson;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.bson.Document;
import org.example.crud_coches.Controller.HelloController;
import org.example.crud_coches.domain.Coche;

import java.util.ArrayList;
import java.util.Objects;


public class CRUDCoches {

    public static void insertarCoche(Coche coche) {
        /*Para insertar un objeto en MongoDB debemos convertirlo a json mediante Gson
        * Parsearlo a documento, puesto que solo podemos insertar documentos en MongoDB
        * y para isertar el documento necesitamos una colección*/
        Gson gson = new Gson();
        //Convertimos el objeto a formato json(Deserializar)
        String json = gson.toJson(coche);
        //Parseamos el json a documento
        Document doc = Document.parse(json);
        //y lo insertamos en la base de datos con collection
        HelloController.collection.insertOne(doc);
    }
    public static void eliminarCoche(Coche coche) {
        //Usamos findOneAndDelete para buscar uno y borrarlo
        //Filters.eq son parámetros de busca, el primero es el atributo que buscamos y el segundo el valor
        HelloController.collection.findOneAndDelete(Filters.eq("matricula",coche.getMatricula()));
    }
    public static void modificarCoche(Coche coche) {
        //Para modificar coche tenemos que usar findOneAndUpdate, y un filtro de búsqueda combinado para poder cambiar
        //Mas de un atributo, Updates.set tiene el mismo tipo de atributos que Filters.eq (1º atributo y 2º valor)
        HelloController.collection.findOneAndUpdate(
                Filters.eq("matricula",coche.getMatricula()),
                Updates.combine(
                        Updates.set("marca",coche.getMarca()),
                        Updates.set("modelo",coche.getModelo()),
                        Updates.set("tipo",coche.getTipo())
                ));
        }
    public static ObservableList<Coche> listar() {
        //Para listar hay que conseguir todos los coches de la base de datos y convertilo a ObservableList para poder cargarlo en la tabla
        //Hacemos un cursor que va a contener documentos
        ObservableList<Coche> observableList=null;
        MongoCursor<Document> cursor = HelloController.collection.find().iterator();
        Gson gson = new Gson();
        //Hacemos un arraylist donde vamos a ir guardando los coches
        ArrayList<Coche> lista = new ArrayList<>();
        try { //Mientras cursor encuentre algo entrará al bucle
            while (cursor.hasNext()) {
                //Convertimos de json a objeto coche con gson
                Coche coche = gson.fromJson(cursor.next().toJson(), Coche.class);
                //y lo añadimos en la lista
                lista.add(coche);
            }
            //y lo convertimos a Observablelist
            observableList = FXCollections.observableList(lista);
        } catch (Exception e ) {
            System.out.println(e.getMessage());
        } finally {
            cursor.close();
        }
        return observableList;
    }

    public static boolean existe(Coche coche) {
        MongoCursor<Document> cursor = HelloController.collection.find().iterator();
        Gson gson = new Gson();
        try {
            while (cursor.hasNext()) {
                Coche coche2 = gson.fromJson(cursor.next().toJson(), Coche.class);
                if (Objects.equals(coche.getMatricula(), coche2.getMatricula())) {
                    return true;
                }
            }
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }finally {
            cursor.close();
        }
        return false;
    }
}