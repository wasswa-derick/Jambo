package com.rosen.jambo.views.articles;


import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rosen.jambo.R;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class MainFragment extends Fragment {

    RecyclerView articlesRecyclerView;

    StaggeredGridLayoutManager staggeredGridLayoutManager;
    LinearLayoutManager linearLayoutManager;

    ArticlesAdapter articlesAdapter;
    List<Article> articleList = new ArrayList<>();
    ArticlesViewModel articlesViewModel;

    //Font Style Preferences
    private SharedPreferences.OnSharedPreferenceChangeListener prefListener;
    SharedPreferences prefs;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        articlesViewModel = ViewModelProviders.of(this).get(ArticlesViewModel.class);

        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        linearLayoutManager = new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false);

        //listener on changed sort text preference:
        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());


        prefListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
                if(key.equals("text_style")){
                    Log.d("Prefernces Changes", "Yes");
                    //setFont(holder, prefs.getString("text_style","1"));
                }
            }
        };
        prefs.registerOnSharedPreferenceChangeListener(prefListener);
    }

    @SuppressLint("CheckResult")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        articlesRecyclerView = view.findViewById(R.id.articles_list);

        articlesAdapter = new ArticlesAdapter(requireActivity(), articleList);
        articlesRecyclerView.setAdapter(articlesAdapter);
        articlesRecyclerView.setLayoutManager(staggeredGridLayoutManager);


        articlesViewModel.getAllNewsArticles(requireActivity().getTitle().toString(), requireActivity().getResources().getString(R.string.news_api_key))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Article>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Article> articles) {
                        if (!articleList.isEmpty()) {
                            articleList.clear();
                        }

                        articleList.addAll(articles);
                        articlesAdapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        articlesRecyclerView.addOnItemTouchListener(new ArticleListClick(requireActivity(), new ArticleListClick.OnItemClickListener() {
            @Override
            public void onItemClick(View childView, int position) {
                Intent intent = new Intent(requireActivity(), ArticleDetails.class);
                Bundle bundle = new Bundle();

                Article article = articleList.get(position);
                bundle.putString("title", article.getTitle());
                bundle.putString("content", article.getContent());
                bundle.putString("description", article.getDescription());
                bundle.putString("author", article.getAuthor());
                bundle.putString("timestamp", article.getPublishedAt());
                bundle.putString("url", article.getUrl());
                bundle.putString("image", article.getUrlToImage());
                intent.putExtra("data", bundle);
                startActivity(intent);
            }

            @Override
            public void onItemLongPress(View childView, int position) {

            }
        }));


        return view;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_quilt) {
            articlesRecyclerView.setLayoutManager(staggeredGridLayoutManager);
        } else if (id == R.id.action_list) {
            articlesRecyclerView.setLayoutManager(linearLayoutManager);
        } else if (id == R.id.action_text_size) {
            showTextSize();
        } else if (id == R.id.action_text_style) {
            showTextStyle();
        }

        return super.onOptionsItemSelected(item);
    }

    // show the dialog text style
    public void showTextStyle() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AppCompatAlertDialogStyle);
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View dl = inflater.inflate(R.layout.text_style, null);

        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/RobotoRegular.ttf");
        Typeface typeface1 = Typeface.createFromAsset(getActivity().getAssets(), "fonts/LobsterTwoRegular.otf");
        Typeface typeface2 = Typeface.createFromAsset(getActivity().getAssets(), "fonts/journal.ttf");
        Typeface typeface3 = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Walkway.ttf");
        Typeface typeface5 = Typeface.createFromAsset(getActivity().getAssets(), "fonts/GoodDog.otf");
        Typeface typeface4 = Typeface.createFromAsset(getActivity().getAssets(), "fonts/DancingScriptRegular.otf");

        RadioButton roboto = dl.findViewById(R.id.roboto);
        roboto.setTypeface(typeface);
        RadioButton journal = dl.findViewById(R.id.journal);
        journal.setTypeface(typeface2);
        RadioButton dancing = dl.findViewById(R.id.dancing);
        dancing.setTypeface(typeface4);
        RadioButton walkway = dl.findViewById(R.id.walkway);
        walkway.setTypeface(typeface3);
        RadioButton gooddog = dl.findViewById(R.id.gooddog);
        gooddog.setTypeface(typeface5);
        RadioButton lobster = dl.findViewById(R.id.lobster);
        lobster.setTypeface(typeface1);

        String style = prefs.getString("text_style", "1");
        if (style.equalsIgnoreCase("1")) {
            roboto.setChecked(true);
        }else if (style.equalsIgnoreCase("2")) {
            journal.setChecked(true);
        }else if (style.equalsIgnoreCase("3")) {
            lobster.setChecked(true);
        }else if (style.equalsIgnoreCase("4")) {
            dancing.setChecked(true);
        }else if (style.equalsIgnoreCase("5")) {
            walkway.setChecked(true);
        }else if (style.equalsIgnoreCase("6")) {
            gooddog.setChecked(true);
        }

        RadioGroup text_styles = dl.findViewById(R.id.text_styles);
        text_styles.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId){
                    case R.id.roboto:
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString("text_style", "1");
                        editor.apply();
                        break;
                    case R.id.journal:
                        SharedPreferences.Editor editor1 = prefs.edit();
                        editor1.putString("text_style", "2");
                        editor1.apply();
                        break;
                    case R.id.lobster:
                        SharedPreferences.Editor editor2 = prefs.edit();
                        editor2.putString("text_style", "3");
                        editor2.apply();
                        break;
                    case R.id.gooddog:
                        SharedPreferences.Editor editor3 = prefs.edit();
                        editor3.putString("text_style", "6");
                        editor3.apply();
                        break;
                    case R.id.dancing:
                        SharedPreferences.Editor editor4 = prefs.edit();
                        editor4.putString("text_style", "4");
                        editor4.apply();
                        break;
                    case R.id.walkway:
                        SharedPreferences.Editor editor5 = prefs.edit();
                        editor5.putString("text_style", "5");
                        editor5.apply();
                        break;
                }

                articlesAdapter.notifyDataSetChanged();
            }
        });

        builder.setTitle("Choose text style.");
        builder.setView(dl);


        final AlertDialog customAlertDialog = builder.create();
        customAlertDialog.show();

    }

    public void showTextSize() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AppCompatAlertDialogStyle);
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View dl = inflater.inflate(R.layout.text_sizes, null);

        RadioButton normal = dl.findViewById(R.id.normal);
        RadioButton small = dl.findViewById(R.id.small);
        RadioButton medium = dl.findViewById(R.id.medium);
        RadioButton large = dl.findViewById(R.id.large);
        RadioButton xlarge = dl.findViewById(R.id.xlarge);


        String style = prefs.getString("text_style", "1");
        if (style.equalsIgnoreCase("1")) {
            normal.setChecked(true);
        }else if (style.equalsIgnoreCase("2")) {
            small.setChecked(true);
        }else if (style.equalsIgnoreCase("3")) {
            medium.setChecked(true);
        }else if (style.equalsIgnoreCase("4")) {
            large.setChecked(true);
        }else if (style.equalsIgnoreCase("5")) {
            xlarge.setChecked(true);
        }

        RadioGroup text_sizes = dl.findViewById(R.id.text_sizes);
        text_sizes.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId){
                    case R.id.normal:
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString("text_size", "1");
                        editor.apply();
                        break;
                    case R.id.small:
                        SharedPreferences.Editor editor1 = prefs.edit();
                        editor1.putString("text_size", "2");
                        editor1.apply();
                        break;
                    case R.id.medium:
                        SharedPreferences.Editor editor2 = prefs.edit();
                        editor2.putString("text_size", "3");
                        editor2.apply();
                        break;
                    case R.id.large:
                        SharedPreferences.Editor editor3 = prefs.edit();
                        editor3.putString("text_size", "4");
                        editor3.apply();
                        break;
                    case R.id.xlarge:
                        SharedPreferences.Editor editor4 = prefs.edit();
                        editor4.putString("text_size", "5");
                        editor4.apply();
                        break;
                }

                articlesAdapter.notifyDataSetChanged();
            }
        });

        builder.setTitle("Choose text syle.");
        builder.setView(dl);




        final AlertDialog customAlertDialog = builder.create();
        customAlertDialog.show();


    }

    private class PagerAdapter extends android.support.v4.view.PagerAdapter {

        @Override
        public int getCount() {
            return 6;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            // Create some layout params
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);

            // Create some text
            TextView textView = getTextView(container.getContext());
            textView.setText(String.valueOf(position));
            textView.setLayoutParams(layoutParams);

            RelativeLayout layout = new RelativeLayout(container.getContext());
            layout.setBackgroundColor(ContextCompat.getColor(container.getContext(), R.color.primaryColor));
            layout.setLayoutParams(layoutParams);

            layout.addView(textView);
            container.addView(layout);
            return layout;
        }

        private TextView getTextView(Context context) {
            TextView textView = new TextView(context);
            textView.setTextColor(Color.WHITE);
            textView.setTextSize(30);
            textView.setTypeface(Typeface.DEFAULT_BOLD);
            return textView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((RelativeLayout) object);
        }
    }
}
