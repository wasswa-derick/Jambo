package com.rosen.jambo.domain.data.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.rosen.jambo.views.articles.Article;
import com.rosen.jambo.views.bookmarks.Bookmark;

/**
 * Created by Derick W on 16,March,2019
 * Github: @wasswa-derick
 * Andela (Kampala, Uganda)
 */
@Database(entities = {Article.class, Bookmark.class}, version = 1, exportSchema = false)
public abstract class ArticleDatabase extends RoomDatabase {

    public abstract ArticleDao articleDao();

    public abstract BookmarkDao bookmarkDao();

    private static volatile ArticleDatabase INSTANCE;

    public static ArticleDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ArticleDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            ArticleDatabase.class,
                            "articles")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
