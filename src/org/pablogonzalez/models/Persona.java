package org.pablogonzalez.models;

public class Persona {
    private String idPersona;
    private String nombre;
    private String apellidos;
    private String telefono;
    
    public Persona(){}
    
    public Persona(String idPersona, String nombre, String apellidos, 
            String telefono){
        this.idPersona = idPersona;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.telefono = telefono;
    }
    
    public String getIdPersona(){
        return idPersona;
    }
    
    public void setIdPersona(String idPersona){
        this.idPersona = idPersona;
    }
    
    public String getNombre(){
        return nombre;
    }
    
    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    
    public String getApellidos(){
        return apellidos;
    }
    
    public void setApellidos(String apellidos){
        this.apellidos = apellidos;
    }
    
    public String getTelefono(){
        return telefono;
    }
    
    public void setTelefono(String telefono){
        this.telefono = telefono;
    }

    @Override
    public String toString() {
        return "Persona{" + "idPersona=" + idPersona + ", nombre=" + nombre + ", apellidos=" + apellidos + ", telefono=" + telefono + '}';
    }
    
    
}
