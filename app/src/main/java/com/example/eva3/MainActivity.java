// MainActivity.java
package com.example.eva3;

import android.content.Intent;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        addPizzaButton = findViewById(R.id.addPizzaButton);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        pizzaList = new ArrayList<>();
        pizzaAdapter = new PizzaAdapter(this, pizzaList);
        recyclerView.setAdapter(pizzaAdapter);

        pizzaRef = FirebaseDatabase.getInstance().getReference("pizzas");

        loadPizzas();

        addPizzaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddEditPizzaActivity.class);
                startActivity(intent);
            }
        });
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
}
