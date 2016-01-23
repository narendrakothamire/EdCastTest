package com.example.narendra.edcasttest.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.narendra.edcasttest.Models.Comment;
import com.example.narendra.edcasttest.Models.Post;
import com.example.narendra.edcasttest.R;

import java.util.ArrayList;

/**
 * Created by Narendra on 1/23/2016.
 */
public class AdapterComments extends BaseAdapter {

    private LayoutInflater inflator;
    private Context context;
    private ArrayList<Comment> comments;

    public AdapterComments(Context context, ArrayList<Comment> comments) {
        this.context = context;
        this.comments = comments;
        inflator = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return comments.size();
    }

    @Override
    public Comment getItem(int position) {
        return comments.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CommentViewHolder postViewHolder;
        if(convertView == null){
            convertView = inflator.inflate(R.layout.item_comments_layout, null, false);
            postViewHolder = new CommentViewHolder();
            postViewHolder.nameTextView = (TextView) convertView.findViewById(R.id.comments_name_text_view);
            postViewHolder.emailTextView = (TextView) convertView.findViewById(R.id.comments_email_text_view);
            postViewHolder.bodyTextView = (TextView) convertView.findViewById(R.id.comments_body_text_view);
            convertView.setTag(postViewHolder);
        }else{
            postViewHolder = (CommentViewHolder) convertView.getTag();
        }

        postViewHolder.nameTextView.setText(getItem(position).getName());
        postViewHolder.emailTextView.setText(getItem(position).getEmail());
        postViewHolder.bodyTextView.setText(getItem(position).getBody());

        return convertView;
    }

    class CommentViewHolder{
        TextView nameTextView, emailTextView, bodyTextView;
    }
}
