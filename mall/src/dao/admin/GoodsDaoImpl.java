package dao.admin;

import model.Goods;
import model.GoodsSpecs;
import model.GoodsType;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import util.DruidUtils;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.List;

public class GoodsDaoImpl implements GoodsDao {
    @Override
    public List<GoodsType> getType() throws SQLException {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        List<GoodsType> query = runner.query("select * from goodsType", new BeanListHandler<GoodsType>(GoodsType.class));
        return query;
    }

    @Override
    public List<Goods> getGoodsByType(String typeId) throws SQLException {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        List<Goods> query = runner.query("select * from goods where typeId = ?", new BeanListHandler<Goods>(Goods.class),typeId);
        return query;
    }

    @Override
    public Boolean AddType(GoodsType goodsType) throws SQLException {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        int update = runner.update("insert into goodsType values(null,?)", goodsType.getName());
        return update>0;
    }

    @Override
    public Boolean deleteGoods(String id) throws SQLException {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        int update = runner.update("delete from goods where id = ?", id);
        return update>0;
    }

    @Override
    public BigInteger addGoods(Goods goods) throws SQLException {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        runner.update("insert into goods(typeId,name,img,`desc`) values(?,?,?,?)",
                goods.getTypeId(),goods.getName(),goods.getImg(),goods.getDesc());
        return (BigInteger) runner.query("SELECT LAST_INSERT_ID()", new ScalarHandler());
    }

    @Override
    public void addSpec(GoodsSpecs goodsSpecs, BigInteger id) throws SQLException {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        runner.update("insert into goodsSpec values(null,?,?,?,?)",id,goodsSpecs.getSpecName(),goodsSpecs.getStockNum(),goodsSpecs.getUnitPrice());
    }

    @Override
    public void addGoodsSpec(BigInteger id, int stockNum, double unitPrice) throws SQLException {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        runner.update("update goods set stockNum = ?,unitPrice = ? where id = ?",stockNum,unitPrice,id);

    }

    @Override
    public Goods getGoodsInfo(String id) throws SQLException {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        Goods goods = runner.query("select * from goods where id = ?", new BeanHandler<Goods>(Goods.class), id);
        return goods;
    }

    @Override
    public List<GoodsSpecs> getGoodsSpec(String id) throws SQLException {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        List<GoodsSpecs> goodsSpecs = runner.query("select * from goodsSpec where gid = ?", new BeanListHandler<GoodsSpecs>(GoodsSpecs.class), id);
        return goodsSpecs;
    }

    @Override
    public GoodsSpecs getSpec(int id) throws SQLException {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        GoodsSpecs spec = runner.query("select * from goodsSpec where id = ?", new BeanHandler<GoodsSpecs>(GoodsSpecs.class), id);
        return spec;
    }

    @Override
    public Boolean updateGoods(Goods goods) throws SQLException {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        int update = runner.update("update goods set typeId = ?,name = ?,img = ?,`desc` = ? where id=?",goods.getTypeId(),goods.getName(),goods.getImg(),goods.getDesc(),goods.getId());
        return update>0;
    }


    @Override
    public void updateGoodsSpec(GoodsSpecs goodsSpecs,int id) throws SQLException {
        QueryRunner runner = new QueryRunner();
        runner.update(DruidUtils.getConnection(true),"update goodsSpec set specName = ?,stockNum = ?,unitPrice = ? where id=?",goodsSpecs.getSpecName(),goodsSpecs.getStockNum(),goodsSpecs.getUnitPrice(),id);
    }                       //update goodsSpec set specName = ?,stockNum = ?,unitPrice = ? where gid=? and specName=?

    @Override
    public void deleteSpec(GoodsSpecs goodsSpecs) throws SQLException {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        runner.update("delete from goodsSpec where gid=? and specName=?",goodsSpecs.getGoodsId(),goodsSpecs.getSpecName());

    }

    @Override
    public int getGoodsSpecId(GoodsSpecs goodsSpecs) throws SQLException {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        int spec = (int) runner.query("select id from goodsSpec where gid = ? and specName=?", new ScalarHandler(), goodsSpecs.getGid(),goodsSpecs.getSpecName());
        return spec;
    }
}
