package service.client;

import dao.admin.GoodsDao;
import dao.admin.GoodsDaoImpl;
import dao.client.ClientGoodsDao;
import dao.client.ClientGoodsDaoImpl;
import model.*;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Properties;

import static java.lang.Double.NaN;

public class ClientGoodsServiceImpl implements ClientGoodsService {

    ClientGoodsDao clientGoodsDao = new ClientGoodsDaoImpl();

    @Override
    public List<Goods> getGoodsByType(String typeId) throws IOException, SQLException {

        List<Goods> goodsList =null;
        if("-1".equals(typeId)){
            goodsList = clientGoodsDao.getAllGoods();
        }else {
            goodsList = clientGoodsDao.getGoodsByType(typeId);
        }


        //图片地址
        Properties properties = new Properties();
        InputStream resourceAsStream = ClientGoodsServiceImpl.class.getClassLoader().getResourceAsStream("imgUrl.properties");
        properties.load(resourceAsStream);
        String imgUrl = properties.getProperty("imgUrl");
        for (Goods goods : goodsList) {
            goods.setPrice(goods.getUnitPrice());
            goods.setImg(imgUrl+goods.getImg());
        }

        return goodsList;
    }

    @Override
    public SpecList getGoodsInfo(String id) throws SQLException, IOException {
        SpecList rel = clientGoodsDao.getGoodsInfo(id);
        //图片地址
        Properties properties = new Properties();
        InputStream resourceAsStream = ClientGoodsServiceImpl.class.getClassLoader().getResourceAsStream("imgUrl.properties");
        properties.load(resourceAsStream);
        String imgUrl = properties.getProperty("imgUrl");
        rel.setImg(imgUrl+rel.getImg());
        List<GoodsSpecs> goodsSpecs =new GoodsDaoImpl().getGoodsSpec(id);
        rel.setSpecs(goodsSpecs);
        return rel;
    }

    @Override
    public List<Message> getGoodsMsg(String id) throws SQLException {
        List<Message> msgs = clientGoodsDao.getGoodsMsg(id);
        for (Message msg : msgs) {
            msg.setTime(msg.getCreatetime());
            ReplyMsg replyMsg = new ReplyMsg();
            replyMsg.setId(msg.getRid());
            replyMsg.setContent(msg.getReplyContent());
            replyMsg.setTime(msg.getRtime());
            msg.setReply(replyMsg);
        }
        return msgs;
    }

    @Override
    public void askGoodsMsg(Message message) throws SQLException {
        //根据token用户名找用户id
        int userId = clientGoodsDao.queryUser(message.getToken());
        //将用户id goodsId msg 存入 message
        message.setUserId(userId);
        clientGoodsDao.askGoodsMsg(message);
    }

    @Override
    public RelComment getGoodsComment(String goodsId) throws SQLException, IOException {
        List<Comment> commentList = clientGoodsDao.getGoodsComment(goodsId);
        RelComment relComment = new RelComment();
        if (commentList==null ||commentList.size()==0){
            relComment.setRate("NaN");
            relComment.setCommentList(commentList);
            return relComment;
        }
        double rate = 0.0;
        //图片地址
        Properties properties = new Properties();
        InputStream resourceAsStream = ClientGoodsServiceImpl.class.getClassLoader().getResourceAsStream("imgUrl.properties");
        properties.load(resourceAsStream);
        String imgUrl = properties.getProperty("imgUrl");
        for (Comment comment : commentList) {
            User user = clientGoodsDao.queryIdUser(comment.getUserId());
            user.setHeadimg(imgUrl+user.getHeadimg());
            User user1 = new User();
            user1.setId(user.getId());
            user1.setHeadimg(user.getHeadimg());
            user1.setNickname(user.getNickname());
            comment.setUser(user1);
            comment.setTime(comment.getPtime());
            rate+=comment.getScore();
        }
        rate/=commentList.size();
//        BigDecimal b = new BigDecimal(rate);
//        double r = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

        DecimalFormat df = new DecimalFormat("0.00");
        String e = df.format(rate); // 此处可以用上面b/c/d任意一种

        relComment.setCommentList(commentList);
        relComment.setRate(e);


        return relComment;
    }

    @Override
    public List<Goods> searchGoods(String goodsName) throws SQLException, IOException {

        List<Goods> searchGoods= clientGoodsDao.searchGoods(goodsName);
        //图片地址
        Properties properties = new Properties();
        InputStream resourceAsStream = ClientGoodsServiceImpl.class.getClassLoader().getResourceAsStream("imgUrl.properties");
        properties.load(resourceAsStream);
        String imgUrl = properties.getProperty("imgUrl");
        for (Goods goods : searchGoods) {
            goods.setPrice(goods.getUnitPrice());
            goods.setImg(imgUrl+goods.getImg());
        }
        return searchGoods;
    }

    @Override
    public List<GoodsType> getType() throws SQLException {
        List<GoodsType> type = new GoodsDaoImpl().getType();
        return type;
    }
}
