package com.example.narendra.edcasttest.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.narendra.edcasttest.Models.Post;
import com.example.narendra.edcasttest.R;

import java.util.ArrayList;

/**
 * Created by Narendra on 1/23/2016.
 */
public class AdapterPosts extends BaseAdapter {

    private LayoutInflater inflator;
    private Context context;
    private ArrayList<Post> posts;

    public AdapterPosts(Context context, ArrayList<Post> posts) {
        this.context = context;
        this.posts = posts;
        inflator = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return posts.size();
    }

    @Override
    public Post getItem(int position) {
        return posts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PostViewHolder postViewHolder;
        if(convertView == null){
            convertView = inflator.inflate(R.layout.item_posts_layout, null, false);
            postViewHolder = new PostViewHolder();
            postViewHolder.titleTextView = (TextView) convertView.findViewById(R.id.post_title_text_view);
            postViewHolder.bodyTextView = (TextView) convertView.findViewById(R.id.post_body_text_view);
            convertView.setTag(postViewHolder);
        }else{
            postViewHolder = (PostViewHolder) convertView.getTag();
        }

        postViewHolder.titleTextView.setText(getItem(position).getTitle());
        postViewHolder.bodyTextView.setText(getItem(position).getBody());

        return convertView;
    }

    class PostViewHolder{
        TextView titleTextView, bodyTextView;
    }
}
