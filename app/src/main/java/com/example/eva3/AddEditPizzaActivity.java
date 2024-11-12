// AddEditPizzaActivity.java
package com.example.eva3;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddEditPizzaActivity extends AppCompatActivity {

    private EditText nombreEditText, ingredientesEditText, precioEditText;
    private Button saveButton;
    private DatabaseReference pizzaRef;
    private String pizzaId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_pizza);

        nombreEditText = findViewById(R.id.nombreEditText);
        ingredientesEditText = findViewById(R.id.ingredientesEditText);
        precioEditText = findViewById(R.id.precioEditText);
        saveButton = findViewById(R.id.saveButton);

        pizzaRef = FirebaseDatabase.getInstance().getReference("pizzas");
        pizzaId = getIntent().getStringExtra("pizzaId");

        if (pizzaId != null) {
            loadPizzaData(pizzaId);
        }

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePizza();
            }
        });
    }

    private void loadPizzaData(@NonNull String id) {
        pizzaRef.child(id).get().addOnSuccessListener(snapshot -> {
            Pizza pizza = snapshot.getValue(Pizza.class);
            if (pizza != null) {
                nombreEditText.setText(pizza.getNombre());
                ingredientesEditText.setText(pizza.getIngredientes());
                // Convertir el precio a cadena con el símbolo "$" solo para mostrarlo en la interfaz
                precioEditText.setText("$" + String.valueOf((int) pizza.getPrecio()));
            }
        }).addOnFailureListener(e -> Toast.makeText(this, "Error al cargar los datos", Toast.LENGTH_SHORT).show());
    }

    // AddEditPizzaActivity.java

    private void savePizza() {
        String nombre = nombreEditText.getText().toString().trim();
        String ingredientes = ingredientesEditText.getText().toString().trim();
        String precioStr = precioEditText.getText().toString().replace("$", "").trim();

        if (TextUtils.isEmpty(nombre) || TextUtils.isEmpty(ingredientes) || TextUtils.isEmpty(precioStr)) {
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            int precio = Integer.parseInt(precioStr);
            int imageResId = R.drawable.pizza_default; // ID de la imagen predeterminada
            Pizza pizza = new Pizza(nombre, ingredientes, precio, imageResId); // Constructor con cuatro parámetros

            if (pizzaId == null) {
                pizzaRef.push().setValue(pizza)
                        .addOnSuccessListener(aVoid -> Toast.makeText(this, "Pizza agregada", Toast.LENGTH_SHORT).show())
                        .addOnFailureListener(e -> Toast.makeText(this, "Error al agregar pizza", Toast.LENGTH_SHORT).show());
            } else {
                pizzaRef.child(pizzaId).setValue(pizza)
                        .addOnSuccessListener(aVoid -> Toast.makeText(this, "Pizza actualizada", Toast.LENGTH_SHORT).show())
                        .addOnFailureListener(e -> Toast.makeText(this, "Error al actualizar pizza", Toast.LENGTH_SHORT).show());
            }
            finish();
        } catch (NumberFormatException e) {
            Toast.makeText(this, "El precio debe ser un número válido", Toast.LENGTH_SHORT).show();
        }
    }
}
