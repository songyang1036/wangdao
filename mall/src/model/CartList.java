package model;

public class CartList {
    private int id;
    private int goodsNum;
    private int amount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(int goodsNum) {
        this.goodsNum = goodsNum;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "CartList{" +
                "id=" + id +
                ", goodsNum=" + goodsNum +
                ", amount=" + amount +
                '}';
    }
}
