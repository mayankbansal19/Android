package com.example.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class CategoriesDataSource
{

    // Database fields
    private SQLiteDatabase database;
    private CategoriesSQLiteHelper dbHelper;
    private String[]       allColumns = { CategoriesSQLiteHelper.COLUMN_ID, CategoriesSQLiteHelper.COLUMN_TITLE, CategoriesSQLiteHelper.COLUMN_DESCRIPTION };

    public CategoriesDataSource(Context context)
    {
        dbHelper = new CategoriesSQLiteHelper(context);
    }

    public void open() throws SQLException
    {
        database = dbHelper.getWritableDatabase();
    }

    public void close()
    {
        dbHelper.close();
    }

    public Category createCategory(String title, String description)
    {
        Log.d("TAG","Create category...");
        ContentValues values = new ContentValues();
        values.put(CategoriesSQLiteHelper.COLUMN_TITLE, title);
        values.put(CategoriesSQLiteHelper.COLUMN_DESCRIPTION, description);
        long insertId = database.insert(CategoriesSQLiteHelper.TABLE_NAME, null, values);
        Cursor cursor = database.query(CategoriesSQLiteHelper.TABLE_NAME, allColumns, CategoriesSQLiteHelper.COLUMN_ID + " = " + insertId, null, null,
                null, null);
        Log.d("TAG",""+cursor.getCount());
        cursor.moveToFirst();
        Category newComment = cursorToCategory(cursor);
        cursor.close();
        return newComment;
    }

    public void deleteCategory(Category category)
    {
        long id = category.getId();
        Log.d("TAG","Category deleted with id: " + id);
        database.delete(CategoriesSQLiteHelper.TABLE_NAME, CategoriesSQLiteHelper.COLUMN_ID + " = " + id, null);
    }

    public List<Category> getAllCategories()
    {
        List<Category> categories = new ArrayList<Category>();

        Cursor cursor = database.query(CategoriesSQLiteHelper.TABLE_NAME, allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            Category newCategory= cursorToCategory(cursor);
            categories.add(newCategory);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return categories;
    }

    private Category cursorToCategory(Cursor cursor)
    {
        Category category = new Category();
        category.setId(cursor.getLong(0));
        category.setTitle(cursor.getString(1));
        category.setDescription(cursor.getString(2));
        Log.d("TAG", ""+category.getId()+","+category.getTitle()+","+category.getDescription());
        return category;
    }

}
