package controller.client;

import com.google.gson.Gson;
import model.Comment;
import model.Orders;
import model.POrder;
import model.RelOrderCart;
import service.client.ClientOrderService;
import service.client.ClientOrderServiceImpl;
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

@WebServlet(value = "/api/mall/order/*")
public class OrderServlet extends HttpServlet {

    ClientOrderService orderService = new ClientOrderServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        requestURI = requestURI.replace("/api/mall/order/", "");
        if ("addOrder".equals(requestURI)){
            addOrder(request,response);
        }else if("settleAccounts".equals(requestURI)){
            settleAccounts(request,response);
        }else if ("sendComment".equals(requestURI)){
            sendComment(request,response);
        }
    }

    private void sendComment(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Result result = new Result();
        Gson gson = new Gson();
        String jsonStr = HttpUtils.getRequestBody(request);
        Comment comment = gson.fromJson(jsonStr, Comment.class);
        try {
            orderService.sendComment(comment);
            result.setCode(0);
            String jsonResult = gson.toJson(result);
            HttpUtils.toResponseBody(jsonResult,response);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void settleAccounts(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Result result = new Result();
        Gson gson = new Gson();
        String jsonStr = HttpUtils.getRequestBody(request);
        POrder order = gson.fromJson(jsonStr, POrder.class);
        try {
            orderService.settleAccounts(order);
            result.setCode(0);
            String jsonResult = gson.toJson(result);
            HttpUtils.toResponseBody(jsonResult,response);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addOrder(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Result result = new Result();
        Gson gson = new Gson();
        String jsonStr = HttpUtils.getRequestBody(request);
        Orders order = gson.fromJson(jsonStr, Orders.class);
        try {
            Boolean flag = orderService.addOrder(order);
            if (flag){
                result.setCode(0);
                String jsonResult = gson.toJson(result);
                HttpUtils.toResponseBody(jsonResult,response);
            }else {
                result.setCode(1000);
                result.setData(null);
                result.setMessage("库存容量不够!");
                result.setStatus(null);
                String jsonResult = gson.toJson(result);
                HttpUtils.toResponseBody(jsonResult,response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        requestURI = requestURI.replace("/api/mall/order/", "");
        if ("getOrderByState".equals(requestURI)){
            getOrderByState(request,response);
        }else if ("deleteOrder".equals(requestURI)){
            deleteOrder(request,response);
        }else if ("pay".equals(requestURI)){
            pay(request,response);
        }else if ("confirmReceive".equals(requestURI)){
            confirmReceive(request,response);
        }
    }

    private void confirmReceive(HttpServletRequest request, HttpServletResponse response) {
        Result result = new Result();
        Gson gson = new Gson();
        String id = request.getParameter("id");
        try {
            Integer.parseInt(id);
        } catch (Exception e) {
            return;
        }
        try {
            orderService.confirmReceive(id);
            result.setCode(0);
            String jsonResult = gson.toJson(result);
            HttpUtils.toResponseBody(jsonResult,response);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void pay(HttpServletRequest request, HttpServletResponse response) {
        Result result = new Result();
        Gson gson = new Gson();
        String id = request.getParameter("id");
        try {
            Integer.parseInt(id);
        } catch (Exception e) {
            return;
        }
        try {
            orderService.pay(id);
            result.setCode(0);
            String jsonResult = gson.toJson(result);
            HttpUtils.toResponseBody(jsonResult,response);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteOrder(HttpServletRequest request, HttpServletResponse response) {
        Result result = new Result();
        Gson gson = new Gson();
        String id = request.getParameter("id");
        try {
            Integer.parseInt(id);
        } catch (Exception e) {
            return;
        }
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

    private void getOrderByState(HttpServletRequest request, HttpServletResponse response) {
        Result result = new Result();
        Gson gson = new Gson();
        String state = request.getParameter("state");
        try {
            Integer.parseInt(state);
        } catch (Exception e) {
            return;
        }
        String token = request.getParameter("token");
        try {
            List<RelOrderCart> orderCartList = orderService.getOrderByState(state,token);
            result.setCode(0);
            result.setData(orderCartList);
            String jsonResult = gson.toJson(result);
            HttpUtils.toResponseBody(jsonResult,response);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
