package org.pablogonzalez.models;

public class Usuario {
    private String id_usuario;
    private String email;
    private String password;
    private String idPersona;
    
    public Usuario(){}
    
    public Usuario(String id_usuario, String email, String password, 
            String idPersona){
        this.id_usuario = id_usuario;
        this.email = email;
        this.password = password;
        this.idPersona = idPersona;
    }

    public String getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(String id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(String idPersona) {
        this.idPersona = idPersona;
    }

    @Override
    public String toString() {
        return "Usuario{" + "id_usuario=" + id_usuario + ", email=" + email + ", password=" + password + ", idPersona=" + idPersona + '}';
    }
    
    
}
