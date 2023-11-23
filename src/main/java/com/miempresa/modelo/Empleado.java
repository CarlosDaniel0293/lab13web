package com.miempresa.modelo;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name= "Empleado")
public class Empleado {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name= "nombre")
	private String nombre;
	
	@Column(name = "rol")
	private String rol;
	
	@OneToMany
	private List<Tarea>tarea;
	
	 // Constructor 
    public Empleado() {
    }

    // Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
    
    public List<Tarea> getTareas() {
        return tarea;
    }

    public void setTareas(List<Tarea> tareas) {
        this.tarea = tareas;
    }

}
