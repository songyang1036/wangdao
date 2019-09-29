package controller.admin;

import com.google.gson.Gson;
import model.*;
import service.admin.OrderService;
import service.admin.OrderServiceImpl;
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

@WebServlet(value = "/api/admin/order/*")
public class OrderServlet extends HttpServlet {

    OrderService orderService = new OrderServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        requestURI = requestURI.replace("/api/admin/order/", "");
        if("ordersByPage".equals(requestURI)){
            ordersByPage(request,response);
        }else if("changeOrder".equals(requestURI)){
            changeOrder(request,response);
        }
    }

    private void changeOrder(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Result result = new Result();
        Gson gson = new Gson();
        String jsonStr = HttpUtils.getRequestBody(request);
        Orders order = gson.fromJson(jsonStr, Orders.class);
        try {
            orderService.changeOrder(order);
            result.setCode(0);
            String jsonResult = gson.toJson(result);
            HttpUtils.toResponseBody(jsonResult,response);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void ordersByPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Result result = new Result();
        Gson gson = new Gson();
        String jsonStr = HttpUtils.getRequestBody(request);
        OrderPage orderPage = gson.fromJson(jsonStr, OrderPage.class);
        try {
            ResultOrderPage orders = orderService.ordersByPage(orderPage);
            result.setCode(0);
            result.setData(orders);
            String jsonResult = gson.toJson(result);
            HttpUtils.toResponseBody(jsonResult,response);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        requestURI = requestURI.replace("/api/admin/order/", "");
        if("order".equals(requestURI)){
            order(request,response);
        }else if("deleteOrder".equals(requestURI)){
            deleteOrder(request,response);
        }
    }

    private void deleteOrder(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        //检验数据
        try {
            Integer.parseInt(id);
        } catch (Exception e) {
            return;
        }
        Gson gson = new Gson();
        Result result = new Result();
        try {
            orderService.deleteOrder(id);
            result.setCode(0);
            String jsonResult = gson.toJson(result);
            HttpUtils.toResponseBody(jsonResult,response);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void order(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        //检验数据
        try {
            Integer.parseInt(id);
        } catch (Exception e) {
            return;
        }
        Gson gson = new Gson();
        Result result = new Result();
        try {
            RelEditOrder relEditOrder = orderService.order(id);
            result.setCode(0);
            result.setData(relEditOrder);
            String jsonResult = gson.toJson(result);
            HttpUtils.toResponseBody(jsonResult,response);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
