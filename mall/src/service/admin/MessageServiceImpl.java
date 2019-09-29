package service.admin;

import dao.admin.MessageDao;
import dao.admin.MessageDaoImpl;
import model.Goods;
import model.Message;
import model.User;
import service.client.ClientGoodsServiceImpl;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

public class MessageServiceImpl implements MessageService {

    MessageDao messageDao = new MessageDaoImpl();

    @Override
    public List<Message> noReplyMsg() throws SQLException, IOException {
        List<Message> messages = messageDao.noReplyMsg();
        //图片地址
        Properties properties = new Properties();
        InputStream resourceAsStream = ClientGoodsServiceImpl.class.getClassLoader().getResourceAsStream("imgUrl.properties");
        properties.load(resourceAsStream);
        String imgUrl = properties.getProperty("imgUrl");
        for (Message message : messages) {
            Goods goods = new Goods();
            goods.setName(message.getName());
            User user = new User();
            user.setName(message.getNickname());
            user.setHeadimg(imgUrl+message.getHeadimg());
            message.setGoods(goods);
            message.setUser(user);
        }
        return messages;
    }

    @Override
    public void reply(Message message) throws SQLException {
        messageDao.reply(message);
        messageDao.updateReplyMsg(message);
    }

    @Override
    public List<Message> repliedMsg() throws SQLException, IOException {
        List<Message> messages = messageDao.repliedMsg();
        //图片地址
        Properties properties = new Properties();
        InputStream resourceAsStream = ClientGoodsServiceImpl.class.getClassLoader().getResourceAsStream("imgUrl.properties");
        properties.load(resourceAsStream);
        String imgUrl = properties.getProperty("imgUrl");
        for (Message message : messages) {
            Goods goods = new Goods();
            goods.setName(message.getName());
            message.setGoods(goods);
            User user = new User();
            user.setName(message.getNickname());
            user.setHeadimg(imgUrl+message.getHeadimg());
            message.setUser(user);
        }
        return messages;
    }
}
