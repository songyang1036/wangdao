package dao.admin;

import model.Goods;
import model.GoodsSpecs;
import model.GoodsType;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.List;

public interface GoodsDao {
    List<GoodsType> getType() throws SQLException;

    List<Goods> getGoodsByType(String typeId) throws SQLException;

    Boolean AddType(GoodsType goodsType) throws SQLException;

    Boolean deleteGoods(String id) throws SQLException;

    BigInteger addGoods(Goods goods) throws SQLException;

    void addSpec(GoodsSpecs goodsSpecs, BigInteger id) throws SQLException;

    void addGoodsSpec(BigInteger id, int stockNum, double unitPrice) throws SQLException;

    Goods getGoodsInfo(String id) throws SQLException;

    List<GoodsSpecs> getGoodsSpec(String id) throws SQLException;

    GoodsSpecs getSpec(int id) throws SQLException;

    Boolean updateGoods(Goods goods) throws SQLException;

    void updateGoodsSpec(GoodsSpecs goodsSpecs,int id) throws SQLException;

    void deleteSpec(GoodsSpecs goodsSpecs) throws SQLException;

    int getGoodsSpecId(GoodsSpecs goodsSpecs) throws SQLException;
}
