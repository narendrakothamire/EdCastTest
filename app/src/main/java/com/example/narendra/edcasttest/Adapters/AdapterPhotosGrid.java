package com.example.narendra.edcasttest.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.narendra.edcasttest.Models.Photo;
import com.example.narendra.edcasttest.Models.Post;
import com.example.narendra.edcasttest.R;

import java.util.ArrayList;

/**
 * Created by Narendra on 1/23/2016.
 */
public class AdapterPhotosGrid extends BaseAdapter {

    private LayoutInflater inflator;
    private Context context;
    private ArrayList<Photo> photos;

    public AdapterPhotosGrid(Context context, ArrayList<Photo> photos) {
        this.context = context;
        this.photos = photos;
        inflator = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return photos.size();
    }

    @Override
    public Photo getItem(int position) {
        return photos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PhotosViewHolder photosViewHolder;
        if(convertView == null){
            convertView = inflator.inflate(R.layout.item_photos_grid, null, false);
            photosViewHolder = new PhotosViewHolder();
            photosViewHolder.imageView = (ImageView) convertView.findViewById(R.id.photo_image_view);
            convertView.setTag(photosViewHolder);
        }else{
            photosViewHolder = (PhotosViewHolder) convertView.getTag();
            photosViewHolder.imageView.setImageBitmap(null);
        }
        Glide.with(context).load(getItem(position).getThumbnailUrl()).into(photosViewHolder.imageView);
        return convertView;
    }



    class PhotosViewHolder{
        ImageView imageView;
    }
}
