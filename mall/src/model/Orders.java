package model;

public class Orders {
    private int id;

    private int userId;

    private int goodsDetailId;

    private int goodsId;

    private String goods;

    private String spec;

    private int specId;

    private int goodsNum;

    private int num;

    private double amount;

    private int stateId;

    private String state;

    private String updatetime;

    private User user;

    private String token;

    private String nickname;

    private String name;

    private String address;

    private long phone;


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getSpecId() {
        return specId;
    }

    public void setSpecId(int specId) {
        this.specId = specId;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
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

    public int getGoodsDetailId() {
        return goodsDetailId;
    }

    public void setGoodsDetailId(int goodsDetailId) {
        this.goodsDetailId = goodsDetailId;
    }

    public String getGoods() {
        return goods;
    }

    public void setGoods(String goods) {
        this.goods = goods;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public int getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(int goodsNum) {
        this.goodsNum = goodsNum;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getStateId() {
        return stateId;
    }

    public void setStateId(int stateId) {
        this.stateId = stateId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "id=" + id +
                ", userId=" + userId +
                ", goodsDetailId=" + goodsDetailId +
                ", goods='" + goods + '\'' +
                ", spec='" + spec + '\'' +
                ", goodsNum=" + goodsNum +
                ", amount=" + amount +
                ", stateId=" + stateId +
                ", state='" + state + '\'' +
                ", client=" + user +
                '}';
    }
}
