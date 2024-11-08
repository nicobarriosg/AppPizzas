package com.example.eva3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eva3.Pizza;
import com.example.eva3.R;

import java.util.List;

public class PizzaAdapter extends RecyclerView.Adapter<PizzaAdapter.PizzaViewHolder> {
    private List<Pizza> pizzaList;
    private Context context;

    public PizzaAdapter(List<Pizza> pizzaList, Context context) {
        this.pizzaList = pizzaList;
        this.context = context;
    }

    public void setPizzas(List<Pizza> pizzas) {
        this.pizzaList = pizzas;
        notifyDataSetChanged();
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
        holder.nombreTextView.setText(pizza.getNombrePizza());
        holder.tipoTextView.setText(pizza.getTipoPizza());
        holder.precioTextView.setText(String.valueOf(pizza.getPrecio()));
    }

    @Override
    public int getItemCount() {
        return pizzaList.size();
    }

    static class PizzaViewHolder extends RecyclerView.ViewHolder {
        TextView nombreTextView, tipoTextView, precioTextView;

        public PizzaViewHolder(@NonNull View itemView) {
            super(itemView);
            nombreTextView = itemView.findViewById(R.id.nombreTextView);
            tipoTextView = itemView.findViewById(R.id.tipoTextView);
            precioTextView = itemView.findViewById(R.id.precioTextView);
        }
    }
}
