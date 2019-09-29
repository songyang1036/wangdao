package controller.admin;

import com.google.gson.Gson;
import model.Admin;
import model.Message;
import service.admin.AdminService;
import service.admin.AdminServiceImpl;
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
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2019/8/30
 * Time: 10:31
 * 用来处理后台管理系统的管理员管理模块，包含登录，增删改查功能
 */
@WebServlet(value = "/api/admin/admin/*")
public class AdminServlet extends HttpServlet {

    private AdminService adminService = new AdminServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        //这一步用来替换掉前面的/api/admin然后后面只有login allAdmins updateAdmin 做相应的分发
        requestURI = requestURI.replace("/api/admin/admin/","");
        //分发 然后获取请求参数，进行具体业务逻辑
        if("login".equals(requestURI)){
            login(request,response);
        }else if("addAdminss".equals(requestURI)){
            //System.out.println(requestURI);
            addAdmins(request,response);
        }else if("updateAdminss".equals(requestURI)){
            updateAdmins(request,response);
        }else if("getSearchAdmins".equals(requestURI)){
            getSearchAdmins(request,response);
        }else if("changePwd".equals(requestURI)){
            changePwd(request,response);
        }
    }

    private void changePwd(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String jsonStr = HttpUtils.getRequestBody(request);
        Gson gson = new Gson();
        Admin admin = gson.fromJson(jsonStr, Admin.class);
        if(!admin.getNewPwd().equals(admin.getConfirmPwd())){
            response.getWriter().print("两次输入的密码不相同");
        }
        try {
            Boolean flag = adminService.changePwd(admin);
            if(flag){
                Result result = new Result();
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

    private void getSearchAdmins(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String jsonStr = HttpUtils.getRequestBody(request);
        Gson gson = new Gson();
        Admin admin = gson.fromJson(jsonStr, Admin.class);
        Result result = new Result();
        try {
            //对前端传过来的数据进行验证
            //若需要的是int double 等数据类型,需要对这些数据类型进行检验
            //因为传过来的数据是String类型,需要转化为需要的类型
            /*
            try{
                if(!StringUtil.isEmpty(admin.getPrice())){
                    Double.parseDouble(admin.getPrice());
                }
            }catch(Exception e){
                //如果price不是数字就会报异常,为空的话,转到service层处理
                return;
            }
            */

            List<Admin> accounts = adminService.getSearchAdmins(admin);
            result.setCode(0);
            result.setData(accounts);
            String jsonResult = gson.toJson(result);
            HttpUtils.toResponseBody(jsonResult,response);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateAdmins(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String jsonStr = HttpUtils.getRequestBody(request);
        Gson gson = new Gson();
        Admin admin = gson.fromJson(jsonStr, Admin.class);
        try {
            Boolean flag = adminService.updateAdmins(admin);
            if(flag){
                Result result = new Result();
                result.setCode(0);
                String jsonResult = gson.toJson(result);
                HttpUtils.toResponseBody(jsonResult,response);
            }else {
                response.getWriter().print("修改失败");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addAdmins(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String jsonStr = HttpUtils.getRequestBody(request);
        Gson gson = new Gson();
        Admin admin = gson.fromJson(jsonStr, Admin.class);
        try {
            Boolean flag = adminService.addAdmins(admin);
            if(flag){
                //response.getWriter().print("添加成功");
                Result result = new Result();
                result.setCode(0);
                String jsonResult = gson.toJson(result);
                HttpUtils.toResponseBody(jsonResult,response);
            }else {
                response.getWriter().print("添加失败");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * 处理登录逻辑，从request中获取请求参数，然后将响应结果写入response
     * @param request
     * @param response
     */
    private void login(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String jsonStr = HttpUtils.getRequestBody(request);
        Gson gson = new Gson();
        Admin admin = gson.fromJson(jsonStr, Admin.class);
//        HttpSession session = request.getSession();
//        session.setAttribute("username",admin.getAccount());
        try {
            boolean flag = adminService.login(admin);
            if(flag){
                //返回前端所需要的数据格式
                admin.setName(admin.getAccount());
                admin.setToken(admin.getAccount());
                Result result = new Result();
                result.setCode(0);
                result.setData(admin);
                HttpSession session = request.getSession();
                //System.out.println(request.getMethod() + "===" + session.getId());
                session.setAttribute("username",admin.getAccount());
                //Object username = session.getAttribute("username");
                //System.out.println(username);
                //接下来要把result变成jsonStr，然后塞到response中
                String jsonResult = gson.toJson(result);
                HttpUtils.toResponseBody(jsonResult,response);

            }else{
                response.getWriter().print("用户名或密码错误！");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            //写入json，和前端协商，返回一个什么类型的结果
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        //这一步用来替换掉前面的/api/admin然后后面只有login allAdmins updateAdmin 做相应的分发
        requestURI = requestURI.replace("/api/admin/admin/","");
        if("allAdmins".equals(requestURI)){
            allAdmins(request,response);
        }else if("deleteAdmins".equals(requestURI)){
            deleteAdmins(request,response);
        }else if("getAdminsInfo".equals(requestURI)){
            getAdminsInfo(request,response);
        }
    }



    private void getAdminsInfo(HttpServletRequest request, HttpServletResponse response) {
        //缺一个数据校验
        String sid = request.getParameter("id");
        int id = Integer.parseInt(sid);
        Gson gson = new Gson();
        Result result = new Result();
        try {
            Admin account = adminService.getAdminsInfo(id);
            result.setCode(0);
            result.setData(account);
            String jsonResult = gson.toJson(result);
            HttpUtils.toResponseBody(jsonResult,response);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteAdmins(HttpServletRequest request, HttpServletResponse response) {
        //缺一个数据校验
        String sid = request.getParameter("id");
        int id = Integer.parseInt(sid);
        Gson gson = new Gson();
        try {
            Boolean flag = adminService.deleteAdmins(id);
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

    private void allAdmins(HttpServletRequest request, HttpServletResponse response) {
        List<Admin> accounts = null;
        Result result = new Result();
        Gson gson = new Gson();
        try {
            accounts = adminService.showAccount();
            result.setCode(0);
            result.setData(accounts);
            String jsonResult = gson.toJson(result);
            HttpUtils.toResponseBody(jsonResult,response);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
