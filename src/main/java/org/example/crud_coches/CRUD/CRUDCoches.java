package org.example.crud_coches.CRUD;

import com.google.gson.Gson;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
import org.example.crud_coches.Controller.HelloController;
import org.example.crud_coches.domain.Coche;

import java.util.ArrayList;


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
    public static void listar() {
        MongoCursor<Document> cursor = HelloController.collection.find().iterator();
        Gson gson = new Gson();
        ArrayList<Coche> lista = new ArrayList<>();
        try {
            while (cursor.hasNext()) {
                Coche coche = gson.fromJson(cursor.next().toJson(), Coche.class);
                lista.add(coche);
                //CONVERTIR OBSERVABLE LIST PARA CARGARLO EN LA TABLA
            }
        } catch (Exception e ) {
            System.out.println(e.getMessage());
        } finally {
            cursor.close();
        }
    }
}
