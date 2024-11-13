package com.example.eva3;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddEditPizzaActivity extends AppCompatActivity {

    private EditText nombreEditText, ingredientesEditText, precioEditText;
    private Spinner imageSpinner;
    private Button saveButton;
    private DatabaseReference pizzaRef;
    private String pizzaId;

    // Nombres y IDs de las imágenes
    private String[] imageNames = {"Pizza Napolitana", "Pizza Pepperoni", "Pizza Hawaiana"};
    private int[] imageIds = {R.drawable.napolitana, R.drawable.pepperoni, R.drawable.hawaiana};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_pizza);

        nombreEditText = findViewById(R.id.nombreEditText);
        ingredientesEditText = findViewById(R.id.ingredientesEditText);
        precioEditText = findViewById(R.id.precioEditText);
        imageSpinner = findViewById(R.id.imageSpinner); // Inicializa el Spinner de nombres
        saveButton = findViewById(R.id.saveButton);

        // Configura el adaptador para el Spinner de nombres
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, imageNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        imageSpinner.setAdapter(adapter);

        pizzaRef = FirebaseDatabase.getInstance().getReference("pizzas");
        pizzaId = getIntent().getStringExtra("pizzaId");

        if (pizzaId != null) {
            loadPizzaData(pizzaId);
        }

        saveButton.setOnClickListener(v -> savePizza());
    }

    private void loadPizzaData(@NonNull String id) {
        pizzaRef.child(id).get().addOnSuccessListener(snapshot -> {
            Pizza pizza = snapshot.getValue(Pizza.class);
            if (pizza != null) {
                nombreEditText.setText(pizza.getNombre());
                ingredientesEditText.setText(pizza.getIngredientes());
                precioEditText.setText(String.valueOf((int) pizza.getPrecio()));

                // Selecciona la imagen en el Spinner (opcional)
                for (int i = 0; i < imageIds.length; i++) {
                    if (imageIds[i] == pizza.getImageResId()) {
                        imageSpinner.setSelection(i);
                        break;
                    }
                }
            }
        }).addOnFailureListener(e -> Toast.makeText(this, "Error al cargar los datos", Toast.LENGTH_SHORT).show());
    }

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
            int selectedImageResId = imageIds[imageSpinner.getSelectedItemPosition()]; // Obtiene el ID de la imagen seleccionada
            Pizza pizza = new Pizza(nombre, ingredientes, precio, selectedImageResId); // Constructor con imagen

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
