package com.example.narendra.edcasttest.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.narendra.edcasttest.Adapters.AdapterPosts;
import com.example.narendra.edcasttest.Models.Post;
import com.example.narendra.edcasttest.Network.VolleyRequest;
import com.example.narendra.edcasttest.R;
import com.example.narendra.edcasttest.application.EdCatTextApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Narendra on 1/21/2016.
 */
public class FragmentPosts extends Fragment {

    private static final String URL = "http://jsonplaceholder.typicode.com/posts";
    private Context context;
    private RecyclerView postsRecyclerView;
    private AdapterPosts adapterPosts;
    private ArrayList<Post> posts = new ArrayList<>();


    private Response.Listener<JSONArray> successListener = new Response.Listener<JSONArray>() {
        @Override
        public void onResponse(JSONArray response) {
            try {

                for(int i=0; i<response.length(); i++){
                    Post post = new Post();
                    JSONObject postJSONObj = response.getJSONObject(i);
                    post.setId(postJSONObj.getInt("id"));
                    post.setUserId(postJSONObj.getInt("userId"));
                    post.setTitle(postJSONObj.getString("title"));
                    post.setBody(postJSONObj.getString("body"));
                    posts.add(post);
                }
                adapterPosts.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }finally {

            }

        }
    };
    private Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
        }
    };


    public FragmentPosts() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_posts, null, false);

        postsRecyclerView = (RecyclerView)view.findViewById(R.id.posts_recycler_view);

        adapterPosts = new AdapterPosts(context, posts);
        postsRecyclerView.setAdapter(adapterPosts);

        VolleyRequest volleyRequest = new VolleyRequest(Request.Method.GET, URL, null, successListener, errorListener);
        EdCatTextApplication.getInstance().addToRequestQueue(volleyRequest);

        return view;
    }
}
