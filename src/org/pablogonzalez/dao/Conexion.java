package org.pablogonzalez.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;

public class Conexion {
    private static Conexion instancia;
    private Connection conexion;
    
    private static final String URL = "jdbc:mysql://localhost:3306/auth_system?useSSL=false&allowPublicKeyRetrieval=true";
    private static final String USER = "root";
    private static final String PASSWORD = "Admin";
    
    private Conexion(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            conexion = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connection exitosa a la DB");
        }catch(ClassNotFoundException error){
            System.err.println("Error: Driver de MySQL no encontrado");
            System.out.println(error.getException());
        }catch(SQLException error){
            System.err.println("Error: no fue posible conectarse a la DB");
            System.out.println(error.getMessage());
        }
    }
    
    public static Conexion getInstance(){
        if(instancia == null){
            instancia = new Conexion();
        }
        return instancia;
    }
    
    public Connection getConexion(){
        if(conexion == null){
            System.err.println("No se establecio la conecion, verifica la configuracion");
        }
        return conexion;
    }
}
