package com.rosen.jambo.views.articles;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.rosen.jambo.R;
import com.rosen.jambo.databinding.ArticleImageRowBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Derick W on 01,March,2019
 * Github: @wasswa-derick
 * Andela (Kampala, Uganda)
 */
public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.ArticleHolder> {

    public Context context;
    private List<Article> articleList;
    private LayoutInflater layoutInflater;
    private SparseBooleanArray selectedItems;

    public ArticlesAdapter(Context context, List<Article> articleList) {
        this.context = context;
        this.articleList = articleList;
        this.layoutInflater = LayoutInflater.from(context);
        this.selectedItems = new SparseBooleanArray();
    }

    @NonNull
    @Override
    public ArticleHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ArticleImageRowBinding articleImageRowBinding =
                DataBindingUtil.inflate(layoutInflater, R.layout.article_image_row, viewGroup, false);
        return new ArticleHolder(articleImageRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleHolder articleHolder, int position) {
        Article article = articleList.get(position);
        article.selected.set(getSelectedItems().contains(position));
        articleHolder.bind(article);
        articleHolder.itemView.setActivated(getSelectedItems().contains(position));
    }

    public void updateArticles(List<Article> articles) {
        this.articleList = articles;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }

    public class ArticleHolder extends RecyclerView.ViewHolder {
        private final ArticleImageRowBinding binding;

        ArticleHolder(@NonNull ArticleImageRowBinding articleImageRowBinding) {
            super(articleImageRowBinding.getRoot());
            this.binding = articleImageRowBinding;
        }

        public void bind(Article article) {
            binding.setArticle(article);
            binding.executePendingBindings();
        }
    }

    void toggleSelection(int pos) {
        // monitor the changes of the item states either is selected or is deselected
        if (selectedItems.get(pos, false)) {
            selectedItems.delete(pos);
        }
        else {
            selectedItems.put(pos, true);
        }
        notifyItemChanged(pos);
    }

    void toggleSelectionAll(Boolean isSelected) {
        for (int position = 0; position < articleList.size(); position++) {
            if (isSelected) {
                selectedItems.put(position, true);
            } else {
                selectedItems.delete(position);
            }

            notifyItemChanged(position);
        }
    }

    void clearSelections() {
        //clear a selected item
        selectedItems.clear();
        notifyDataSetChanged();
    }

    //get the number of items selected
    int getSelectedItemCount() {
        return selectedItems.size();
    }

    public List<Integer> getSelectedItems() {
        List<Integer> items = new ArrayList<Integer>(selectedItems.size());
        for (int i = 0; i < selectedItems.size(); i++) {
            items.add(selectedItems.keyAt(i));
        }
        return items;
    }

}
