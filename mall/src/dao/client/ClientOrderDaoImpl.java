package dao.client;

import model.*;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import util.DruidUtils;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.List;

public class ClientOrderDaoImpl implements ClientOrderDao {
    @Override
    public int addOrder(Orders order) throws SQLException {
        QueryRunner runner = new QueryRunner();
        int update = runner.update(DruidUtils.getConnection(true),"insert into orders values(null,?,?,?,?,now())",
                order.getUserId(), order.getState(), order.getNum(), order.getAmount());
        int oid = 0;
        if (update>0){
            BigInteger query = (BigInteger) runner.query(DruidUtils.getConnection(true),"SELECT LAST_INSERT_ID()", new ScalarHandler());
           oid = query.intValue();
        }
        return oid;
    }

    @Override
    public Boolean addDetailOrder(int oid, Goods goodsInfo, GoodsSpecs spec) throws SQLException {
        QueryRunner runner = new QueryRunner();
        int update = runner.update(DruidUtils.getConnection(true),"insert into orderDetail values(null,?,?,?,?,?,?,?,'false')",
                oid,goodsInfo.getId(),goodsInfo.getName(),spec.getSpecName(),1,
                spec.getUnitPrice(),spec.getId());
        return update>0;
    }

    @Override
    public List<RelOrderCart> getOrderByState(String state, int id) throws SQLException {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        List<RelOrderCart> orders = runner.query("select d.id,o.state,o.createtime,o.goodsNum,o.amount,d.specId as goodsDetailId,d.goodsId,d.hasComment from orders as o left join orderDetail as d on o.id=d.orderId where o.state =? and o.userId=?",
                new BeanListHandler<RelOrderCart>(RelOrderCart.class),
                state,id);

        return orders;
    }

    @Override
    public List<RelOrderCart> getAllOrderState(String token, int id) throws SQLException {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        List<RelOrderCart> orders = runner.query("select d.id,o.state,o.createtime,o.goodsNum,o.amount,d.specId as goodsDetailId,d.goodsId,d.hasComment from orders as o left join orderDetail as d on o.id=d.orderId where o.userId=?",
                new BeanListHandler<RelOrderCart>(RelOrderCart.class),
                id);

        return orders;
    }

    @Override
    public void pay(int oid) throws SQLException {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        runner.update("update orders set state = '1',createtime=now() where id = ?",oid);
    }

    @Override
    public void confirmReceive(int oid, String state) throws SQLException {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        runner.update("update orders set state = ?,createtime=now() where id = ?",state,oid);
    }

    @Override
    public void updateOrderHasCom(String hasComment,int orderId) throws SQLException {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        runner.update("update orderDetail set hasComment = ? where id = ?",hasComment,orderId);
    }

    @Override
    public void sendComment(Comment comment) throws SQLException {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        runner.update("insert into appraise values(null,?,?,?,?,?,now())",comment.getUserId(),
                comment.getGoodsId(),comment.getGoodsDetailId(),comment.getContent(),comment.getScore());

    }

    @Override
    public int queryOrder(int id) throws SQLException {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        int query = (Integer) runner.query("select orderId from orderDetail where id = ?", new ScalarHandler(), id);
        return query;
    }

    @Override
    public void updateOrder(CartList list,int oid) throws SQLException {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        runner.update("update orders set state = '1',goodsNum = ?,amount=?,createtime=now() where id = ?",
                list.getGoodsNum(),list.getAmount(),oid);
    }


}
