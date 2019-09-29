package service.admin;

import dao.admin.GoodsDaoImpl;
import dao.admin.OrderDao;
import dao.admin.OrderDaoImpl;
import dao.client.ClientOrderDaoImpl;
import model.*;
import util.DruidUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class OrderServiceImpl implements OrderService {

    OrderDao orderDao = new OrderDaoImpl();

    @Override
    public ResultOrderPage ordersByPage(OrderPage orderPage) throws SQLException {
        //查询总数目
        int count = orderDao.queryTotalOrders();
        //查询总页数
        int totalPage = 0;
        if (count % orderPage.getPagesize() == 0) {
            totalPage = count / orderPage.getPagesize();
        } else {
            totalPage = count / orderPage.getPagesize() + 1;
        }
        //每页显示的条目 user集合  limit page_size offset (currentPage-1)*page_size
        //List<Orders> orders = orderDao.queryPageOrder(orderPage.getPagesize(),orderPage.getCurrentPage());

        //state   -1  1  2  3  4
        //-1查询全部的问题
        List<Orders> orders = null;
        if (orderPage.getState() == -1) {
            orders = orderDao.queryPageOrder(orderPage.getPagesize(), orderPage.getCurrentPage());
        } else {
            orders = orderDao.queryOtherPageOrder(orderPage);
        }

        for (Orders order : orders) {
            if (order.getStateId() == 0) {
                order.setState("未付款");
            } else if (order.getStateId() == 1) {
                order.setState("未发货");
            } else if (order.getStateId() == 2) {
                order.setState("已发货");
            } else if (order.getStateId() == 3) {
                order.setState("已到货");
            }

            order.setAmount(order.getGoodsNum() * order.getAmount());

            User user = new User();
            user.setNickname(order.getNickname());
            user.setName(order.getName());
            user.setAddress(order.getAddress());
            user.setPhone(order.getPhone());
            order.setUser(user);
        }
        ResultOrderPage resultOrderPage = new ResultOrderPage();
        resultOrderPage.setTotal(count);
        resultOrderPage.setOrders(orders);
        return resultOrderPage;
    }

    @Override
    public RelEditOrder order(String id) throws SQLException {
        Orders orders = orderDao.order(id);
        List<GoodsSpecs> specs = orderDao.orderGoodsSpec(orders.getGoodsId());
        List<States> states = orderDao.queryOrderState();
        for (States state : states) {
            state.setId(state.getId() - 1);
        }

        RelEditOrder relEditOrder = new RelEditOrder();
        relEditOrder.setId(orders.getId());
        relEditOrder.setAmount(orders.getAmount());
        relEditOrder.setNum(orders.getGoodsNum());
        relEditOrder.setGoodsDetailId(orders.getSpecId());
        relEditOrder.setState(orders.getStateId());
        relEditOrder.setGoods(orders.getGoods());
        relEditOrder.setSpec(specs);

        relEditOrder.setStates(states);

        CurState curState = new CurState();
        curState.setId(orders.getStateId());
        relEditOrder.setCurState(curState);


        CurSpec curSpec = new CurSpec();
        curSpec.setId(orders.getSpecId());
        relEditOrder.setCurSpec(curSpec);
        return relEditOrder;
    }

    @Override
    public void changeOrder(Orders order) throws SQLException {
        GoodsSpecs specs = orderDao.querySpec(order);
        orderDao.changeOrderDetail(order, specs);
        int oid = new ClientOrderDaoImpl().queryOrder(order.getId());
        order.setId(oid);
        orderDao.changeOrder(order, specs);
    }

    @Override
    public void deleteOrder(String id) throws SQLException {

        //int did = orderDao.queryOrderDid(id);
        Orders orders = orderDao.queryOrderDetail(id);

        Connection connection = null;
        try {
            connection = DruidUtils.getConnection(true);
            connection.setAutoCommit(false);

            orderDao.deleteOrderDetail(id);
            GoodsSpecs spec = new GoodsDaoImpl().getSpec(orders.getSpecId());
            //删除主订单,若不同商品为1,直接删除该条订单，若不是就减数量和价格；
            //count =num  id= orderId  goodsNum  amount
            if (orders.getNum() == 1) {//有点问题
                orderDao.deleteOrder(orders);
                RelEditOrder order = orderDao.queryOrder(orders.getId());
                spec.setStockNum(spec.getStockNum() + order.getGoodsNum());
                new GoodsDaoImpl().updateGoodsSpec(spec, spec.getId());
            } else {
                //或得order的goodsNum amount
                RelEditOrder order = orderDao.queryOrder(orders.getId());
                spec.setStockNum(spec.getStockNum() + order.getGoodsNum());
                new GoodsDaoImpl().updateGoodsSpec(spec, spec.getId());
                orderDao.updateOrder(order, orders);
            }
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
}
