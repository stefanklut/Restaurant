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

public class CategoriesRequest implements Response.Listener<JSONObject>, Response.ErrorListener {
    Context context;
    Callback activity;

    public interface Callback {
        // Callback functions
        void gotCategories(ArrayList<String> categories);
        void gotCategoriesError(String message);
    }

    public CategoriesRequest(Context context) {
        this.context = context;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        // If Volley gets an error return this to the activity
        activity.gotCategoriesError(error.getMessage());
    }

    @Override
    public void onResponse(JSONObject response) {
        // Check if the JSONArray exists
        JSONArray jsonArray = null;
        try {
            jsonArray = response.getJSONArray("categories");
        } catch (JSONException e) {
            e.printStackTrace();
            activity.gotCategoriesError(e.getMessage());
        }

        //If it does put the items from the JSONArray into a String in an ArrayList
        if (jsonArray != null) {
            ArrayList<String> categories = new ArrayList<>();
            for (int i = 0; i<jsonArray.length(); i++) {
                try {
                    categories.add(jsonArray.get(i).toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                    activity.gotCategoriesError(e.getMessage());
                }
            }
            activity.gotCategories(categories);
        }
    }

    void getCategories(Callback activity) {
        this.activity = activity;

        // Create Volley queue
        RequestQueue queue = Volley.newRequestQueue(context);

        // Create JsonObjectRequest with API url
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                "https://resto.mprog.nl/categories",
                null, this, this);

        // Put request in the queue
        queue.add(jsonObjectRequest);
    }
}
