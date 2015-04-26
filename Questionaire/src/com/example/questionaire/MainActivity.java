package com.example.questionaire;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.db.CategoriesDataSource;
import com.example.db.Category;

public class MainActivity extends ListActivity
{
    private CategoriesDataSource datasource;
    ArrayList<Category>          values;
    CategoryListAdapeter         adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        values = new ArrayList<Category>();

        datasource = new CategoriesDataSource(this);
        datasource.open();

        values = (ArrayList<Category>) datasource.getAllCategories();
        Log.d("TAG", "size of values in oncreate " + values.size());
        adapter = new CategoryListAdapeter(values, this);
        setListAdapter(adapter);
        Log.d("TAG", "size of values in oncreate " + values.size());
        testMethod();
    }

    public void testMethod()
    {
        try
        {
            JSONObject bbb = new JSONObject();
            bbb.put("mayank", "bansal");
            bbb.put("pin", "high");
            JSONArray ab = new JSONArray();
            ab.put(bbb);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("abc", "asdfasdf");
            jsonObject.put("Data", ab);
            jsonObject.put("R", "1");
            Log.d("TAG", "" + jsonObject);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    // Will be called via the onClick attribute
    // of the buttons in main.xml
    public void onClick(View view)
    {
        Log.d("TAG", "onclick " + values.size());

        @SuppressWarnings("unchecked")
        Category category = null;
        switch (view.getId())
        {
        case R.id.add:
            Log.d("TAG", "Inside on click add");
            String[] title = new String[]
            { "Cool", "Very nice", "Hate it" };
            int nextTitle = new Random().nextInt(3);

            String[] desc = new String[]
            { "this is very cool", "this is Very nice", "this I Hate very mcu" };
            int nextDesc = new Random().nextInt(3);

            // save the new comment to the database
            category = datasource.createCategory(title[nextTitle], desc[nextDesc]);
            Log.d("TAG", "Before adding new element " + values.size());
            values.add(category);
            Log.d("TAG", "size of values " + values.size());
            for (Category item : values)
            {
                Log.d("TAG", "" + item.getId() + "," + item.getTitle() + "," + item.getDescription());
            }
            break;
        case R.id.delete:
            Log.d("TAG", "Inside on click delete");
            Log.d("TAG", "list adapter count " + getListAdapter().getCount());
            if (getListAdapter().getCount() > 0)
            {
                category = (Category) getListAdapter().getItem(0);
                datasource.deleteCategory(category);
                values.remove(0);
                values.trimToSize();
                adapter.setCategoriesList(values);
            }
            break;
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume()
    {
        datasource.open();
        super.onResume();
    }

    @Override
    protected void onPause()
    {
        datasource.close();
        super.onPause();
    }

}
