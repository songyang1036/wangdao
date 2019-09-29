package model;

public class Comment {

    private int id;
    private double score;
    private String comment;
    private String content;
    private String time;
    private String ptime;

    private int orderId;//订单详情表id
    private int goodsId;
    private int goodsDetailId;//goodsSpecId
    private String specName;

    private int userId;
    private String token;
    private User user;


    public String getPtime() {
        return ptime;
    }

    public void setPtime(String ptime) {
        this.ptime = ptime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public int getGoodsDetailId() {
        return goodsDetailId;
    }

    public void setGoodsDetailId(int goodsDetailId) {
        this.goodsDetailId = goodsDetailId;
    }

    public String getSpecName() {
        return specName;
    }

    public void setSpecName(String specName) {
        this.specName = specName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", score=" + score +
                ", comment='" + comment + '\'' +
                ", time='" + time + '\'' +
                ", orderId=" + orderId +
                ", goodsId=" + goodsId +
                ", goodsDetailId=" + goodsDetailId +
                ", specName='" + specName + '\'' +
                ", userId=" + userId +
                ", token='" + token + '\'' +
                ", user=" + user +
                '}';
    }
}
