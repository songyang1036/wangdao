package model;

import java.util.List;

public class SpecList {

    private int id;
    private String img;
    private String name;
    private String desc;
    private int typeId;


    private List<GoodsSpecs> specs;

    private Goods goods;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public List<GoodsSpecs> getSpecs() {
        return specs;
    }

    public void setSpecs(List<GoodsSpecs> specs) {
        this.specs = specs;
    }

    @Override
    public String toString() {
        return "SpecList{" +
                "id=" + id +
                ", img='" + img + '\'' +
                ", name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", typeId=" + typeId +
                ", specs=" + specs +
                ", goods=" + goods +
                '}';
    }
}
