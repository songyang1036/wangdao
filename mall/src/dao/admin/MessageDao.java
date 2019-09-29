package dao.admin;

import model.Message;

import java.sql.SQLException;
import java.util.List;

public interface MessageDao {
    List<Message> noReplyMsg() throws SQLException;

    void reply(Message message) throws SQLException;

    List<Message> repliedMsg() throws SQLException;

    void updateReplyMsg(Message message) throws SQLException;
}
