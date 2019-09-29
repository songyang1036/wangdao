/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2019/8/30
 * Time: 11:24
 */
package service.admin;


import model.Admin;
import model.Message;
import model.User;

import java.sql.SQLException;
import java.util.List;

public interface AdminService {
    boolean login(Admin admin) throws SQLException;

    List<Admin> showAccount() throws SQLException;

    Boolean addAdmins(Admin admin) throws SQLException;

    Boolean deleteAdmins(int id) throws SQLException;

    Admin getAdminsInfo(int id) throws SQLException;

    Boolean updateAdmins(Admin admin) throws SQLException;

    List<Admin> getSearchAdmins(Admin admin) throws SQLException;

    Boolean changePwd(Admin admin) throws SQLException;

}
