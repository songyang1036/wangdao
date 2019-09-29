package controller.client;

import com.google.gson.Gson;
import model.User;
import service.client.UserServiceImpl;
import service.client.UserService;
import util.HttpUtils;
import util.Result;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(value = "/api/user/*")
public class UserServlet extends HttpServlet {

    private UserService userService = new UserServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        requestURI = requestURI.replace("/api/user/","");
        if("login".equals(requestURI)){
            login(request,response);
        }else if("signup".equals(requestURI)){
            signup(request,response);
        }else if("updatePwd".equals(requestURI)){
            updatePwd(request,response);
        }else if("updateUserData".equals(requestURI)){
            updateUserData(request,response);
        }
    }

    private void updateUserData(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Result result = new Result();
        Gson gson = new Gson();
        String jsonStr = HttpUtils.getRequestBody(request);
        User user = gson.fromJson(jsonStr, User.class);
        try {
            userService.updateUserData(user);
            result.setCode(0);
            String jsonResult = gson.toJson(result);
            HttpUtils.toResponseBody(jsonResult,response);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updatePwd(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Result result = new Result();
        Gson gson = new Gson();
        String jsonStr = HttpUtils.getRequestBody(request);
        User user = gson.fromJson(jsonStr, User.class);
        if(!user.getNewPwd().equals(user.getConfirmPwd())){
            response.getWriter().print("两次输入的密码不相同");
        }
        try {
            Boolean flag = userService.updatePwd(user);
            if (flag){
                result.setCode(0);
                String jsonResult = gson.toJson(result);
                HttpUtils.toResponseBody(jsonResult,response);
            }else {
                response.getWriter().print("修改密码失败");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void signup(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Result result = new Result();
        Gson gson = new Gson();
        String jsonStr = HttpUtils.getRequestBody(request);
        User user = gson.fromJson(jsonStr, User.class);
        try {
            Boolean flag = userService.signup(user);
            if(flag){
                user.setName(user.getNickname());
                user.setToken(user.getNickname());

                result.setCode(0);
                result.setData(user);
                HttpSession session = request.getSession();
                //System.out.println(request.getMethod() + "===" + session.getId());
                session.setAttribute("name",user.getName());
                String jsonResult = gson.toJson(result);
                HttpUtils.toResponseBody(jsonResult,response);
            }else {
                response.getWriter().print("用户名或密码错误！");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String jsonStr = HttpUtils.getRequestBody(request);
        Gson gson = new Gson();
        Result result = new Result();
        User user = gson.fromJson(jsonStr, User.class);
        try {
            boolean flag = userService.login(user);
            if(flag){
                user.setName(user.getAccount());
                user.setToken(user.getAccount());

                result.setCode(0);
                result.setData(user);
                HttpSession session = request.getSession();
                //System.out.println(request.getMethod() + "===" + session.getId());
                session.setAttribute("name",user.getName());
                String jsonResult = gson.toJson(result);
                HttpUtils.toResponseBody(jsonResult,response);
            }else {
                response.getWriter().print("用户名或密码错误！");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        requestURI = requestURI.replace("/api/user/","");
        if("data".equals(requestURI)){
            userData(request,response);
        }
    }

    private void userData(HttpServletRequest request, HttpServletResponse response) {
        Gson gson = new Gson();
        Result result = new Result();
        String token = request.getParameter("token");
        try {
            User user = userService.getUserData(token);
            result.setCode(0);
            result.setData(user);
            String jsonResult = gson.toJson(result);
            HttpUtils.toResponseBody(jsonResult,response);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
