package com.example.stefan.restaurant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class CategoriesActivity extends AppCompatActivity implements CategoriesRequest.Callback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        CategoriesRequest categoriesRequest = new CategoriesRequest(this);
        categoriesRequest.getCategories(this);

        ListView listView = findViewById(R.id.listViewCategories);
        listView.setOnItemClickListener(new OnItemClickListener());
    }

    @Override
    public void gotCategories(ArrayList<String> categories) {
        ListView listView = findViewById(R.id.listViewCategories);
        CategoryAdapter adapter = new CategoryAdapter(this, R.layout.category_item, categories);
        listView.setAdapter(adapter);
    }

    @Override
    public void gotCategoriesError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    private class OnItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // Retrieve what category was clicked
            String clickedCategory = (String) parent.getItemAtPosition(position);

            // Open new activity
            Intent intent = new Intent(CategoriesActivity.this, MenuActivity.class);
            intent.putExtra("category", clickedCategory);
            startActivity(intent);
        }
    }
}
