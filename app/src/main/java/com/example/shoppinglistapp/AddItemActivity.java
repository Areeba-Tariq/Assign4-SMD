package com.example.shoppinglist;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddItemActivity extends AppCompatActivity {
    private EditText itemNameEditText, itemQuantityEditText, itemPriceEditText;
    private Button addItemButton;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        databaseReference = FirebaseDatabase.getInstance().getReference("ShoppingList");

        itemNameEditText = findViewById(R.id.itemNameEditText);
        itemQuantityEditText = findViewById(R.id.itemQuantityEditText);
        itemPriceEditText = findViewById(R.id.itemPriceEditText);
        addItemButton = findViewById(R.id.addItemButton);

        addItemButton.setOnClickListener(v -> addItem());
    }

    private void addItem() {
        String name = itemNameEditText.getText().toString().trim();
        String quantity = itemQuantityEditText.getText().toString().trim();
        String price = itemPriceEditText.getText().toString().trim();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(quantity) || TextUtils.isEmpty(price)) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        String id = databaseReference.push().getKey();
        Item item = new Item(name, Integer.parseInt(quantity), Double.parseDouble(price));
        databaseReference.child(id).setValue(item)
                .addOnSuccessListener(unused -> {
                    Toast.makeText(this, "Item added", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }
}
