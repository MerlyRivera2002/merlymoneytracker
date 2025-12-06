package com.example.merlymoneytracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DBNAME = "moneytracker.db";

    public DBHelper(Context context) {
        super(context, DBNAME, null, 2); // Versión 2: agrega tabla categories
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // ========================
        // TABLA TRANSACCIONES
        // ========================
        db.execSQL(
                "CREATE TABLE transactions (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "type TEXT," +
                        "amount REAL," +
                        "category TEXT," +
                        "description TEXT," +
                        "date TEXT," +
                        "payment_method TEXT)"
        );

        // Tabla categorias

        db.execSQL(
                "CREATE TABLE categories (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "name TEXT," +
                        "type TEXT)" // DEBIT | CREDIT
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            db.execSQL("CREATE TABLE IF NOT EXISTS categories (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "name TEXT," +
                    "type TEXT)");
        }
    }

    // =====================================================
    //                    TRANSACCIONES
    // =====================================================

    public boolean insertTransaction(Transaction t) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("type", t.getType());
        values.put("amount", t.getAmount());
        values.put("category", t.getCategory());
        values.put("description", t.getDescription());
        values.put("date", t.getDate());
        values.put("payment_method", t.getPaymentMethod());

        long result = db.insert("transactions", null, values);
        return result != -1;
    }

    public Transaction getById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM transactions WHERE id = ?",
                new String[]{String.valueOf(id)}
        );

        if (cursor.moveToFirst()) {
            Transaction t = new Transaction(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getDouble(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6)
            );
            cursor.close();
            return t;
        }
        cursor.close();
        return null;
    }

    public boolean updateTransaction(Transaction t) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("type", t.getType());
        values.put("amount", t.getAmount());
        values.put("category", t.getCategory());
        values.put("description", t.getDescription());
        values.put("date", t.getDate());
        values.put("payment_method", t.getPaymentMethod());

        int rows = db.update(
                "transactions",
                values,
                "id=?",
                new String[]{String.valueOf(t.getId())}
        );

        return rows > 0;
    }

    public boolean deleteTransaction(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rows = db.delete("transactions", "id=?", new String[]{String.valueOf(id)});
        return rows > 0;
    }

    // =====================================================
    //                    CATEGORÍAS
    // =====================================================

    public boolean insertCategory(Category c) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("name", c.getName());
        values.put("type", c.getType());

        long result = db.insert("categories", null, values);
        return result != -1;
    }

    public Category getCategoryById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM categories WHERE id = ?",
                new String[]{String.valueOf(id)}
        );

        if (cursor.moveToFirst()) {
            Category c = new Category(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2)
            );
            cursor.close();
            return c;
        }
        cursor.close();
        return null;
    }

    public boolean updateCategory(Category c) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("name", c.getName());
        values.put("type", c.getType());

        int rows = db.update(
                "categories",
                values,
                "id=?",
                new String[]{String.valueOf(c.getId())}
        );
        return rows > 0;
    }

    public boolean deleteCategory(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rows = db.delete("categories", "id=?", new String[]{String.valueOf(id)});
        return rows > 0;
    }

    public List<Category> getAllCategories() {
        List<Category> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM categories", null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String type = cursor.getString(2);
                list.add(new Category(id, name, type));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }
}





