package model;

public class GoodsSpecs {

    private int id;

    private int goodsId;

    private int gid;

    private String specName;

    private int stockNum;

    private double unitPrice;


    public int getGid() {
        return gid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSpecName() {
        return specName;
    }

    public void setSpecName(String specName) {
        this.specName = specName;
    }

    public int getStockNum() {
        return stockNum;
    }

    public void setStockNum(int stockNum) {
        this.stockNum = stockNum;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Override
    public String toString() {
        return "GoodsSpecs{" +
                "id=" + id +
                ", specName='" + specName + '\'' +
                ", stockNum=" + stockNum +
                ", unitPrice=" + unitPrice +
                '}';
    }
}
