package com.example.eva3;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.eva3.Pizza;
import com.example.eva3.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddEditPizzaActivity extends AppCompatActivity {
    private EditText nombreEditText, tipoEditText, precioEditText;
    private DatabaseReference databaseReference;
    private String pizzaId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_pizza);

        databaseReference = FirebaseDatabase.getInstance().getReference("pizzas");

        nombreEditText = findViewById(R.id.nombreEditText);
        tipoEditText = findViewById(R.id.tipoEditText);
        precioEditText = findViewById(R.id.precioEditText);
        Button saveButton = findViewById(R.id.saveButton);

        pizzaId = getIntent().getStringExtra("pizzaId");
        if (pizzaId != null) {
            loadPizzaData();
        }

        saveButton.setOnClickListener(v -> {
            String nombre = nombreEditText.getText().toString().trim();
            String tipo = tipoEditText.getText().toString().trim();
            double precio = Double.parseDouble(precioEditText.getText().toString().trim());

            if (pizzaId == null) {
                addPizza(nombre, tipo, precio);
            } else {
                editPizza(nombre, tipo, precio);
            }
        });
    }

    private void addPizza(String nombre, String tipo, double precio) {
        String id = databaseReference.push().getKey();
        Pizza pizza = new Pizza(id, nombre, tipo, precio);
        databaseReference.child(id).setValue(pizza)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Pizza agregada", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(this, "Error al agregar pizza", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void editPizza(String nombre, String tipo, double precio) {
        Pizza pizza = new Pizza(pizzaId, nombre, tipo, precio);
        databaseReference.child(pizzaId).setValue(pizza)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Pizza actualizada", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(this, "Error al actualizar pizza", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void loadPizzaData() {
        databaseReference.child(pizzaId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Pizza pizza = dataSnapshot.getValue(Pizza.class);
                if (pizza != null) {
                    nombreEditText.setText(pizza.getNombrePizza());
                    tipoEditText.setText(pizza.getTipoPizza());
                    precioEditText.setText(String.valueOf(pizza.getPrecio()));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(AddEditPizzaActivity.this, "Error al cargar datos", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
