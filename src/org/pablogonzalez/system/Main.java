package org.pablogonzalez.system;

import java.io.InputStream;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.pablogonzalez.controllers.LoginController;
import org.pablogonzalez.controllers.NewUserController;
import org.pablogonzalez.controllers.RecuperarContraseñaController;

public class Main extends Application{
    private final String PAQUETE_VISTA = "/org/pablogonzalez/views/";
    private Stage escenarioPrincipal;
    private Scene escena;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage escenarioPrincipal) throws Exception {
        this.escenarioPrincipal = escenarioPrincipal;
        this.escenarioPrincipal.setTitle("AuthSystem Fundamentos 2025");
        login();
        escenarioPrincipal.show();
    }
    
    public Initializable cambiarEscena(String fxml, int ancho, int alto) throws Exception{
        Initializable resultado;
        FXMLLoader cargadorFXML = new FXMLLoader();
        InputStream archivo = Main.class.getResourceAsStream(PAQUETE_VISTA + fxml);
        
        if (archivo == null) {
            throw new Exception("No se encontró el archivo FXML en la ruta: " + PAQUETE_VISTA + fxml);
        }
        cargadorFXML.setBuilderFactory(new JavaFXBuilderFactory());
        cargadorFXML.setLocation(Main.class.getResource(PAQUETE_VISTA + fxml));
        escena = new Scene((AnchorPane) cargadorFXML.load(archivo), ancho, alto);
        escenarioPrincipal.setScene(escena);
        escenarioPrincipal.sizeToScene();
        resultado = (Initializable)cargadorFXML.getController();
        return resultado;
    }
    
    public void login(){
        try{
            System.out.println("Intentando cargar el archivo FXML...");
            LoginController login = (LoginController) cambiarEscena("LoginView.fxml", 626, 388);
            login.setEscenarioPrincipal(this);
            System.out.println("Carga exitosa.");
        }catch(Exception error){
            System.out.println("Ocurrió un error:");
            error.printStackTrace();
        }
    }
    
    public void newUser(){
        try{
            System.out.println("Intentando cargar el archivo FXML...");
            NewUserController newUser = (NewUserController) cambiarEscena("NewUserView.fxml",443,470);
            newUser.setEscenarioPrincipal(this);
        }catch(Exception error){
            System.out.println("Ocurrio un error");
            error.printStackTrace();
        }
    }
    
    public void recuperarContraseña(){
        try{
            System.out.println("Intentando cargar el archivo FXML...");
            RecuperarContraseñaController rcc = (RecuperarContraseñaController) cambiarEscena("RecuperarContraseñaView.fxml",600,400);
            rcc.setEscenarioPrincipal(this);
        }catch(Exception error){
            System.out.println("Ocurrio un error");
            error.printStackTrace();
        }
    }
}
