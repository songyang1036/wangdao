package service.client;

import model.User;

import java.io.IOException;
import java.sql.SQLException;

public interface UserService {
    boolean login(User user) throws SQLException;

    Boolean signup(User user) throws SQLException;

    User getUserData(String token) throws SQLException, IOException;

    Boolean updatePwd(User user) throws SQLException;

    void updateUserData(User user) throws SQLException;
}
