package com.example.eva3;

// Clase Pizza
public class Pizza {
    private String id;
    private String nombrePizza;
    private String tipoPizza;
    private double precio;

    // Constructor vacío requerido por Firebase
    public Pizza() {}

    // Constructor con parámetros
    public Pizza(String id, String nombrePizza, String tipoPizza, double precio) {
        this.id = id;
        this.nombrePizza = nombrePizza;
        this.tipoPizza = tipoPizza;
        this.precio = precio;
    }

    // Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombrePizza() {
        return nombrePizza;
    }

    public void setNombrePizza(String nombrePizza) {
        this.nombrePizza = nombrePizza;
    }

    public String getTipoPizza() {
        return tipoPizza;
    }

    public void setTipoPizza(String tipoPizza) {
        this.tipoPizza = tipoPizza;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    // Método toString opcional para visualizar en listas
    @Override
    public String toString() {
        return "Pizza{" +
                "id='" + id + '\'' +
                ", nombrePizza='" + nombrePizza + '\'' +
                ", tipoPizza='" + tipoPizza + '\'' +
                ", precio=" + precio +
                '}';
    }
}
