package com.rosen.jambo.domain.data.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.rosen.jambo.views.articles.Article;

import java.util.List;

/**
 * Created by Derick W on 16,March,2019
 * Github: @wasswa-derick
 * Andela (Kampala, Uganda)
 */
@Dao
public interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Article article);

    @Query("SELECT * from article WHERE id = :articleTag  ORDER BY publishedAt ASC")
    List<Article> getAllArticles(String articleTag);

    @Query("DELETE FROM article WHERE id = :articleTag")
    public void deleteArticleBy(String articleTag);

}
