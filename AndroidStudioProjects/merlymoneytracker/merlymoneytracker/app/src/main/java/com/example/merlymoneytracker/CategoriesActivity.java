package com.example.merlymoneytracker;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CategoriesActivity extends AppCompatActivity {

    EditText txtCatId, txtCatName, txtCatType;
    RecyclerView rvCategories;
    DBHelper db;
    CategoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        txtCatId = findViewById(R.id.txtCatId);
        txtCatName = findViewById(R.id.txtCatName);
        txtCatType = findViewById(R.id.txtCatType);
        rvCategories = findViewById(R.id.rvCategories);

        db = new DBHelper(this);

        rvCategories.setLayoutManager(new LinearLayoutManager(this));

        loadCategories();
    }

    // CRUD

    public void createCategory(View v) {
        String name = txtCatName.getText().toString();
        String type = txtCatType.getText().toString();

        if (name.isEmpty() || type.isEmpty()) {
            Toast.makeText(this, "Nombre y Tipo son obligatorios", Toast.LENGTH_SHORT).show();
            return;
        }

        if (db.insertCategory(new Category(name, type)))
            Toast.makeText(this, "Categoría creada", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "Error al crear categoría", Toast.LENGTH_SHORT).show();

        clearFields();
        loadCategories();
    }

    public void readCategory(View v) {
        try {
            int id = Integer.parseInt(txtCatId.getText().toString());
            Category c = db.getCategoryById(id);

            if (c != null) {
                txtCatName.setText(c.getName());
                txtCatType.setText(c.getType());
            } else {
                Toast.makeText(this, "No existe la categoría", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "ID inválido", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateCategory(View v) {
        try {
            int id = Integer.parseInt(txtCatId.getText().toString());
            String name = txtCatName.getText().toString();
            String type = txtCatType.getText().toString();

            if (db.updateCategory(new Category(id, name, type)))
                Toast.makeText(this, "Categoría actualizada", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this, "Error al actualizar", Toast.LENGTH_SHORT).show();

            clearFields();
            loadCategories();

        } catch (Exception e) {
            Toast.makeText(this, "Datos inválidos", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteCategory(View v) {
        try {
            int id = Integer.parseInt(txtCatId.getText().toString());

            if (db.deleteCategory(id))
                Toast.makeText(this, "Categoría eliminada", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this, "No existe la categoría", Toast.LENGTH_SHORT).show();

            clearFields();
            loadCategories();

        } catch (Exception e) {
            Toast.makeText(this, "ID inválido", Toast.LENGTH_SHORT).show();
        }
    }

    // =============================
    //      LISTADO DE CATEGORÍAS
    // =============================
    private void loadCategories() {
        List<Category> categoryList = db.getAllCategories(); // Debes implementar este método en DBHelper
        adapter = new CategoryAdapter(categoryList);
        rvCategories.setAdapter(adapter);
    }

    // =============================
    //      LIMPIAR CAMPOS
    // =============================
    private void clearFields() {
        txtCatId.setText("");
        txtCatName.setText("");
        txtCatType.setText("");
    }
}

