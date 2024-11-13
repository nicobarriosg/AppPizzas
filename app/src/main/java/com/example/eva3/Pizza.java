package com.example.eva3;

public class Pizza {
    private String id;
    private String nombre;
    private String ingredientes;
    private int precio;
    private int imageResId;
    private long createdAt; // Campo para la fecha de creación en milisegundos

    // Constructor vacío para Firebase
    public Pizza() {}

    // Constructor para crear una pizza con los datos básicos
    public Pizza(String nombre, String ingredientes, int precio, int imageResId) {
        this.nombre = nombre;
        this.ingredientes = ingredientes;
        this.precio = precio;
        this.imageResId = imageResId;
        this.createdAt = System.currentTimeMillis(); // Asigna la fecha de creación actual
    }

    // Getters y setters para cada campo
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(String ingredientes) {
        this.ingredientes = ingredientes;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public int getImageResId() {
        return imageResId;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }
}
