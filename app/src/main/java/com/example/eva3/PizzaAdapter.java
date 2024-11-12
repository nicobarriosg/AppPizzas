// PizzaAdapter.java
package com.example.eva3;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.List;

public class PizzaAdapter extends RecyclerView.Adapter<PizzaAdapter.PizzaViewHolder> {

    private Context context;
    private List<Pizza> pizzaList;

    public PizzaAdapter(Context context, List<Pizza> pizzaList) {
        this.context = context;
        this.pizzaList = pizzaList;
    }

    @NonNull
    @Override
    public PizzaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_pizza, parent, false);
        return new PizzaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PizzaViewHolder holder, int position) {
        Pizza pizza = pizzaList.get(position);
        holder.nombreTextView.setText(pizza.getNombre());
        holder.ingredientesTextView.setText(pizza.getIngredientes());
        // Mostrar el precio formateado con el símbolo "$" sin decimales en la lista
        holder.precioTextView.setText("$" + String.valueOf((int) pizza.getPrecio()));

        // Acción del botón Editar
        holder.editButton.setOnClickListener(v -> {
            Intent intent = new Intent(context, AddEditPizzaActivity.class);
            intent.putExtra("pizzaId", pizza.getId());
            context.startActivity(intent);
        });

        // Acción del botón Borrar
        holder.deleteButton.setOnClickListener(v -> {
            DatabaseReference pizzaRef = FirebaseDatabase.getInstance().getReference("pizzas").child(pizza.getId());
            pizzaRef.removeValue()
                    .addOnSuccessListener(aVoid -> {
                        pizzaList.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, pizzaList.size());
                        Toast.makeText(context, "Pizza eliminada", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> Toast.makeText(context, "Error al eliminar pizza", Toast.LENGTH_SHORT).show());
        });
    }

    @Override
    public int getItemCount() {
        return pizzaList.size();
    }

    public static class PizzaViewHolder extends RecyclerView.ViewHolder {
        TextView nombreTextView, ingredientesTextView, precioTextView;
        Button editButton, deleteButton;

        public PizzaViewHolder(@NonNull View itemView) {
            super(itemView);
            nombreTextView = itemView.findViewById(R.id.nombreTextView);
            ingredientesTextView = itemView.findViewById(R.id.ingredientesTextView);
            precioTextView = itemView.findViewById(R.id.precioTextView);
            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
}
