package model;

import java.util.List;

public class RelComment {
    private List<Comment> commentList;
    private String rate;

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    @Override
    public String toString() {
        return "RelComment{" +
                "commentList=" + commentList +
                ", rate=" + rate +
                '}';
    }
}
