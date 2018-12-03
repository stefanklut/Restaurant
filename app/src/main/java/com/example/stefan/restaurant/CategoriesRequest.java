package com.example.stefan.restaurant;

import android.app.Activity;
import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class CategoriesRequest implements Response.Listener<JSONObject>, Response.ErrorListener {
    Context context;
    Callback activity;

    public interface Callback {
        void gotCategories(ArrayList<String> categories);
        void gotCategoriesError(String message);
    }

    public CategoriesRequest(Context context) {
        this.context = context;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        activity.gotCategoriesError(error.getMessage());
    }

    @Override
    public void onResponse(JSONObject response) {
        // TODO ask about try except
        JSONArray jsonArray = response.getJSONArray("categories");

        if (jsonArray != null) {
            ArrayList<String> categories = new ArrayList<String>();
            for (int i = 0; i<jsonArray.length(); i++) {
                categories.add(jsonArray.get(i));
            }
            activity.gotCategories(categories);
        }
    }

    void getCategories(Callback activity) {
        this.activity = activity;

        RequestQueue queue = Volley.newRequestQueue(context);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                "https://resto.mprog.nl/categories",
                null, this, this);

    }
}
