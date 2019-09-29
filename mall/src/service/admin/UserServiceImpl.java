package service.admin;

import dao.admin.UserDao;
import dao.admin.UserDaoImpl;
import model.User;

import java.sql.SQLException;
import java.util.List;

public class UserServiceImpl implements UserService {
    private UserDao userDao = new UserDaoImpl();
    @Override
    public List<User> searchUser(String word) throws SQLException {
        return userDao.searchUser(word);
    }

    @Override
    public Boolean deleteUser(int id) throws SQLException {
        return userDao.deleteUser(id);
    }

    @Override
    public List<User> allUser() throws SQLException {
        return userDao.allUser();
    }
}
