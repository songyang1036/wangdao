package service.admin;
import dao.admin.GoodsDao;
import dao.admin.GoodsDaoImpl;
import model.Goods;
import model.GoodsSpecs;
import model.GoodsType;
import model.SpecList;

import java.io.*;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

public class GoodsServiceImpl implements GoodsService {

    GoodsDao goodsDao = new GoodsDaoImpl();

    @Override
    public List<GoodsType> getType() throws SQLException {
        return goodsDao.getType();
    }

    @Override
    public List<Goods> getGoodsByType(String typeId) throws SQLException, IOException {
        List<Goods> goodsList = goodsDao.getGoodsByType(typeId);
        Properties properties = new Properties();
        //FileInputStream fin = new FileInputStream(new File("imgUrl.properties"));
        InputStream resourceAsStream = GoodsServiceImpl.class.getClassLoader().getResourceAsStream("imgUrl.properties");
        properties.load(resourceAsStream);
        String imgUrl = properties.getProperty("imgUrl");
        for (Goods goods : goodsList) {

            goods.setPrice(goods.getUnitPrice());
            //imgUrl += goods.getImg();
            goods.setImg(imgUrl+goods.getImg());
        }
        return goodsList;
    }

    @Override
    public Boolean addType(GoodsType goodsType) throws SQLException {
        List<GoodsType> types = goodsDao.getType();
        for (GoodsType type : types) {
            if (goodsType.getName().equals(type.getName())){
                return false;
            }
        }
        return goodsDao.AddType(goodsType);
    }

    @Override
    public Boolean deleteGoods(String id) throws SQLException {
        return goodsDao.deleteGoods(id);
    }

    @Override
    public void addGoods(Goods goods) throws SQLException {
        BigInteger id = goodsDao.addGoods(goods);
        List<GoodsSpecs> specList = goods.getSpecList();
        int stockNum = 0;
        double unitPrice = Double.MAX_VALUE;
        for (GoodsSpecs goodsSpecs : specList) {
            goodsDao.addSpec(goodsSpecs,id);
            stockNum += goodsSpecs.getStockNum();
            if(goodsSpecs.getUnitPrice()<unitPrice){
                unitPrice = goodsSpecs.getUnitPrice();
            }
        }
        goodsDao.addGoodsSpec(id,stockNum,unitPrice);
    }

    @Override
    public SpecList getGoodsInfo(String id) throws SQLException {
        Goods goods = goodsDao.getGoodsInfo(id);
        List<GoodsSpecs> goodsSpecs = goodsDao.getGoodsSpec(id);
        //goods.setSpecList(goodsSpecs);
        SpecList specList = new SpecList();
        specList.setSpecs(goodsSpecs);
        specList.setGoods(goods);
        return specList;
    }

    @Override
    public Boolean updateGoods(Goods goods) throws SQLException {
        if(goodsDao.updateGoods(goods)){
            List<GoodsSpecs> specList = goods.getSpecList();
            for (GoodsSpecs goodsSpecs : specList) {
                //根据goodsId,和specName找specId
                if (goods.getId()==0){
                    int specId = goodsDao.getGoodsSpecId(goodsSpecs);
                    goodsDao.updateGoodsSpec(goodsSpecs,specId);
                }else {
                    goodsDao.updateGoodsSpec(goodsSpecs, goodsSpecs.getId());
                }

            }
            return true;
        }
        return false;
    }

    @Override
    public boolean addSpec(GoodsSpecs goodsSpecs) throws SQLException {
        List<GoodsSpecs> goodsSpec = goodsDao.getGoodsSpec(String.valueOf(goodsSpecs.getGoodsId()));
        for (GoodsSpecs specs : goodsSpec) {
            if(goodsSpecs.getSpecName().equals(specs.getSpecName())){
                return false;
            }
        }
        goodsDao.addSpec(goodsSpecs,BigInteger.valueOf(goodsSpecs.getGoodsId()));
        return true;
    }

    @Override
    public void deleteSpec(GoodsSpecs goodsSpecs) throws SQLException {
        goodsDao.deleteSpec(goodsSpecs);
    }
}
