package com.example.narendra.edcasttest.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.narendra.edcasttest.Activities.ActivityComments;
import com.example.narendra.edcasttest.Adapters.AdapterPosts;
import com.example.narendra.edcasttest.Constants;
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
    private ListView postsListView;
    private View indicatorLayout;
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

    private ListView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            Intent intent = new Intent(context, ActivityComments.class);
            intent.putExtra(Constants.ARG_POST_ID, adapterPosts.getItem(position).getId());
            startActivity(intent);
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


    public void setTitle() {
        getActivity().setTitle("Posts");
    }

    @Override
    public void onResume() {
        super.onResume();
        setTitle();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_posts, container, false);

        postsListView = (ListView)view.findViewById(R.id.posts_list_view);
        postsListView.setOnItemClickListener(itemClickListener);
        indicatorLayout = view.findViewById(R.id.indicator_frame_layout);

        adapterPosts = new AdapterPosts(context, posts);
        postsListView.setAdapter(adapterPosts);

        toggleIndicator(View.VISIBLE);
        VolleyRequest volleyRequest = new VolleyRequest(Request.Method.GET, "get_posts", URL, null, successListener, errorListener);
        EdCatTextApplication.getInstance().addToRequestQueue(volleyRequest);

        return view;
    }

    private void toggleIndicator(int visible) {
        indicatorLayout.setVisibility(visible);
    }

    @Override
    public void onStop() {
        super.onStop();
        EdCatTextApplication.getInstance().cancelPendingRequests("get_posts");
    }
}
