package com.example.guardiannewslast;

public class Article {    /// create object to be inserted into database

    protected long id;
    protected String title;
    protected String section;
    protected String date;
    protected String url;

//constructors
    public Article(long i, String t, String s, String d, String u) {
        id = i;
        title = t;
        section = s;
        date = d;
        url = u;

    }

    public void update(String t, String s, String d, String u) {
        title = t;
        section = s;
        date = d;
        url = u;

    }
    /// chaining contructor
    public Article(String t, String s, String d, String u) {
        this(0, t, s, d, u);
    }

    //getters
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
