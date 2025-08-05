package org.pablogonzalez.controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.pablogonzalez.dao.Conexion;
import org.pablogonzalez.system.Main;

public class RecuperarContraseñaController implements Initializable{
    private Main escenarioPrincipal;
    private String codigoGenerado = generarCodigoAleatorio(2);
    
    @FXML
    private TextField txtEmail, txtCodigo;
    
    @FXML
    private PasswordField txtPassword;
    
    @FXML
    private Button btnCambiarContraseña, btnCancelar, btnGenerarCodigo;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
    
    public Main getEscenarioPrincipal(){
        return escenarioPrincipal;
    }
    
    public void setEscenarioPrincipal(Main escenarioPrincipal){
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    @FXML
    private void eventoCambiarContraseña(ActionEvent evento){
        if(validarCodigo()){
            mostrarAlerta(Alert.AlertType.INFORMATION,
                "Cambiar contraseña",
                "Exito!!!",
                "Se cambió de contraseña correctamente");
        limpiarControllers();
        Alert alerta = new Alert(Alert.AlertType.WARNING,
                    "¿Deseas iniciar sesión",
                    ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> eleccion = alerta.showAndWait();
        if(eleccion.get() == ButtonType.YES) escenarioPrincipal.login();
        }
    }
    
    private boolean validarCodigo(){
        String emailIngresado = txtEmail.getText();
        String codigoIngresado = txtCodigo.getText();
        String nuevaContrasenia = txtPassword.getText();

        if (emailIngresado.isEmpty() || codigoIngresado.isEmpty() || nuevaContrasenia.isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, 
                    "Campos vacíos", 
                    "Faltan datos", 
                    "Por favor llena todos los campos.");
            return false;
        }

        if (!codigoIngresado.equals(codigoGenerado)) {
            mostrarAlerta(Alert.AlertType.ERROR, 
                    "Código incorrecto", 
                    "Código inválido", 
                    "El código ingresado no coincide.");
            return false;
        }
        try{
            PreparedStatement sp = Conexion.getInstance()
                    .getConexion()
                    .prepareCall("CAll sp_cambiar_contrasenia(?,?);");
            sp.setString(1, emailIngresado);
            sp.setString(2, nuevaContrasenia);
            sp.execute();
            return true;
        }catch(SQLException error){
            System.err.println(error.getMessage());
            return false;
        }  
    }
    
    @FXML
    private void eventoCancelar(ActionEvent evento){
        escenarioPrincipal.login();
    }
    
    @FXML
    private void eventoGenerarCodigo(ActionEvent evento){
        buscarUsuarioEmail();
    }
    
    private void buscarUsuarioEmail(){
        Connection conexion = Conexion.getInstance().getConexion();
        if(conexion == null){
            mostrarAlerta(Alert.AlertType.ERROR,
                    "Base de datos",
                    "Error de conecion",
                    "No fue posible conectarse a la DB, revisa configuraciones");
        }
        String query = "call sp_validar_email(?)";
        try(PreparedStatement sp = conexion.prepareStatement(query)){
            sp.setString(1, txtEmail.getText());
            ResultSet resultado = sp.executeQuery();
            
            if(resultado.next()){
                String respuesta = resultado.getString("resultado");
                
                switch(respuesta){
                    case "NO_EMAIL" -> mostrarAlerta(Alert.AlertType.ERROR,
                            "Login",
                            "Fallo inicio de sesion",
                            "No es un correo valido");
                    case "OK" -> {
                        mostrarCodigo();
                    }
                }
            }
            
        }catch(SQLException error){
            System.err.println("Error en la consulta SQL " + error.getMessage());
            mostrarAlerta(Alert.AlertType.ERROR,
                    "Base de Datos",
                    "Error en consulta SQL",
                    "No fue posible completar la consulta a la base de datos");
        }
    }
    
    private void mostrarCodigo(){
        
        mostrarAlerta(Alert.AlertType.INFORMATION,
                "Código de recuperación",
                "este es tu código de verificación",
                "Código: " + codigoGenerado);
    }
    
    private String generarCodigoAleatorio(int longitud) {
        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder codigo = new StringBuilder();
        Random random = new Random();
        
        for (int i = 0; i < longitud; i++) {
            int index = random.nextInt(caracteres.length());
            codigo.append(caracteres.charAt(index));
        }
        return codigo.toString();
    }
    
    private void mostrarAlerta(Alert.AlertType tipo,
            String titulo, String encabezado, String mensaje){
       Alert alerta = new Alert(tipo);
       alerta.setTitle(titulo);
       alerta.setHeaderText(encabezado);
       alerta.setContentText(mensaje);
       alerta.showAndWait();
    }
    
    private void limpiarControllers(){
        txtEmail.setText("");
        txtCodigo.setText("");
        txtPassword.setText("");
    }
    
}
