package dao.admin;

import model.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDao {
    List<User> searchUser(String word) throws SQLException;

    Boolean deleteUser(int id) throws SQLException;

    List<User> allUser() throws SQLException;

}
