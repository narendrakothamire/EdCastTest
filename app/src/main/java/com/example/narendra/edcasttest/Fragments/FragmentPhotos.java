package com.example.narendra.edcasttest.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.narendra.edcasttest.Activities.ActivityPhoto;
import com.example.narendra.edcasttest.Adapters.AdapterPhotosGrid;
import com.example.narendra.edcasttest.Adapters.AdapterPosts;
import com.example.narendra.edcasttest.Constants;
import com.example.narendra.edcasttest.Models.Photo;
import com.example.narendra.edcasttest.Models.Post;
import com.example.narendra.edcasttest.Network.VolleyRequest;
import com.example.narendra.edcasttest.R;
import com.example.narendra.edcasttest.application.EdCatTextApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Narendra on 1/23/2016.
 */
public class FragmentPhotos extends Fragment {

    private static final String URL = "http://jsonplaceholder.typicode.com/photos";
    private GridView photosGridView;
    private View indicatorLayout;
    private Context context;
    private ArrayList<Photo> photos = new ArrayList<>();
    private AdapterPhotosGrid adapterPhotos;
    private Response.Listener<JSONArray> successListener = new Response.Listener<JSONArray>() {
        @Override
        public void onResponse(JSONArray response) {
            try {

                for(int i=0; i<response.length(); i++){
                    Photo photo = new Photo();
                    JSONObject photosJSONObj = response.getJSONObject(i);
                    photo.setId(photosJSONObj.getInt("id"));
                    photo.setAlbumId(photosJSONObj.getInt("albumId"));
                    photo.setTitle(photosJSONObj.getString("title"));
                    photo.setThumbnailUrl(photosJSONObj.getString("thumbnailUrl"));
                    photo.setUrl(photosJSONObj.getString("url"));
                    photos.add(photo);
                }
                adapterPhotos.notifyDataSetChanged();
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

    private GridView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(context, ActivityPhoto.class);
            intent.putExtra(Constants.ARG_URL, adapterPhotos.getItem(position).getUrl());
            startActivity(intent);
        }
    };

    public FragmentPhotos() {
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
        getActivity().setTitle("Photos");
    }

    @Override
    public void onResume() {
        super.onResume();
        setTitle();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photos, container, false);

        photosGridView = (GridView)view.findViewById(R.id.photos_grid_view);
        photosGridView.setOnItemClickListener(itemClickListener);
        indicatorLayout = view.findViewById(R.id.indicator_frame_layout);

        adapterPhotos = new AdapterPhotosGrid(context, photos);
        photosGridView.setAdapter(adapterPhotos);

        toggleIndicator(View.VISIBLE);
        VolleyRequest volleyRequest = new VolleyRequest(Request.Method.GET, "get_photos", URL, null, successListener, errorListener);
        EdCatTextApplication.getInstance().addToRequestQueue(volleyRequest);

        return view;
    }

    private void toggleIndicator(int visible) {
        indicatorLayout.setVisibility(visible);
    }
}
