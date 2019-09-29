package service.client;

import dao.admin.GoodsDaoImpl;
import dao.client.ClientOrderDao;
import dao.client.ClientOrderDaoImpl;
import dao.client.UserDaoImpl;
import model.*;
import service.admin.OrderServiceImpl;
import util.DruidUtils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ClientOrderServiceImpl implements ClientOrderService {
    ClientOrderDao clientOrderDao = new ClientOrderDaoImpl();
    @Override
    public Boolean addOrder(Orders order) throws SQLException{


        User user = new UserDaoImpl().getUserData(order.getToken());
        order.setUserId(user.getId());
        //商品spec
        GoodsSpecs spec = new GoodsDaoImpl().getSpec(order.getGoodsDetailId());
        //商品id
        String gid = String.valueOf(spec.getGid());
        //商品信息
        Goods goodsInfo = new GoodsDaoImpl().getGoodsInfo(gid);

        //增加主订单,返回订单id
        int oid = 0;
        Connection connection = null;
        try {
            connection = DruidUtils.getConnection(true);
            connection.setAutoCommit(false);
            oid = clientOrderDao.addOrder(order);
            if (oid==0){
                int i = 1/0;
            }
            //增加详情单
            Boolean flag = clientOrderDao.addDetailOrder(oid, goodsInfo, spec);
            //减库存
            spec.setStockNum(spec.getStockNum()-order.getNum());
            //更新库存
            new GoodsDaoImpl().updateGoodsSpec(spec,spec.getId());
            //new GoodsDaoImpl().update
            connection.commit();
            return flag;

        } catch (SQLException e) {
            e.printStackTrace();
            if(connection != null){
                try {
                    connection.rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        }finally {
            try {
                if(connection != null){
                    connection.close();
                    DruidUtils.setNull();//将threadLocal中的connection设置为空
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


        return false;
    }

    @Override
    public List<RelOrderCart> getOrderByState(String state, String token) throws SQLException, IOException {
        User user = new UserDaoImpl().getUserData(token);
        List<RelOrderCart> orderCartList = null;
        if ("-1".equals(state)){
            orderCartList = clientOrderDao.getAllOrderState(token,user.getId());
        }else {
            orderCartList = clientOrderDao.getOrderByState(state, user.getId());
        }

        for (RelOrderCart relOrderCart : orderCartList) {
            //根据goodsId查goods
            Goods goodsInfo = new GoodsDaoImpl().getGoodsInfo(relOrderCart.getGoodsId());
            //根据goodsDetailId查goodsSpec
            GoodsSpecs spec = new GoodsDaoImpl().getSpec(relOrderCart.getGoodsDetailId());
            //relOrderCart.setHasComment(false);
            goodsInfo.setGoodsDetailId(relOrderCart.getGoodsDetailId());
            goodsInfo.setSpec(spec.getSpecName());
            goodsInfo.setUnitPrice(spec.getUnitPrice());
            //图片地址
            Properties properties = new Properties();
            InputStream resourceAsStream = ClientGoodsServiceImpl.class.getClassLoader().getResourceAsStream("imgUrl.properties");
            properties.load(resourceAsStream);
            String imgUrl = properties.getProperty("imgUrl");
            goodsInfo.setImg(imgUrl+goodsInfo.getImg());
            relOrderCart.setGoods(goodsInfo);
        }


        return orderCartList;
    }

    @Override
    public void settleAccounts(POrder order) throws SQLException {
        List<CartList> cartList = order.getCartList();
        for (CartList list : cartList) {
            //找主订单id
            int oid = clientOrderDao.queryOrder(list.getId());
            //修改主订单 state num amount createtime
            clientOrderDao.updateOrder(list,oid);

        }
    }

    @Override
    public void deleteOrder(String id) throws SQLException {

        new OrderServiceImpl().deleteOrder(id);
    }

    @Override
    public void pay(String id) throws SQLException {
        //找主订单id
        int i = Integer.parseInt(id);
        int oid = clientOrderDao.queryOrder(i);
        clientOrderDao.pay(oid);
    }

    @Override
    public void confirmReceive(String id) throws SQLException {
        int i = Integer.parseInt(id);
        int oid = clientOrderDao.queryOrder(i);
        String state = "3";
        clientOrderDao.confirmReceive(oid,state);
    }

    @Override
    public void sendComment(Comment comment) throws SQLException {
        //根据token获得userId
        User user = new UserDaoImpl().getUserData(comment.getToken());
        comment.setUserId(user.getId());
        //修改订单详情表的hasComment
        String hasComment="true";
        clientOrderDao.updateOrderHasCom(hasComment,comment.getOrderId());
        //将评论数据插入表中
        clientOrderDao.sendComment(comment);
    }
}
