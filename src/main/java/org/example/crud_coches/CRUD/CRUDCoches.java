package org.example.crud_coches.CRUD;

import com.google.gson.Gson;
import org.bson.Document;
import org.example.crud_coches.Controller.HelloController;
import org.example.crud_coches.domain.Coche;


public class CRUDCoches {

    public static void insertarCoche(Coche coche) {
        /*Para insertar un objeto en MongoDB debemos convertirlo a json mediante Gson
        * Parsearlo a documento, puesto que solo podemos insertar documentos en MongoDB
        * y para isertar el documento necesitamos una colección*/
        Gson gson = new Gson();
        String json = gson.toJson(coche);
        Document doc = Document.parse(json);
        HelloController.collection.insertOne(doc);
    }
}
