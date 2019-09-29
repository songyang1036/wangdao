package dao.client;

import model.*;

import java.sql.SQLException;
import java.util.List;

public interface ClientGoodsDao {
    List<Goods> getAllGoods() throws SQLException;

    List<Goods> getGoodsByType(String typeId) throws SQLException;

    SpecList getGoodsInfo(String id) throws SQLException;

    List<Message> getGoodsMsg(String id) throws SQLException;

    int queryUser(String token) throws SQLException;

    void askGoodsMsg(Message message) throws SQLException;

    List<Comment> getGoodsComment(String goodsId) throws SQLException;

    User queryIdUser(int userId) throws SQLException;

    List<Goods> searchGoods(String goodsName) throws SQLException;
}
