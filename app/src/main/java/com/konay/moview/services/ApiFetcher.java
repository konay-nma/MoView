package com.konay.moview.services;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class ApiFetcher {
    private String url;
    private Context context;
    private OnResponseListener listener;
    private OnErrorListener errorListener;

    public ApiFetcher (Context context) {
        listener = null;
        this.context = context;
    }

    public interface OnResponseListener {
        void OnResponse(JSONObject response) throws JSONException;
    }
    public interface OnErrorListener {
        void OnError(VolleyError error);
    }

    public ApiFetcher setOnResponseLister(OnResponseListener listener, OnErrorListener errorListener) {
        this.listener = listener;
        this.errorListener = errorListener;
        return this;
    }
    public ApiFetcher fetch(String url) {
        if (url.equals(this.url)) return  this;
        this.url = url;
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if(listener != null) {
                    try {
                        listener.OnResponse(response);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(errorListener !=null) {
                    errorListener.OnError(error);
                }
            }
        });
        requestQueue.add(jsonObjectRequest);
        return this;
    }
}
