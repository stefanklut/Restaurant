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
        void gotMenuItems(ArrayList<MenuItem> menuItems);
        void gotMenuItemsError(String message);
    }

    public MenuItemsRequest (Context context) {
        this.context = context;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        activity.gotMenuItemsError(error.getMessage());
    }

    @Override
    public void onResponse(JSONObject response) {
        JSONArray jsonArray = null;
        try {
            jsonArray = response.getJSONArray("items");
        } catch (JSONException e) {
            e.printStackTrace();
            activity.gotMenuItemsError(e.getMessage());
        }

        if (jsonArray != null) {
            ArrayList<MenuItem> items = new ArrayList<MenuItem>();
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

        RequestQueue queue = Volley.newRequestQueue(context);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                "https://resto.mprog.nl/menu?category=" + category,
                null, this, this);
        queue.add(jsonObjectRequest);
    }
}
