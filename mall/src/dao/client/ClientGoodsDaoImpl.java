package dao.client;

import model.*;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import util.DruidUtils;

import java.sql.SQLException;
import java.util.List;

public class ClientGoodsDaoImpl implements ClientGoodsDao {
    @Override
    public List<Goods> getAllGoods() throws SQLException {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        List<Goods> query = runner.query("select * from goods", new BeanListHandler<Goods>(Goods.class));
        return query;
    }

    @Override
    public List<Goods> getGoodsByType(String typeId) throws SQLException {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        List<Goods> query = runner.query("select * from goods where typeId = ?", new BeanListHandler<Goods>(Goods.class),typeId);
        return query;
    }

    @Override
    public SpecList getGoodsInfo(String id) throws SQLException {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        SpecList rel = runner.query("select * from goods where id = ?", new BeanHandler<SpecList>(SpecList.class), id);
        return rel;
    }

    @Override
    public List<Message> getGoodsMsg(String id) throws SQLException {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        List<Message> msgs = runner.query("select m.id,m.content,m.createtime,u.nickname as asker,r.id as rid,r.content as replyContent,r.createtime as rtime from message as m left join user as u on u.id=m.userId left join replyMsg as r on m.id=r.mid where m.goodsId=?", new BeanListHandler<Message>(Message.class), id);
        return msgs;
    }

    @Override
    public int queryUser(String token) throws SQLException {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        int query = (int) runner.query("select user.id from user where nickname = ?", new ScalarHandler(), token);
        return query;
    }

    @Override
    public void askGoodsMsg(Message message) throws SQLException {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        runner.update("insert into message values(null,?,?,?,'1',now())",message.getUserId(),message.getGoodsId(),message.getMsg());
    }

    @Override
    public List<Comment> getGoodsComment(String goodsId) throws SQLException {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        List<Comment> comments = runner.query("select a.id,a.userId,a.comment,a.ptime,a.score,s.specName from appraise as a left join goodsSpec as s on s.id=a.goodsDetailId where goodsId = ?",
                new BeanListHandler<Comment>(Comment.class), goodsId);
        return comments;
    }

    @Override
    public User queryIdUser(int userId) throws SQLException {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        User user = runner.query("select * from user where id = ?", new BeanHandler<>(User.class), userId);
        return user;
    }

    @Override
    public List<Goods> searchGoods(String goodsName) throws SQLException {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        List<Goods> query = runner.query("select * from goods where name like ?", new BeanListHandler<Goods>(Goods.class), "%"+goodsName+"%");
        return query;
    }
}
