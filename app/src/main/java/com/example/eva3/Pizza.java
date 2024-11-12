// Pizza.java
package com.example.eva3;

public class Pizza {
    private String id;
    private String nombre;
    private String ingredientes;
    private int precio;
    private int imageResId;  // ID del recurso de la imagen local

    public Pizza() {}

    public Pizza(String nombre, String ingredientes, int precio, int imageResId) {
        this.nombre = nombre;
        this.ingredientes = ingredientes;
        this.precio = precio;
        this.imageResId = imageResId;
    }

    public int getImageResId() {
        return imageResId;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }

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

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = (int) precio;
    }
}
