package model;

public class Message {
    private int id;

    private int userId;

    private int rid;
    private String asker;
    private String time;
    private String rtime;
    private ReplyMsg reply;

    private int goodsId;
    private String msg;
    private String token;

    private String content;

    private String replyContent;

    private int state;

    private String createtime;

    private User user;
    private String headimg;
    private String nickname;

    private Goods goods;

    private String name;

    public String getHeadimg() {
        return headimg;
    }

    public void setHeadimg(String headimg) {
        this.headimg = headimg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }

    public String getRtime() {
        return rtime;
    }

    public void setRtime(String rtime) {
        this.rtime = rtime;
    }

    public String getAsker() {
        return asker;
    }

    public void setAsker(String asker) {
        this.asker = asker;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public ReplyMsg getReply() {
        return reply;
    }

    public void setReply(ReplyMsg reply) {
        this.reply = reply;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getReplyContent() {
        return replyContent;
    }

    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String creaetime) {
        this.createtime = creaetime;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", userId=" + userId +
                ", goodsId=" + goodsId +
                ", content='" + content + '\'' +
                ", replyContent='" + replyContent + '\'' +
                ", state=" + state +
                ", createtime='" + createtime + '\'' +
                '}';
    }
}
