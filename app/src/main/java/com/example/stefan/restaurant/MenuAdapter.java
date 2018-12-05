package com.example.stefan.restaurant;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MenuAdapter extends ArrayAdapter {
    private ArrayList<MenuItem> menuItems;

    public MenuAdapter(Context context, int resource, ArrayList<MenuItem> objects) {
        super(context, resource, objects);
        menuItems = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.menu_item, parent, false);
        }

        // Variables for the views in the convert view
        TextView name = convertView.findViewById(R.id.textViewName);
        TextView price = convertView.findViewById(R.id.textViewPrice);
        ImageView image = convertView.findViewById(R.id.imageViewMenu);

        // Depending on the position set name, price and description of the menu item
        MenuItem menuItem = menuItems.get(position);
        name.setText(menuItem.getName());
        price.setText("$ " + menuItem.getPrice());

        // Set image in image view based on the url that was in the MenuItem Object
        Picasso.get()
                .load(menuItem.getImageUrl())
                .resize(256, 256)
                .centerCrop()
                .error(R.drawable.error)
                .into(image);


        return convertView;
    }
}
