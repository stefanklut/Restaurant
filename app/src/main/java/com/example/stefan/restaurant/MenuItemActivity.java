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

        Intent intent = getIntent();
        MenuItem menuItem = (MenuItem) intent.getSerializableExtra("MenuItem");

        TextView name = findViewById(R.id.textViewNameItem);
        TextView description = findViewById(R.id.textViewDescriptionItem);
        TextView price = findViewById(R.id.textViewPriceItem);
        ImageView image = findViewById(R.id.imageViewMenuItem);

        name.setText(menuItem.getName());
        description.setText(menuItem.getDescription());
        price.setText("$ " + menuItem.getPrice());

        Picasso.get()
                .load(menuItem.getImageUrl())
                .into(image);
    }
}
