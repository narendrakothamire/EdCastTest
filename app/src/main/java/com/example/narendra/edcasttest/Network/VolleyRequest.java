package com.example.narendra.edcasttest.Network;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by narendra on 10/9/15.
 */
public class VolleyRequest extends JsonArrayRequest {

    private static final int DEFAULT_MAX_RETRIES = 0;
    private static final float DEFAULT_BACKOFF_MULT = 1f;
    private static final int DEFAULT_TIME_OUT = (int) TimeUnit.SECONDS.toMillis(20);

    public VolleyRequest(int method, String url, JSONObject requestBody, Response.Listener<JSONArray> listener, Response.ErrorListener errorListener) {
        super(method, url, requestBody, listener, errorListener);
        setRetryPolicy(new DefaultRetryPolicy(this.DEFAULT_TIME_OUT, this.DEFAULT_MAX_RETRIES, this.DEFAULT_BACKOFF_MULT));
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Accept", "application/json");
        return headers;
    }


}
