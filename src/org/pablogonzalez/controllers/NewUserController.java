package org.pablogonzalez.controllers;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.CallableStatement;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.pablogonzalez.dao.Conexion;
import org.pablogonzalez.models.Persona;
import org.pablogonzalez.models.Usuario;
import org.pablogonzalez.system.Main;

public class NewUserController implements Initializable{
    private Main escenarioPrincipal;
    private Persona nuevaPersona = new Persona();
    private Usuario nuevoUsuario = new Usuario();

    @FXML
    private TextField txtNombres, txtApellidos, txtTelefono, txtEmail;
    
    @FXML
    private PasswordField txtPassword;
    
    @FXML
    private Button btnAceptar, btnCancelar;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public Main getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    private void limpiarControllers(){
        txtNombres.setText("");
        txtApellidos.setText("");
        txtTelefono.setText("");
        txtEmail.setText("");
        txtPassword.setText("");
    }
    
    private void agregarPersona(){
        nuevaPersona.setNombre(txtNombres.getText());
        nuevaPersona.setApellidos(txtApellidos.getText());
        nuevaPersona.setTelefono(txtTelefono.getText());
        try{
            PreparedStatement sp = Conexion.getInstance().getConexion()
                    .prepareCall("CALL sp_agregar_persona(?,?,?);");
            sp.setString(1, nuevaPersona.getNombre());
            sp.setString(2, nuevaPersona.getApellidos());
            sp.setString(3, nuevaPersona.getTelefono());
            sp.execute();
        }catch(SQLException error){
            System.err.println(error.getMessage());
        }
    }
    
    private String idPersona(){
        String id = null;
        try{
            CallableStatement cs = Conexion.getInstance().getConexion()
                    .prepareCall("{CALL sp_buscar_persona(?)}");
            cs.registerOutParameter(1, java.sql.Types.VARCHAR);
            cs.execute();
            id = cs.getString(1);
        }catch(SQLException error){
            System.err.println(error.getMessage());
        }
        return id;
    }
    
    private void agregarUsuario(String idPersona){
        nuevoUsuario.setEmail(txtEmail.getText());
        nuevoUsuario.setPassword(txtPassword.getText());
        nuevoUsuario.setIdPersona(idPersona);
        try{
            PreparedStatement sp = Conexion.getInstance()
                    .getConexion()
                    .prepareCall("CAll sp_agregar_usuario(?,?,?);");
            sp.setString(1, nuevoUsuario.getEmail());
            sp.setString(2, nuevoUsuario.getPassword());
            sp.setString(3, nuevoUsuario.getIdPersona());
            sp.execute();
        }catch(SQLException error){
            System.err.println(error.getMessage());
        }
    }
    
    @FXML
    public void eventoAceptar(ActionEvent evento){
        agregarPersona();
        String id = idPersona();
        System.out.println("ID recuperado: " + id);
        agregarUsuario(id);
        mostrarAlerta(Alert.AlertType.INFORMATION,
                "Registro de usuarios",
                "Exito!!!",
                "El registro se realizó de manera correcta");
        limpiarControllers();
        Alert alerta = new Alert(Alert.AlertType.WARNING,
                    "¿Deseas iniciar sesión",
                    ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> eleccion = alerta.showAndWait();
        if(eleccion.get() == ButtonType.YES) escenarioPrincipal.login();
    }
    
    @FXML
    public void eventoCancelar(ActionEvent evento){
        escenarioPrincipal.login();
    }
    
    private void mostrarAlerta(Alert.AlertType tipo, String titulo,
            String encabezado, String mensaje){
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(encabezado);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
