package com.rosen.jambo.views.articles;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.rosen.jambo.R;
import com.rosen.jambo.databinding.ArticleImageRowBinding;

import java.util.List;

/**
 * Created by Derick W on 01,March,2019
 * Github: @wasswa-derick
 * Andela (Kampala, Uganda)
 */
public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.ArticleHolder> {

    public Context context;
    public List<Article> articleList;
    public LayoutInflater layoutInflater;

    public ArticlesAdapter(Context context, List<Article> articleList) {
        this.context = context;
        this.articleList = articleList;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ArticleHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ArticleImageRowBinding articleImageRowBinding =
                DataBindingUtil.inflate(layoutInflater, R.layout.article_image_row, viewGroup, false);
        return new ArticleHolder(articleImageRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleHolder articleHolder, int i) {
        Article article = articleList.get(i);
        articleHolder.bind(article);
    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }

    public class ArticleHolder extends RecyclerView.ViewHolder {
        private final ArticleImageRowBinding binding;

        public ArticleHolder(@NonNull ArticleImageRowBinding articleImageRowBinding) {
            super(articleImageRowBinding.getRoot());
            this.binding = articleImageRowBinding;
        }

        public void bind(Article article) {
            binding.setArticle(article);
            binding.executePendingBindings();
        }
    }

}
