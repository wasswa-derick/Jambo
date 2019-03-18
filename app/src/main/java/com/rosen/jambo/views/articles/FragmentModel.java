package com.rosen.jambo.views.articles;

/**
 * Created by Derick W on 16,March,2019
 * Github: @wasswa-derick
 * Andela (Kampala, Uganda)
 */
public class FragmentModel {

    Boolean loadingArticles;

    Boolean emptyList;

    public Boolean getLoadingArticles() {
        return loadingArticles;
    }

    public void setLoadingArticles(Boolean loadingArticles) {
        this.loadingArticles = loadingArticles;
    }

    public Boolean getEmptyList() {
        return emptyList;
    }

    public void setEmptyList(Boolean emptyList) {
        this.emptyList = emptyList;
    }
}
