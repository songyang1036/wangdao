package service.admin;

import model.Goods;
import model.GoodsSpecs;
import model.GoodsType;
import model.SpecList;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface GoodsService {
    List<GoodsType> getType() throws SQLException;

    List<Goods> getGoodsByType(String typeId) throws SQLException, IOException;

    Boolean addType(GoodsType goodsType) throws SQLException;

    Boolean deleteGoods(String id) throws SQLException;

    void addGoods(Goods goods) throws SQLException;

    SpecList getGoodsInfo(String id) throws SQLException;

    Boolean updateGoods(Goods goods) throws SQLException;

    boolean addSpec(GoodsSpecs goodsSpecs) throws SQLException;

    void deleteSpec(GoodsSpecs goodsSpecs) throws SQLException;

}
