package com.example.merlymoneytracker;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;   // ← IMPORTANTE para abrir otra Activity

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText txtId, txtTipo, txtMonto, txtCategoria, txtDescripcion, txtFecha, txtMetodoPago;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtId = findViewById(R.id.txtId);
        txtTipo = findViewById(R.id.txtTipo);
        txtMonto = findViewById(R.id.txtMonto);
        txtCategoria = findViewById(R.id.txtCategoria);
        txtDescripcion = findViewById(R.id.txtDescripcion);
        txtFecha = findViewById(R.id.txtFecha);
        txtMetodoPago = findViewById(R.id.txtMetodoPago);

        db = new DBHelper(this);
    }

    public void cmdCreate_onClick(View v) {
        try {
            String tipo = txtTipo.getText().toString();
            String montoStr = txtMonto.getText().toString();
            String categoria = txtCategoria.getText().toString();
            String descripcion = txtDescripcion.getText().toString();
            String fecha = txtFecha.getText().toString();
            String metodoPago = txtMetodoPago.getText().toString();

            if(tipo.isEmpty() || montoStr.isEmpty()) {
                Toast.makeText(this, "Tipo y Monto son obligatorios", Toast.LENGTH_SHORT).show();
                return;
            }

            double monto = Double.parseDouble(montoStr);

            Transaction t = new Transaction(tipo, monto, categoria, descripcion, fecha, metodoPago);

            if (db.insertTransaction(t))
                Toast.makeText(this, "Registro guardado", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this, "Error al guardar", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Toast.makeText(this, "Datos inválidos", Toast.LENGTH_SHORT).show();
        }
    }

    public void cmdGetById_onClick(View v) {
        try {
            int id = Integer.parseInt(txtId.getText().toString());
            Transaction t = db.getById(id);

            if (t != null) {
                txtTipo.setText(t.getType());
                txtMonto.setText(String.valueOf(t.getAmount()));
                txtCategoria.setText(t.getCategory());
                txtDescripcion.setText(t.getDescription());
                txtFecha.setText(t.getDate());
                txtMetodoPago.setText(t.getPaymentMethod());
                Toast.makeText(this, "Datos cargados", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "No existe registro con ese ID", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            Toast.makeText(this, "ID inválido", Toast.LENGTH_SHORT).show();
        }
    }

    public void cmdUpdate_onClick(View v) {
        try {
            int id = Integer.parseInt(txtId.getText().toString());

            Transaction t = new Transaction(
                    id,
                    txtTipo.getText().toString(),
                    Double.parseDouble(txtMonto.getText().toString()),
                    txtCategoria.getText().toString(),
                    txtDescripcion.getText().toString(),
                    txtFecha.getText().toString(),
                    txtMetodoPago.getText().toString()
            );

            if (db.updateTransaction(t))
                Toast.makeText(this, "Registro actualizado", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this, "No se pudo actualizar", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Toast.makeText(this, "Datos inválidos", Toast.LENGTH_SHORT).show();
        }
    }

    public void cmdDelete_onClick(View v) {
        try {
            int id = Integer.parseInt(txtId.getText().toString());

            if (db.deleteTransaction(id)) {
                limpiarCampos();
                Toast.makeText(this, "Registro eliminado", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "No existe registro con ese ID", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            Toast.makeText(this, "ID inválido", Toast.LENGTH_SHORT).show();
        }
    }

    private void limpiarCampos() {
        txtId.setText("");
        txtTipo.setText("");
        txtMonto.setText("");
        txtCategoria.setText("");
        txtDescripcion.setText("");
        txtFecha.setText("");
        txtMetodoPago.setText("");
    }


    // Boton para entrar a Categorías

    public void openCategories(View v) {
        Intent i = new Intent(this, CategoriesActivity.class);
        startActivity(i);
    }
}


