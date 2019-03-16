package com.rosen.jambo.utils;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.widget.TextView;

import com.rosen.jambo.R;

/**
 * Created by Derick W on 15,March,2019
 * Github: @wasswa-derick
 * Andela (Kampala, Uganda)
 */
public class LinkfyTextViewAdapter {
    @BindingAdapter({"bind:webLink"})
    public static void linkfyTextView(TextView textView, String text) {
        Context context = textView.getContext();
        textView.setTextColor(context.getResources().getColor(R.color.accentColor));
        textView.setTypeface(null, Typeface.BOLD);
        textView.setPaintFlags(textView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    }
}
