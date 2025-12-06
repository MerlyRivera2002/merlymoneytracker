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

    EditText txtCatId, txtCatName, txtCatType, txtCatIcon;
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
        txtCatIcon = findViewById(R.id.txtCatIcon);
        rvCategories = findViewById(R.id.rvCategories);

        db = new DBHelper(this);
        rvCategories.setLayoutManager(new LinearLayoutManager(this));

        loadCategories();
    }

    public void createCategory(View v) {
        String name = txtCatName.getText().toString().trim();
        String type = txtCatType.getText().toString().trim();
        String icon = txtCatIcon.getText().toString().trim();

        if (name.isEmpty() || type.isEmpty() || icon.isEmpty()) {
            Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean ok = db.insertCategory(new Category(name, type, icon));

        if (ok) {
            Toast.makeText(this, "Categoría creada", Toast.LENGTH_SHORT).show();
            clearFields();
            loadCategories();
        } else {
            Toast.makeText(this, "Error al crear categoría", Toast.LENGTH_SHORT).show();
        }
    }

    public void readCategory(View v) {
        if (txtCatId.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Debe ingresar un ID", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            int id = Integer.parseInt(txtCatId.getText().toString());
            Category c = db.getCategoryById(id);

            if (c != null) {
                txtCatName.setText(c.getName());
                txtCatType.setText(c.getType());
                txtCatIcon.setText(c.getIcon());
            } else {
                Toast.makeText(this, "No existe la categoría con ese ID", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            Toast.makeText(this, "ID inválido", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateCategory(View v) {
        if (txtCatId.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Debe ingresar el ID de la categoría", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            int id = Integer.parseInt(txtCatId.getText().toString());

            String name = txtCatName.getText().toString().trim();
            String type = txtCatType.getText().toString().trim();
            String icon = txtCatIcon.getText().toString().trim();

            if (name.isEmpty() || type.isEmpty() || icon.isEmpty()) {
                Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean ok = db.updateCategory(new Category(id, name, type, icon));

            if (ok) {
                Toast.makeText(this, "Categoría actualizada", Toast.LENGTH_SHORT).show();
                clearFields();
                loadCategories();
            } else {
                Toast.makeText(this, "No existe la categoría", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            Toast.makeText(this, "Error en los datos", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteCategory(View v) {
        if (txtCatId.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Debe ingresar el ID para eliminar", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            int id = Integer.parseInt(txtCatId.getText().toString());

            boolean ok = db.deleteCategory(id);

            if (ok) {
                Toast.makeText(this, "Categoría eliminada", Toast.LENGTH_SHORT).show();
                clearFields();
                loadCategories();
            } else {
                Toast.makeText(this, "Categoría no encontrada", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            Toast.makeText(this, "ID inválido", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadCategories() {
        List<Category> list = db.getAllCategories();
        adapter = new CategoryAdapter(list);
        rvCategories.setAdapter(adapter);
    }

    private void clearFields() {
        txtCatId.setText("");
        txtCatName.setText("");
        txtCatType.setText("");
        txtCatIcon.setText("");
    }
}
