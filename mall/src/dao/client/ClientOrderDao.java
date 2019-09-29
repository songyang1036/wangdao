package dao.client;

import model.*;

import java.sql.SQLException;
import java.util.List;

public interface ClientOrderDao {
    int addOrder(Orders order) throws SQLException;

    Boolean addDetailOrder(int oid, Goods goodsInfo, GoodsSpecs spec) throws SQLException;

    List<RelOrderCart> getOrderByState(String state, int id) throws SQLException;

    int queryOrder(int id) throws SQLException;

    void updateOrder(CartList list,int oid) throws SQLException;

    List<RelOrderCart> getAllOrderState(String token, int id) throws SQLException;

    void pay(int oid) throws SQLException;

    void confirmReceive(int oid, String state) throws SQLException;

    void updateOrderHasCom(String hasComment,int orderId) throws SQLException;

    void sendComment(Comment comment) throws SQLException;
}
