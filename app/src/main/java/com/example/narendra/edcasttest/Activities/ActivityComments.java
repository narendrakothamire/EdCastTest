package com.example.narendra.edcasttest.Activities;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.narendra.edcasttest.Adapters.AdapterComments;
import com.example.narendra.edcasttest.Models.Comment;
import com.example.narendra.edcasttest.Network.VolleyRequest;
import com.example.narendra.edcasttest.R;
import com.example.narendra.edcasttest.application.EdCatTextApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ActivityComments extends AppCompatActivity {

    private static final String BASE_URL = "http://jsonplaceholder.typicode.com/posts/";
    private ListView commentsListView;
    private View indicatorLayout;
    private Context context;
    private AdapterComments adapterComments;
    private ArrayList<Comment> comments = new ArrayList<>();
    private int postId;

    private Response.Listener<JSONArray> successListener = new Response.Listener<JSONArray>() {
        @Override
        public void onResponse(JSONArray response) {
            try{
                for(int i=0; i<response.length(); i++){
                    Comment comment = new Comment();
                    JSONObject commentsJSONObj = response.getJSONObject(i);
                    comment.setId(commentsJSONObj.getInt("id"));
                    comment.setPostId(commentsJSONObj.getInt("postId"));
                    comment.setName(commentsJSONObj.getString("name"));
                    comment.setEmail(commentsJSONObj.getString("email"));
                    comment.setBody(commentsJSONObj.getString("body"));
                    comments.add(comment);
                }
                adapterComments.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }finally {
                toggleIndicator(View.GONE);
            }
        }
    };

    private Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            toggleIndicator(View.GONE);
            Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        context = this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Comments");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        commentsListView = (ListView)findViewById(R.id.comments_list_view);
        indicatorLayout = findViewById(R.id.indicator_frame_layout);

        adapterComments = new AdapterComments(context, comments);
        commentsListView.setAdapter(adapterComments);

        toggleIndicator(View.VISIBLE);
        String URL = BASE_URL + 1 + "/comments";
        VolleyRequest volleyRequest = new VolleyRequest(Request.Method.GET, "get_comments", URL, null, successListener, errorListener);
        EdCatTextApplication.getInstance().addToRequestQueue(volleyRequest);
    }

    private void toggleIndicator(int visible) {
        indicatorLayout.setVisibility(visible);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
