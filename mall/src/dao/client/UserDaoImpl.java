package dao.client;


import model.User;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import util.DruidUtils;

import java.sql.SQLException;

public class UserDaoImpl implements UserDao {
    @Override
    public boolean login(User user) throws SQLException {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        Long query = (Long) runner.query("select count(id) from user where nickname = ? and pwd = ?",
                new ScalarHandler(), user.getAccount(), user.getPwd());
        if (query == 1) {
            return true;
        }
        return false;
    }

    @Override
    public Boolean signup(User user) throws SQLException {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        int update = runner.update("insert into user values(null,?,?,'男',?,?,?,?,'/static/image/user1.jpg')", user.getEmail(), user.getNickname(), user.getPwd(), user.getRecipient(), user.getAddress(), user.getPhone());
        return update>0;
    }

    @Override
    public User getUserData(String token) throws SQLException {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        User user = runner.query("select * from user where nickname = ?", new BeanHandler<>(User.class), token);
        return user;
    }

    @Override
    public String queryUserPwd(int id) throws SQLException {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        String pwd = (String) runner.query("select user.pwd from user where id = ?",new ScalarHandler(),id);
        return pwd;
    }

    @Override
    public Boolean updatePwd(User user) throws SQLException {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        int update = runner.update("update user set pwd = ? where id = ?", user.getNewPwd(), user.getId());
        return update>0;
    }

    @Override
    public void updateUserData(User user) throws SQLException {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        runner.update("update user set nickname = ?,recipient=?,address=?,phone=? where id = ?", user.getNickname(),user.getRecipient(),user.getAddress(),user.getPhone(), user.getId());
    }
}
