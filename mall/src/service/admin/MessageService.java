package service.admin;

import model.Message;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface MessageService {
    List<Message> noReplyMsg() throws SQLException, IOException;

    void reply(Message message) throws SQLException;

    List<Message> repliedMsg() throws SQLException, IOException;

}
