package com.rosen.jambo.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.BindingAdapter;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.widget.CardView;
import android.util.TypedValue;
import android.widget.TextView;

import java.util.Random;

/**
 * Created by Derick W on 15,March,2019
 * Github: @wasswa-derick
 * Andela (Kampala, Uganda)
 */
public class ArticleContentPreferenceAdapter {

    static SharedPreferences prefs;
    static SharedPreferences.OnSharedPreferenceChangeListener prefListener;

    @BindingAdapter({"bind:textPref"})
    public static void setTextPrefs(TextView textView, String text) {
        Context context = textView.getContext();
        prefs = PreferenceManager.getDefaultSharedPreferences(context);

        setFont(textView, prefs.getString("text_style","1"), context);
        setTextSize(textView, prefs.getString("text_size", "1"));

        prefListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
                if(key.equals("text_style")){
                    setFont(textView, prefs.getString("text_style","1"), context);
                } else if (key.equals("text_size")) {
                    setTextSize(textView, prefs.getString("text_size", "1"));
                }
            }
        };

        prefs.registerOnSharedPreferenceChangeListener(prefListener);

    }

    @BindingAdapter({"bind:cardPref"})
    public static void setCardBackground(CardView cardView, String text) {
        setRandomBackground(cardView);
    }


    //method to set the random background
    public static void setRandomBackground(CardView cardView){

        Random random = new Random();
        int position = random.nextInt(400 - 1) + 1;
        if (position < 50) {
            cardView.setCardBackgroundColor(Color.parseColor("#fde0dc"));
        }else if ((position > 50) && (position < 100)) {
            cardView.setCardBackgroundColor(Color.parseColor("#f3e5f5"));
        }else if ((position > 100) && (position < 150)) {
            cardView.setCardBackgroundColor(Color.parseColor("#e8eaf6"));
        }else if ((position > 150) && (position < 200)) {
            cardView.setCardBackgroundColor(Color.parseColor("#e1f5f3"));
        }else if ((position > 200) && (position < 250)) {
            cardView.setCardBackgroundColor(Color.parseColor("#e0f7fa"));
        }else if ((position > 250) && (position < 300)) {
            cardView.setCardBackgroundColor(Color.parseColor("#d0f8ce"));
        }else if ((position > 300) && (position < 350)) {
            cardView.setCardBackgroundColor(Color.parseColor("#f9fbe7"));
        }else if ((position > 350) && (position < 400)) {
            cardView.setCardBackgroundColor(Color.parseColor("#fbe9e7"));
        }
    }

    //method to set the random font
    public static void setFont(TextView textView, String color, Context context){

        Roboto roboto = new Roboto(context);
        if (color.equalsIgnoreCase("1")) {
            textView.setTypeface(roboto.getLightRoboto());
        }else if (color.equalsIgnoreCase("2")) {
            textView.setTypeface(roboto.getJournal());
        }else if (color.equalsIgnoreCase("3")) {
            textView.setTypeface(roboto.getLobster());
        }else if (color.equalsIgnoreCase("4")) {
            textView.setTypeface(roboto.getStencilLight());
        }else if (color.equalsIgnoreCase("5")) {
            textView.setTypeface(roboto.getWalkWay());
        }else if (color.equalsIgnoreCase("6")) {
            textView.setTypeface(roboto.getGoodDog());
        }
    }

    //method to set the random font
    public static void setTextSize(TextView textView, String size){

        if (size.equalsIgnoreCase("1")) {
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 32);
        }else if (size.equalsIgnoreCase("2")) {
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
        }else if (size.equalsIgnoreCase("3")) {
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 38);
        }else if (size.equalsIgnoreCase("4")) {
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 40);
        }else if (size.equalsIgnoreCase("5")) {
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 48);
        }
    }
}


