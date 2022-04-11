package com.example.guardiannewslast;

public class Article {

    protected long id;
    protected String title;
    protected String section;
    protected String url;
    protected String date;


    public Article(long i, String t, String s, String u, String d) {
        id = i;
        title = t;
        section = s;
        url = u;
        date = d;
    }

    public void update(String t, String s, String u, String d) {
        title = t;
        section = s;
        url = u;
        date = d;
    }

    public Article(String t, String s, String u, String d) {
        this(0, t, s, u, d);
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getSection() {
        return section;
    }

    public String getDate() {
        return date;
    }

    public String getUrl() {
        return url;
    }


}
