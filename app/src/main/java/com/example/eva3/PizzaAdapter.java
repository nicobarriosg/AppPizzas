package com.example.eva3;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PizzaAdapter extends RecyclerView.Adapter<PizzaAdapter.PizzaViewHolder> {

    private ArrayList<Pizza> pizzaList;
    private boolean isAdmin;

    public PizzaAdapter(ArrayList<Pizza> pizzaList, boolean isAdmin) {
        this.pizzaList = pizzaList != null ? pizzaList : new ArrayList<>();
        this.isAdmin = isAdmin;
    }

    @NonNull
    @Override
    public PizzaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pizza, parent, false);
        return new PizzaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PizzaViewHolder holder, int position) {
        Pizza pizza = pizzaList.get(position);
        holder.pizzaName.setText(pizza.getNombre());
        holder.pizzaPrice.setText("Precio: $" + pizza.getPrecio());
        holder.pizzaImageView.setImageResource(pizza.getImageResId()); // Mostrar la imagen
        holder.ingredientesTextView.setText("Ingredientes: " + pizza.getIngredientes()); // Mostrar los ingredientes

        if (isAdmin) {
            holder.editButton.setVisibility(View.VISIBLE);
            holder.deleteButton.setVisibility(View.VISIBLE);
            holder.btnBuy.setVisibility(View.GONE);

            // Mostrar ID y fecha solo si el usuario es admin
            holder.idTextView.setVisibility(View.VISIBLE);
            holder.idTextView.setText("ID: " + pizza.getId());

            holder.fechaTextView.setVisibility(View.VISIBLE);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            holder.fechaTextView.setText("Fecha de Creación: " + dateFormat.format(new Date(pizza.getCreatedAt())));

            holder.editButton.setOnClickListener(v -> {
                Intent intent = new Intent(v.getContext(), AddEditPizzaActivity.class);
                intent.putExtra("pizzaId", pizza.getId()); // Pasa el ID de la pizza
                v.getContext().startActivity(intent);
            });

            holder.deleteButton.setOnClickListener(v -> {
                DatabaseReference pizzaRef = FirebaseDatabase.getInstance().getReference("pizzas").child(pizza.getId());
                pizzaRef.removeValue().addOnSuccessListener(aVoid ->
                        Toast.makeText(v.getContext(), "Pizza eliminada", Toast.LENGTH_SHORT).show()
                ).addOnFailureListener(e ->
                        Toast.makeText(v.getContext(), "Error al eliminar pizza", Toast.LENGTH_SHORT).show()
                );
            });

        } else {
            holder.editButton.setVisibility(View.GONE);
            holder.deleteButton.setVisibility(View.GONE);
            holder.idTextView.setVisibility(View.GONE);
            holder.fechaTextView.setVisibility(View.GONE);
            holder.btnBuy.setVisibility(View.VISIBLE);
            holder.btnBuy.setOnClickListener(v ->
                    Toast.makeText(v.getContext(), "Compraste: " + pizza.getNombre(), Toast.LENGTH_SHORT).show()
            );
        }
    }

    @Override
    public int getItemCount() {
        return pizzaList.size();
    }

    public static class PizzaViewHolder extends RecyclerView.ViewHolder {
        TextView pizzaName, pizzaPrice, ingredientesTextView, idTextView, fechaTextView;
        ImageView pizzaImageView;
        Button btnBuy, editButton, deleteButton;

        public PizzaViewHolder(@NonNull View itemView) {
            super(itemView);
            pizzaName = itemView.findViewById(R.id.nombreTextView);
            pizzaPrice = itemView.findViewById(R.id.precioTextView);
            pizzaImageView = itemView.findViewById(R.id.pizzaImageView);
            ingredientesTextView = itemView.findViewById(R.id.ingredientesTextView);
            idTextView = itemView.findViewById(R.id.idTextView);
            fechaTextView = itemView.findViewById(R.id.fechaTextView);
            btnBuy = itemView.findViewById(R.id.btnBuy);
            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
}
