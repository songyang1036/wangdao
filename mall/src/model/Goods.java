package model;

import java.util.List;

public class Goods {

    private int id;

    private String desc;

    private String img;

    private String name;

    private int typeId;

    private double unitPrice;

    private double price;

    private int stockNum;

    private String file;

    private String spec;
    private int goodsDetailId;

    private String specs;

    private List<GoodsSpecs> specList;


    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public int getGoodsDetailId() {
        return goodsDetailId;
    }

    public void setGoodsDetailId(int goodsDetailId) {
        this.goodsDetailId = goodsDetailId;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getSpecs() {
        return specs;
    }

    public void setSpecs(String specs) {
        this.specs = specs;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getStockNum() {
        return stockNum;
    }

    public void setStockNum(int stockNum) {
        this.stockNum = stockNum;
    }

    public List<GoodsSpecs> getSpecList() {
        return specList;
    }

    public void setSpecList(List<GoodsSpecs> specList) {
        this.specList = specList;
    }

    @Override
    public String toString() {
        return "Goods{" +
                "id=" + id +
                ", desc='" + desc + '\'' +
                ", img='" + img + '\'' +
                ", name='" + name + '\'' +
                ", typeId=" + typeId +
                ", unitPrice=" + unitPrice +
                ", stockNum=" + stockNum +
                ", specList='" + specList + '\'' +
                '}';
    }
}
