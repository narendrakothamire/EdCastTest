package com.example.narendra.edcasttest.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.narendra.edcasttest.Models.Post;
import com.example.narendra.edcasttest.R;

import java.util.ArrayList;

/**
 * Created by Narendra on 1/21/2016.
 */
public class AdapterPosts extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private LayoutInflater inflator;
    private Context context;
    private ArrayList<Post> posts;

    public AdapterPosts(Context context, ArrayList<Post> posts) {
        this.context = context;
        this.posts = posts;
        inflator = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflator.inflate(R.layout.item_posts_layout, null, false);
        return new PostsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        PostsViewHolder postsViewHolder = (PostsViewHolder) holder;
        postsViewHolder.titleTextView.setText(posts.get(position).getTitle());
        postsViewHolder.bodyTextView.setText(posts.get(position).getBody());
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    class PostsViewHolder extends RecyclerView.ViewHolder{

        TextView titleTextView, bodyTextView;

        public PostsViewHolder(View itemView) {
            super(itemView);
            titleTextView = (TextView) itemView.findViewById(R.id.post_title_text_view);
            bodyTextView = (TextView) itemView.findViewById(R.id.post_body_text_view);
        }
    }
}
