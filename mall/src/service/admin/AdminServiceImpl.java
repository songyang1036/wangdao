/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2019/8/30
 * Time: 11:25
 */
package service.admin;


import dao.admin.AdminDao;
import dao.admin.AdminDaoImpl;
import model.Admin;
import model.Message;
import model.User;
import util.StringUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminServiceImpl implements AdminService {

    private AdminDao adminDao = new AdminDaoImpl();
    @Override
    public boolean login(Admin admin) throws SQLException {
       return adminDao.login(admin);
    }

    @Override
    public List<Admin> showAccount() throws SQLException {
        return adminDao.showAccount();
    }

    @Override
    public Boolean addAdmins(Admin admin) throws SQLException {
        return adminDao.addAdmins(admin);
    }

    @Override
    public Boolean deleteAdmins(int id) throws SQLException {
        return adminDao.deleteAdmins(id);
    }

    @Override
    public Admin getAdminsInfo(int id) throws SQLException {
        return adminDao.getAdminsInfo(id);
    }

    @Override
    public Boolean updateAdmins(Admin admin) throws SQLException {
        return adminDao.updateAdmins(admin);
    }

    @Override
    public List<Admin> getSearchAdmins(Admin admin) throws SQLException {
        //sql拼接  对名字进行模糊查询
        String sql = "select * from admin where 1=1 ";
        List<Object> list = new ArrayList<>();
        if(!StringUtil.isEmpty(admin.getAccount())){
            sql += " and account like ? ";
            list.add("%"+admin.getAccount()+"%");
        }
        if(!StringUtil.isEmpty(admin.getName())){
            sql += " and name like ?";
            list.add("%"+admin.getName()+"%");
        }

        return adminDao.getSearchAdmins(sql,list);
    }

    @Override
    public Boolean changePwd(Admin admin) throws SQLException {
        return adminDao.changePwd(admin);
    }


}
