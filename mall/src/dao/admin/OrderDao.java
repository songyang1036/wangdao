package dao.admin;

import model.*;

import java.sql.SQLException;
import java.util.List;

public interface OrderDao {
    int queryTotalOrders() throws SQLException;

    List<Orders> queryPageOrder(int pageSize,int currentPage) throws SQLException;

    List<Orders> queryOtherPageOrder(OrderPage orderPage) throws SQLException;

    Orders order(String id) throws SQLException;

    List<GoodsSpecs> orderGoodsSpec(int id) throws SQLException;

    List<States> queryOrderState() throws SQLException;

    GoodsSpecs querySpec(Orders order) throws SQLException;

    void changeOrderDetail(Orders order,GoodsSpecs specs) throws SQLException;

    void changeOrder(Orders order, GoodsSpecs specs) throws SQLException;

    Orders queryOrderDetail(String id) throws SQLException;

    void deleteOrderDetail(String id) throws SQLException;

    void deleteOrder(Orders orders) throws SQLException;

    void updateOrder(RelEditOrder order,Orders orders) throws SQLException;

    RelEditOrder queryOrder(int id) throws SQLException;
}
