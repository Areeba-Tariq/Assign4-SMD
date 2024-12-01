package com.example.shoppinglist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;

public class FirestoreAdapter extends FirestoreRecyclerAdapter<Item, FirestoreAdapter.ItemViewHolder> {

    private final FirebaseFirestore firestore;

    public FirestoreAdapter(@NonNull FirestoreRecyclerOptions<Item> options, FirebaseFirestore firestore) {
        super(options);
        this.firestore = firestore;
    }

    @Override
    protected void onBindViewHolder(@NonNull ItemViewHolder holder, int position, @NonNull Item model) {
        holder.itemName.setText(model.getName());
        holder.itemQuantity.setText("Quantity: " + model.getQuantity());
        holder.itemPrice.setText("Price: $" + model.getPrice());

        holder.deleteButton.setOnClickListener(v -> {
            String documentId = getSnapshots().getSnapshot(position).getId();
            firestore.collection("ShoppingList").document(documentId).delete()
                    .addOnSuccessListener(unused -> notifyDataSetChanged())
                    .addOnFailureListener(e -> Toast.makeText(holder.itemView.getContext(), "Error deleting item", Toast.LENGTH_SHORT).show());
        });
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new ItemViewHolder(view);
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView itemName, itemQuantity, itemPrice;
        Button deleteButton;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.itemName);
            itemQuantity = itemView.findViewById(R.id.itemQuantity);
            itemPrice = itemView.findViewById(R.id.itemPrice);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
}
