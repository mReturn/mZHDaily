package com.mreturn.zhihudaily.model;

/**
 * Created by mReturn
 * on 2017/5/24.
 */

public class StoryExtraBean {

    /**
     * vote_status : 0
     * popularity : 458
     * favorite : false
     * long_comments : 1
     * comments : 42
     * short_comments : 41
     */

    private int vote_status;
    private int popularity;
    private boolean favorite;
    private int long_comments;
    private int comments;
    private int short_comments;

    public int getVote_status() {
        return vote_status;
    }

    public void setVote_status(int vote_status) {
        this.vote_status = vote_status;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public int getLong_comments() {
        return long_comments;
    }

    public void setLong_comments(int long_comments) {
        this.long_comments = long_comments;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    public int getShort_comments() {
        return short_comments;
    }

    public void setShort_comments(int short_comments) {
        this.short_comments = short_comments;
    }
}
