package com.example.eva3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PizzaAdapter pizzaAdapter;
    private List<Pizza> pizzaList;
    private DatabaseReference pizzaRef;
    private Button addPizzaButton;
    private boolean isAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar el botón de agregar pizza
        addPizzaButton = findViewById(R.id.addPizzaButton);

        // Cargar el rol de administrador desde SharedPreferences
        SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        isAdmin = prefs.getBoolean("isAdmin", false);

        // Configuración del RecyclerView y adaptador
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        pizzaList = new ArrayList<>();
        pizzaAdapter = new PizzaAdapter((ArrayList<Pizza>) pizzaList, isAdmin);
        recyclerView.setAdapter(pizzaAdapter);

        // Controlar la visibilidad del botón de agregar según el rol
        if (isAdmin) {
            addPizzaButton.setVisibility(View.VISIBLE);
            addPizzaButton.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, AddEditPizzaActivity.class);
                startActivity(intent);
            });
        } else {
            addPizzaButton.setVisibility(View.GONE);
        }

        // Inicializar referencia de Firebase y cargar datos
        pizzaRef = FirebaseDatabase.getInstance().getReference("pizzas");
        loadPizzas();
    }

    private void loadPizzas() {
        pizzaRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                pizzaList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Pizza pizza = dataSnapshot.getValue(Pizza.class);
                    if (pizza != null) {
                        pizza.setId(dataSnapshot.getKey());
                        pizzaList.add(pizza);
                    }
                }
                pizzaAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Error al cargar las pizzas", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        // Redirige al LoginActivity cuando se presiona "Atrás"
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish(); // Cierra MainActivity para evitar volver a ella
    }
}
