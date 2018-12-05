package com.example.stefan.restaurant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MenuItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_item);

        // Get the intent and get the menu item that was clicked
        Intent intent = getIntent();
        MenuItem menuItem = (MenuItem) intent.getSerializableExtra("MenuItem");

        // Variables for the views
        TextView name = findViewById(R.id.textViewNameItem);
        TextView description = findViewById(R.id.textViewDescriptionItem);
        TextView price = findViewById(R.id.textViewPriceItem);
        ImageView image = findViewById(R.id.imageViewMenuItem);

        // Set the text in the text view depending on what menu item was returned
        name.setText(menuItem.getName());
        description.setText(menuItem.getDescription());
        price.setText("$ " + menuItem.getPrice());

        // Set image in image view based on the url that was in the MenuItem Object
        Picasso.get()
                .load(menuItem.getImageUrl())
                .error(R.drawable.error)
                .into(image);
    }
}
