package dao.client;

import model.User;

import java.sql.SQLException;

public interface UserDao {
    boolean login(User user) throws SQLException;

    Boolean signup(User user) throws SQLException;

    User getUserData(String token) throws SQLException;

    String queryUserPwd(int id) throws SQLException;

    Boolean updatePwd(User user) throws SQLException;

    void updateUserData(User user) throws SQLException;
}
