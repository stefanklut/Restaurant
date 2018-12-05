package com.example.stefan.restaurant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity implements MenuItemsRequest.Callback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // Get the intent and get the category that was clicked
        Intent intent = getIntent();
        String category = intent.getStringExtra("category");

        // Request the menu items to be displayed
        MenuItemsRequest menuItemsRequest = new MenuItemsRequest(this);
        menuItemsRequest.getMenuItems(this, category);

        // Set OnItemClickListener for the list view
        ListView listView = findViewById(R.id.listViewMenu);
        listView.setOnItemClickListener(new OnItemClickListener());
    }


    @Override
    public void gotMenuItems(ArrayList<MenuItem> menuItems) {
        // Show retrieved menu items in the list view
        ListView listView = findViewById(R.id.listViewMenu);
        MenuAdapter adapter = new MenuAdapter(this, R.layout.menu_item, menuItems);
        listView.setAdapter(adapter);
    }

    @Override
    public void gotMenuItemsError(String message) {
        // Display error if no menu items were retrieved
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    private class OnItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // Retrieve what menu item was clicked
            MenuItem menuItem = (MenuItem) parent.getItemAtPosition(position);

            // Open new activity
            Intent intent = new Intent(MenuActivity.this, MenuItemActivity.class);
            intent.putExtra("MenuItem", menuItem);
            startActivity(intent);
        }
    }
}
