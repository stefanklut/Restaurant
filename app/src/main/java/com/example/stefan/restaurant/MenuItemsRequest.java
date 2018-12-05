package com.example.stefan.restaurant;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MenuItemsRequest implements Response.Listener<JSONObject>, Response.ErrorListener {
    Context context;
    Callback activity;

    public interface Callback {
        // Callback functions
        void gotMenuItems(ArrayList<MenuItem> menuItems);
        void gotMenuItemsError(String message);
    }

    public MenuItemsRequest (Context context) {
        this.context = context;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        // If Volley gets an error return this to the activity
        activity.gotMenuItemsError(error.getMessage());
    }

    @Override
    public void onResponse(JSONObject response) {
        // Check if the JSONArray exists
        JSONArray jsonArray = null;
        try {
            jsonArray = response.getJSONArray("items");
        } catch (JSONException e) {
            e.printStackTrace();
            activity.gotMenuItemsError(e.getMessage());
        }

        //If it does put the items from the JSONArray into a MenuItem Object in an ArrayList
        if (jsonArray != null) {
            ArrayList<MenuItem> items = new ArrayList<>();
            for (int i = 0; i<jsonArray.length(); i++) {
                try {
                    MenuItem menuItem = new MenuItem();
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    menuItem.setCategory(jsonObject.getString("category"));
                    menuItem.setName(jsonObject.getString("name"));
                    menuItem.setDescription(jsonObject.getString("description"));
                    menuItem.setPrice(jsonObject.getString("price"));
                    menuItem.setImageUrl(jsonObject.getString("image_url"));
                    items.add(menuItem);
                } catch (JSONException e) {
                    e.printStackTrace();
                    activity.gotMenuItemsError(e.getMessage());
                }
            }
            activity.gotMenuItems(items);
        }
    }

    void getMenuItems(Callback activity, String category) {
        this.activity = activity;

        // Create Volley queue
        RequestQueue queue = Volley.newRequestQueue(context);

        // Create JsonObjectRequest with API url
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                "https://resto.mprog.nl/menu?category=" + category,
                null, this, this);

        // Put request in the queue
        queue.add(jsonObjectRequest);
    }
}
