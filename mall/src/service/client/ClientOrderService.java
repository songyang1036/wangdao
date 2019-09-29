package service.client;

import model.Comment;
import model.Orders;
import model.POrder;
import model.RelOrderCart;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface ClientOrderService {
    Boolean addOrder(Orders order) throws SQLException;

    List<RelOrderCart> getOrderByState(String state, String token) throws SQLException, IOException;

    void settleAccounts(POrder order) throws SQLException;

    void deleteOrder(String id) throws SQLException;

    void pay(String id) throws SQLException;

    void confirmReceive(String id) throws SQLException;

    void sendComment(Comment comment) throws SQLException;
}
