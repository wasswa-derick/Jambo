package com.rosen.jambo.views.articles;


import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.rosen.jambo.R;
import com.rosen.jambo.databinding.FragmentMainBinding;
import com.rosen.jambo.utils.NetworkConnectionDetector;
import com.rosen.jambo.utils.Roboto;
import com.rosen.jambo.views.bookmarks.Bookmark;
import com.rosen.jambo.views.bookmarks.BookmarksActivity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class MainFragment extends Fragment{

    RecyclerView articlesRecyclerView;

    StaggeredGridLayoutManager staggeredGridLayoutManager;
    LinearLayoutManager linearLayoutManager;

    ArticlesAdapter articlesAdapter;
    List<Article> articleList = new ArrayList<>();
    ArticlesViewModel articlesViewModel;

    //Font Style Preferences
    private SharedPreferences.OnSharedPreferenceChangeListener prefListener;
    SharedPreferences prefs;

    //  Broadcast event for internet connectivity
    BroadcastReceiver networkStateReceiver;

    FragmentMainBinding binding;
    private ActionMode actionMode = null;
    ActionModeCallback actionModeCallback = new ActionModeCallback();
    Roboto roboto;

    public MainFragment(){}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        articlesViewModel = ViewModelProviders.of(this, new ArticleViewModelFactory(requireActivity().getApplication())).get(ArticlesViewModel.class);

        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        linearLayoutManager = new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false);

        //listener on changed sort text preference:
        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

        roboto = new Roboto(requireActivity());

        prefs.registerOnSharedPreferenceChangeListener(prefListener);
    }

    @SuppressLint("CheckResult")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false);
        View view = binding.getRoot();

        binding.setViewModel(articlesViewModel);

        articlesRecyclerView = view.findViewById(R.id.articles_list);
        articlesAdapter = new ArticlesAdapter(requireActivity(), articleList);
        articlesRecyclerView.setAdapter(articlesAdapter);
        articlesRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        checkNetworkConnection();

        getArticleDetails();

        return view;
    }

    public void getOfflineArticles(String articleTag){
        if (!articleList.isEmpty()) {
            articleList.clear();
        }
        articleList.addAll(articlesViewModel.getOfflineArticlesByTag(articleTag));
        articlesAdapter.notifyDataSetChanged();
    }

    private void checkNetworkConnection() {
        // Network Utility
        networkStateReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Boolean connection = new NetworkConnectionDetector(requireActivity().getApplicationContext()).InternetConnectionStatus();
                if (!connection) {
                    if (articleList.isEmpty()) {
                        Toast.makeText(context, "offline articles still here", Toast.LENGTH_SHORT).show();
                        getOfflineArticles(requireActivity().getTitle().toString());
                    }
                } else {
                    fetchAPIArticles();
                }
            }
        };
        requireActivity().registerReceiver(networkStateReceiver, new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));
    }

    private void fetchAPIArticles() {
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
                        articlesViewModel.saveTagArticles(articles, requireActivity().getTitle().toString());
                    }

                    @SuppressLint("LogNotTimber")
                    @Override
                    public void onError(Throwable e) {
                        Log.d("ERROR", e.getLocalizedMessage());
                    }

                    @Override
                    public void onComplete() {
                        articlesAdapter.notifyDataSetChanged();
                    }
                });

    }

    private void getArticleDetails() {
        articlesRecyclerView.addOnItemTouchListener(new ArticleListClick(requireActivity(), new ArticleListClick.OnItemClickListener() {
            @Override
            public void onItemClick(View childView, int position) {

                if (actionMode != null) {
                    childView.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
                    toggleArticleSelection(position);
                } else {
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
            }

            @Override
            public void onItemLongPress(View childView, int position) {
                if (actionMode == null) {
                    actionMode = ((AppCompatActivity) requireActivity()).startSupportActionMode(actionModeCallback);
                }

                childView.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
                toggleArticleSelection(position);
            }
        }));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_quilt) {
            articlesRecyclerView.setLayoutManager(staggeredGridLayoutManager);
        } else if (id == R.id.action_list) {
            articlesRecyclerView.setLayoutManager(linearLayoutManager);
        } else if (id == R.id.action_text_size) {
            showTextSize();
        } else if (id == R.id.action_text_style) {
            showTextStyle();
        } else if (id == R.id.action_bookmarks) {
            startActivity(new Intent(requireActivity(), BookmarksActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    // show the dialog text style
    public void showTextStyle() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity(), R.style.AppCompatAlertDialogStyle);
        LayoutInflater inflater = LayoutInflater.from(requireActivity());
        View dl = inflater.inflate(R.layout.text_style, null);

        RadioButton robotoButton = dl.findViewById(R.id.roboto);
        robotoButton.setTypeface(roboto.getLightRoboto());
        RadioButton journal = dl.findViewById(R.id.journal);
        journal.setTypeface(roboto.getJournal());
        RadioButton dancing = dl.findViewById(R.id.dancing);
        dancing.setTypeface(roboto.getDancing());
        RadioButton walkway = dl.findViewById(R.id.walkway);
        walkway.setTypeface(roboto.getWalkWay());
        RadioButton gooddog = dl.findViewById(R.id.gooddog);
        gooddog.setTypeface(roboto.getGoodDog());
        RadioButton lobster = dl.findViewById(R.id.lobster);
        lobster.setTypeface(roboto.getLobster());

        String style = prefs.getString("text_style", "1");
        if (style.equalsIgnoreCase("1")) {
            robotoButton.setChecked(true);
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
                    default:
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
        final AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity(), R.style.AppCompatAlertDialogStyle);
        LayoutInflater inflater = LayoutInflater.from(requireActivity());
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
                    default:
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

    private void updateActionModeTitle() {
        if (actionMode != null) {
            if (articlesAdapter.getSelectedItemCount() == 0) {
                actionMode.finish();
            } else {
                String title = getString(R.string.selected_count, articlesAdapter.getSelectedItemCount() + "");
                actionMode.setTitle(title);
                actionMode.invalidate();
            }
        }
    }

    private void toggleArticleSelection(int idx) {
        articlesAdapter.toggleSelection(idx);
        updateActionModeTitle();
    }

    public void bookmarkArticles() {
        for (int i = 0; i <articleList.size(); i++) {
            if (articlesAdapter.getSelectedItems().contains(i)) {
                Bookmark bookmark = new Bookmark();
                bookmark.setArticleID(articleList.get(i).getTitle());
                articlesViewModel.bookArticles(bookmark);
            }
        }
        actionMode.finish();
        Toast.makeText(requireActivity(), "Article(s) bookmarked.", Toast.LENGTH_LONG).show();
    }

    private class ActionModeCallback implements ActionMode.Callback {

        @SuppressWarnings("unused")
        private final String TAG = ActionModeCallback.class.getSimpleName();

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate (R.menu.bookmark_articles, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_bookmark_article:
                    bookmarkArticles();
                    return true;
                case R.id.action_select_all:
                    item.setChecked(!item.isChecked());
                    articlesAdapter.toggleSelectionAll(item.isChecked());
                    updateActionModeTitle();
                    return true;
                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            actionMode = null;
            articlesAdapter.clearSelections();
        }
    }

}
