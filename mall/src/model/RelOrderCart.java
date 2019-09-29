package model;

public class RelOrderCart {

    /*
    	"id": 818,
		"state": 0,
		"goodsNum": 1,
		"amount": 100.0,
		"goodsDetailId": 956,
		"createtime": "2019-09-05 00:38:01",
		"hasComment": false,
     */
    private int id;
    private int state;
    private int goodsNum;
    private double amount;
    private int goodsDetailId;
    private String createtime;
    private Boolean hasComment;
    private Goods goods;
    private String goodsId;
    //private int specId;

//    public int getSpecId() {
//        return specId;
//    }
//
//    public void setSpecId(int specId) {
//        this.specId = specId;
//    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
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

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
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

    public int getGoodsDetailId() {
        return goodsDetailId;
    }

    public void setGoodsDetailId(int goodsDetailId) {
        this.goodsDetailId = goodsDetailId;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public Boolean getHasComment() {
        return hasComment;
    }

    public void setHasComment(Boolean hasComment) {
        this.hasComment = hasComment;
    }

    @Override
    public String toString() {
        return "RelOrderCart{" +
                "id=" + id +
                ", state=" + state +
                ", goodsNum=" + goodsNum +
                ", amount=" + amount +
                ", goodsDetailId=" + goodsDetailId +
                ", createtime='" + createtime + '\'' +
                ", hasComment=" + hasComment +
                '}';
    }
}
