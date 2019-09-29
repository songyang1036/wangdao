package service.admin;

import model.OrderPage;
import model.Orders;
import model.RelEditOrder;
import model.ResultOrderPage;

import java.sql.SQLException;
import java.util.List;

public interface OrderService {
    ResultOrderPage ordersByPage(OrderPage orderPage) throws SQLException;

    RelEditOrder order(String id) throws SQLException;

    void changeOrder(Orders order) throws SQLException;

    void deleteOrder(String id) throws SQLException;
}
