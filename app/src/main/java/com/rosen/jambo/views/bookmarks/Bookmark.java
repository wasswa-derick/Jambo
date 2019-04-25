package com.rosen.jambo.views.bookmarks;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.rosen.jambo.views.articles.Article;

@Entity(tableName = "bookmarks", foreignKeys = @ForeignKey(entity = Article.class, parentColumns = "title", childColumns = "articleID"))
public class Bookmark {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "articleID", index = true)
    private String articleID;

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    public String getArticleID() {
        return articleID;
    }

    public void setArticleID(String articleID) {
        this.articleID = articleID;
    }

}
