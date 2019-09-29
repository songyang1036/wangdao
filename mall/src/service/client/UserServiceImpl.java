package service.client;


import dao.client.UserDao;
import dao.client.UserDaoImpl;
import model.User;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;

public class UserServiceImpl implements UserService {

    private UserDao userDao = new UserDaoImpl();

    @Override
    public boolean login(User user) throws SQLException {
        return userDao.login(user);
    }

    @Override
    public Boolean signup(User user) throws SQLException {
        return userDao.signup(user);
    }

    @Override
    public User getUserData(String token) throws SQLException, IOException {
        User user = userDao.getUserData(token);

        //图片地址
        Properties properties = new Properties();
        InputStream resourceAsStream = ClientGoodsServiceImpl.class.getClassLoader().getResourceAsStream("imgUrl.properties");
        properties.load(resourceAsStream);
        String imgUrl = properties.getProperty("imgUrl");
        user.setHeadimg(imgUrl+user.getHeadimg());
        return user;
    }

    @Override
    public Boolean updatePwd(User user) throws SQLException {
        String password = userDao.queryUserPwd(user.getId());
        if(user.getOldPwd().equals(password)){
            return userDao.updatePwd(user);
        }
        return false;
    }

    @Override
    public void updateUserData(User user) throws SQLException {
        userDao.updateUserData(user);
    }
}
