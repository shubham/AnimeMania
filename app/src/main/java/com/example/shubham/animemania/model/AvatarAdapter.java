package com.example.shubham.animemania.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.shubham.animemania.R;

/**
 * Adapter to display Avatar icons.
 * Created by shubham on 27/1/17.
 */
public class AvatarAdapter extends BaseAdapter {

    private static final Avatar[] mAvatars = Avatar.values();

    private final LayoutInflater mLayoutInflater;

    public AvatarAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (null == convertView) {
            convertView = mLayoutInflater.inflate(R.layout.avatar_item, parent, false);
        }
        ImageView mAvatarImageView = (ImageView) convertView.findViewById(R.id.avatar_imageView);
        setAvatar(mAvatarImageView, mAvatars[position]);
        return convertView;
    }

    private void setAvatar(ImageView imageView, Avatar avatar) {
        imageView.setImageResource(avatar.getDrawableId());
        imageView.setContentDescription(avatar.getNameForAccessibility());
    }

    @Override
    public int getCount() {
        return mAvatars.length;
    }

    @Override
    public Object getItem(int position) {
        return mAvatars[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}