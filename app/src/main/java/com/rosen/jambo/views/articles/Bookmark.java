package com.rosen.jambo.views.articles;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Derick W on 05,April,2019
 * Github: @wasswa-derick
 * Andela (Kampala, Uganda)
 */
@Entity(tableName = "bookmarks", foreignKeys = @ForeignKey(entity = Article.class, parentColumns = "title", childColumns = "articleID"))
public class Bookmark {

    @PrimaryKey
    @ColumnInfo(name = "id")
    private String id;

    @ColumnInfo(name = "articleID", index = true)
    private String articleID;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getArticleID() {
        return articleID;
    }

    public void setArticleID(String articleID) {
        this.articleID = articleID;
    }
}
