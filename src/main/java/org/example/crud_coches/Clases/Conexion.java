package org.example.crud_coches.Clases;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

public class Conexion {
    private static MongoClient con;
    public static MongoClient conectar(){

        Properties properties= new Properties();
        String host="";
        String port="";
        String name="";
        String username="";
        String password="";
        try {
            properties.load(new FileInputStream("src/main/resources/org/example/crud_coches/Configuration/database.properties"));
            host=String.valueOf(properties.get("host"));
            port=String.valueOf(properties.get("port"));
            name=String.valueOf(properties.get("name"));
            username=String.valueOf(properties.get("username"));
            password=String.valueOf(properties.get("password"));
            System.out.println(host+"  "+port+"  "+name+"  "+username+"  "+password);
            con = new MongoClient(new MongoClientURI("mongodb://" + username + ":" + password + "@" + host + ":" + port + "/?authSource=admin"));
            return con;

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            return null;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            System.out.println("Conexion Fallida");
            System.out.println(e);
            return null;
        }
    }
    public static void desconectar() throws SQLException {
        con.close();
    }
}
