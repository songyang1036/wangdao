package dao.admin;

import model.*;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import util.DruidUtils;

import java.sql.SQLException;
import java.util.List;

public class OrderDaoImpl implements OrderDao {
    @Override
    public int queryTotalOrders() throws SQLException {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        Long count = (Long) runner.query("select count(id) from orderDetail",new ScalarHandler());
        return count.intValue();
    }

    @Override
    public List<Orders> queryPageOrder(int pageSize,int currentPage) throws SQLException {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        List<Orders> orders = runner.query("select * from (select d.id,o.state as stateId,o.createtime as updatetime,u.id as userId,u.nickname,u.recipient as name,u.address,u.phone,d.specId as goodsDetailId,d.goodsName as goods,d.spec,o.goodsNum,d.amount from orders as o left join user as u on u.id=o.userId left join orderDetail as d on o.id=d.orderId) as T limit ? offset ?", new BeanListHandler<Orders>(Orders.class),
                pageSize, (currentPage - 1) * pageSize);
        return orders;
    }

    @Override
    public List<Orders> queryOtherPageOrder(OrderPage orderPage) throws SQLException {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        List<Orders> orders = runner.query("select * from (select d.id,o.state as stateId,o.createtime as updatetime,u.id as userId,u.nickname,u.recipient as name,u.address,u.phone,d.specId as goodsDetailId,d.goodsName as goods,d.spec,d.goodsNum,d.amount from orders as o left join user as u on u.id=o.userId left join orderDetail as d on o.id=d.orderId where state =?) as T limit ? offset ?", new BeanListHandler<Orders>(Orders.class),
                orderPage.getState(),orderPage.getPagesize(), (orderPage.getCurrentPage() - 1) * orderPage.getPagesize());
        return orders;
    }

    @Override
    public Orders order(String id) throws SQLException {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        Orders orders = runner.query("select d.id,o.state as stateId,d.goodsName as goods,o.goodsNum,o.amount,d.goodsId,d.specId from orders as o left join orderDetail as d on d.orderId=o.id where d.id=?", new BeanHandler<Orders>(Orders.class),id);
        return orders;
    }

    @Override
    public List<GoodsSpecs> orderGoodsSpec(int id) throws SQLException {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        List<GoodsSpecs> specs = runner.query("select * from goodsSpec where gid=?", new BeanListHandler<GoodsSpecs>(GoodsSpecs.class),id);
        return specs;
    }

    @Override
    public List<States> queryOrderState() throws SQLException {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        List<States> states = runner.query("select * from orderState", new BeanListHandler<States>(States.class));
        return states;
    }

    @Override
    public GoodsSpecs querySpec(Orders order) throws SQLException {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        GoodsSpecs specs = runner.query("select * from goodsSpec where id = ?", new BeanHandler<GoodsSpecs>(GoodsSpecs.class),order.getSpec());
        return specs;
    }

    @Override
    public void changeOrderDetail(Orders order,GoodsSpecs specs) throws SQLException {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        runner.update("update orderDetail set specId = ?,spec =?,amount=? where id =?",specs.getId(),specs.getSpecName(),specs.getUnitPrice(),order.getId());
    }

    @Override
    public void changeOrder(Orders order, GoodsSpecs specs) throws SQLException {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        runner.update("update orders set state = ?,goodsNum =?,amount=? where id =?",order.getState(),order.getNum(),specs.getUnitPrice()*order.getNum(),order.getId());

    }

    @Override
    public Orders queryOrderDetail(String id) throws SQLException {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        Orders orders = runner.query("select count(o.orderId) as num,o.orderId as id,o.goodsNum,o.amount,o.specId from orderDetail as o where o.id=?", new BeanHandler<Orders>(Orders.class),id);
        return orders;
    }

    @Override
    public void deleteOrderDetail(String id) throws SQLException {
        QueryRunner runner = new QueryRunner();
        runner.update(DruidUtils.getConnection(true),"delete from orderDetail where id = ?",id);

    }

    @Override
    public void deleteOrder(Orders orders) throws SQLException {
        QueryRunner runner = new QueryRunner();
        runner.update(DruidUtils.getConnection(true),"delete from orders where id = ?",orders.getId());
    }

    @Override
    public RelEditOrder queryOrder(int id) throws SQLException {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        RelEditOrder orders = runner.query("select o.goodsNum,o.amount from orders as o where id =?", new BeanHandler<RelEditOrder>(RelEditOrder.class),id);
        return orders;
    }

    @Override
    public void updateOrder(RelEditOrder order,Orders orders) throws SQLException {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        runner.update("update orders set goodsNum = ?,amount=? where id =?",order.getGoodsNum()-orders.getGoodsNum(),order.getAmount()-(orders.getAmount()*orders.getGoodsNum()),orders.getId());
    }


}
