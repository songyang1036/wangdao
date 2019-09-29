package service.client;

import model.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface ClientGoodsService {
    List<Goods> getGoodsByType(String typeId) throws IOException, SQLException;

    SpecList getGoodsInfo(String id) throws SQLException, IOException;

    List<Message> getGoodsMsg(String id) throws SQLException;

    void askGoodsMsg(Message message) throws SQLException;

    RelComment getGoodsComment(String goodsId) throws SQLException, IOException;

    List<Goods> searchGoods(String goodsName) throws SQLException, IOException;

    List<GoodsType> getType() throws SQLException;

}
