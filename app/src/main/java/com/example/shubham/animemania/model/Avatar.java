package com.example.shubham.animemania.model;

import android.support.annotation.DrawableRes;

import com.example.shubham.animemania.R;

/**
 * The available avatars with their corresponding drawable resource ids.
 *
 * Created by shubham on 25/1/17.
 */
public enum Avatar {

    ONE(R.drawable.bitmap_avatar_1),
    TWO(R.drawable.bitmap_avatar_2),
    THREE(R.drawable.bitmap_avatar_3),
    FOUR(R.drawable.bitmap_avatar_4),
    FIVE(R.drawable.bitmap_avatar_5),
    SIX(R.drawable.bitmap_avatar_6),
    SEVEN(R.drawable.bitmap_avatar_7),
    EIGHT(R.drawable.bitmap_avatar_8);
    private static final String TAG = "Avatar";

    private final int mResId;

    Avatar(@DrawableRes final int resId) {
        mResId = resId;
    }

    /**
     * Method for getting Drawable id of selected image
     * @return :id
     */
    @DrawableRes
    public int getDrawableId() {
        return mResId;
    }

    /**
     * Method For checking
     * @return :string name
     */
    public String getNameForAccessibility() {
        return TAG + " " + ordinal() + 1;
    }
}