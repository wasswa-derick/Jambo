package com.rosen.jambo.domain.databinding;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.rosen.jambo.R;
import com.squareup.picasso.Picasso;

/**
 * Created by Derick W on 01,March,2019
 * Github: @wasswa-derick
 * Andela (Kampala, Uganda)
 */
public class BindingAdapters {

    @BindingAdapter({"bind:imageUrl"})
    public static void setImageUrl(ImageView imageView, String url) {

        Picasso.get()
                .load(url)
                .placeholder(R.drawable.cat)
                .into(imageView);

    }

}
