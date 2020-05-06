package com.staradmin.android.tasku.Activities.Book;

public class Book {
    private String id;
    private String title;
    private String url_buku;
    private String url_cover;

    public Book(String id, String title, String url_buku, String url_cover) {
        this.id = id;
        this.title = title;
        this.url_buku = url_buku;
        this.url_cover = url_cover;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl_buku() {
        return url_buku;
    }

    public void setUrl_buku(String url_buku) {
        this.url_buku = url_buku;
    }

    public String getUrl_cover() {
        return url_cover;
    }

    public void setUrl_cover(String url_cover) {
        this.url_cover = url_cover;
    }




}
