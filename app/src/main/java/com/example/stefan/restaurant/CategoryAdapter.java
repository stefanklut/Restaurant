package com.example.stefan.restaurant;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CategoryAdapter extends ArrayAdapter {
    private ArrayList<String> categories;

    public CategoryAdapter(Context context, int resource, ArrayList<String> objects) {
        super(context, resource, objects);
        categories = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.category_item, parent, false);
        }

        // Variables for the views in the convert view
        TextView category = convertView.findViewById(R.id.textViewCategory);

        // Depending on the position set the name of the category
        category.setText(categories.get(position));

        return convertView;
    }
}
