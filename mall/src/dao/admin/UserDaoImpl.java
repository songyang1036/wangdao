package dao.admin;

import model.User;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import util.DruidUtils;

import java.sql.SQLException;
import java.util.List;

public class UserDaoImpl implements UserDao {
    @Override
    public List<User> allUser() throws SQLException {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        List<User> query = runner.query("select * from user", new BeanListHandler<User>(User.class));
        return query;
    }

    @Override
    public Boolean deleteUser(int id) throws SQLException {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        int update = runner.update("delete from user where id=?", id);
        return update > 0;
    }

    @Override
    public List<User> searchUser(String word) throws SQLException {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
//        List<User> query =null;
//        if ("".equals(word)) {
//            query = runner.query("select * from user", new BeanListHandler<User>(User.class));
//        }else {
//            query = runner.query("select * from user where nickname=?", new BeanListHandler<User>(User.class), word);
//        }
        List<User> query = runner.query("select * from user where nickname like ?", new BeanListHandler<User>(User.class), "%"+word+"%");
        return query;
    }
}
