package controller.admin;

import com.google.gson.Gson;
import model.User;
import service.admin.UserService;
import service.admin.UserServiceImpl;
import util.HttpUtils;
import util.Result;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(value = "/api/admin/user/*")
public class UserServlet extends HttpServlet {

    private UserService userService = new UserServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        requestURI = requestURI.replace("/api/admin/user/","");

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        requestURI = requestURI.replace("/api/admin/user/","");
        if("allUser".equals(requestURI)){
            allUser(request,response);
        }else if("deleteUser".equals(requestURI)){
            deleteUser(request,response);
        }else if("searchUser".equals(requestURI)){
            searchUser(request,response);
        }
    }

    private void searchUser(HttpServletRequest request, HttpServletResponse response) {
        String word = request.getParameter("word");
        Result result = new Result();
        Gson gson = new Gson();
        List<User> users = null;
        try {
            users = userService.searchUser(word);
            result.setCode(0);
            result.setData(users);
            String jsonResult = gson.toJson(result);
            HttpUtils.toResponseBody(jsonResult,response);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void deleteUser(HttpServletRequest request, HttpServletResponse response) {
        //缺一个数据校验
        String sid = request.getParameter("id");
        int id = Integer.parseInt(sid);
        Gson gson = new Gson();
        try {
            Boolean flag = userService.deleteUser(id);
            if(flag){
                Result result = new Result();
                result.setCode(0);
                String jsonResult = gson.toJson(result);
                HttpUtils.toResponseBody(jsonResult,response);
            }else {
                response.getWriter().print("删除失败！");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void allUser(HttpServletRequest request, HttpServletResponse response) {
        Result result = new Result();
        Gson gson = new Gson();
        try {
            List<User> users = userService.allUser();
            result.setCode(0);
            result.setData(users);
            String jsonResult = gson.toJson(result);
            HttpUtils.toResponseBody(jsonResult,response);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
