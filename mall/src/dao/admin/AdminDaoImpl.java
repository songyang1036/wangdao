/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2019/8/30
 * Time: 11:27
 */
package dao.admin;

import dao.admin.AdminDao;
import model.Admin;
import model.Message;
import model.User;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import util.DruidUtils;

import java.sql.SQLException;
import java.util.List;

public class AdminDaoImpl implements AdminDao {


    @Override
    public boolean login(Admin admin) throws SQLException {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        Long query = (Long) runner.query("select count(id) from admin where account = ? and pwd = ?",
                new ScalarHandler(), admin.getAccount(), admin.getPwd());
        if (query == 1) {
            return true;
        }
        return false;
    }

    @Override
    public List<Admin> showAccount() throws SQLException {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        List<Admin> query = runner.query("select * from admin", new BeanListHandler<Admin>(Admin.class));
        return query;
    }

    @Override
    public Boolean addAdmins(Admin admin) throws SQLException {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        int update = runner.update("insert into admin values(null,?,?,?)", admin.getAccount(), admin.getName(), admin.getPwd());
        return update > 0;
    }

    @Override
    public Boolean deleteAdmins(int id) throws SQLException {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        int update = runner.update("delete from admin where id=?", id);
        return update > 0;
    }

    @Override
    public Admin getAdminsInfo(int id) throws SQLException {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        Admin admin = runner.query("select * from admin where id = ?", new BeanHandler<Admin>(Admin.class), id);

        return admin;
    }

    @Override
    public Boolean updateAdmins(Admin admin) throws SQLException {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        int update = runner.update("update admin set account = ?,name = ?,pwd = ? where id = ?;", admin.getAccount(), admin.getName(), admin.getPwd(), admin.getId());
        return update > 0;
    }

    @Override
    public List<Admin> getSearchAdmins(String sql,List<Object> list) throws SQLException {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        List<Admin> admins = runner.query(sql, new BeanListHandler<Admin>(Admin.class), list.toArray());
        return admins;
    }

    @Override
    public Boolean changePwd(Admin admin) throws SQLException {
        QueryRunner runner = new QueryRunner(DruidUtils.getDataSource());
        Admin query = runner.query("select * from admin where account=?",
                new BeanHandler<Admin>(Admin.class),
                admin.getAdminName());
        if (query.getPwd().equals(admin.getOldPwd())) {
            int update = runner.update("update admin set pwd =? where account=?", admin.getNewPwd(), admin.getAdminName());
            return update > 0;
        }
        return false;
    }



}
