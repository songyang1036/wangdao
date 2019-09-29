package dao.admin;

import model.Message;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import util.DruidUtils;

import java.sql.SQLException;
import java.util.List;

public class MessageDaoImpl implements MessageDao {
    @Override
    public List<Message> noReplyMsg() throws SQLException {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        List<Message> messages = runner.query("select u.headimg,u.nickname,g.name,m.id,m.userId,m.goodsId,m.content,m.state,m.createtime from message as m left join user as u on u.id=m.userId left join goods as g on g.id=m.goodsId where state = '1'", new BeanListHandler<Message>(Message.class));
        return messages;
    }

    @Override
    public void reply(Message message) throws SQLException {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        runner.update("update message set state = '0' where id = ?",message.getId());
    }

    @Override
    public List<Message> repliedMsg() throws SQLException {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        List<Message> messages = runner.query("select u.headimg,u.nickname,g.name,m.id,m.userId,m.goodsId,m.content,r.content as replyContent,m.state,m.createtime from message as m left join user as u on u.id=m.userId left join goods as g on g.id=m.goodsId left join replyMsg as r on m.id=r.mid where state = '0'", new BeanListHandler<Message>(Message.class));
        return messages;
    }

    @Override
    public void updateReplyMsg(Message message) throws SQLException {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        runner.update("insert into replyMsg values(null,?,?,now())",message.getId(),message.getContent());

    }
}
