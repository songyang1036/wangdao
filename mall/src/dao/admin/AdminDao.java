/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2019/8/30
 * Time: 11:27
 */
package dao.admin;


import model.Admin;
import model.Message;
import model.User;

import java.sql.SQLException;
import java.util.List;

public interface AdminDao {
    boolean login(Admin admin) throws SQLException;

    List<Admin> showAccount() throws SQLException;

    Boolean addAdmins(Admin admin) throws SQLException;

    Boolean deleteAdmins(int id) throws SQLException;

    Admin getAdminsInfo(int id) throws SQLException;

    Boolean updateAdmins(Admin admin) throws SQLException;

    List<Admin> getSearchAdmins(String sql,List<Object> list) throws SQLException;

    Boolean changePwd(Admin admin) throws SQLException;

}
