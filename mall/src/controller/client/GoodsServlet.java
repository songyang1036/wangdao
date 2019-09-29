package controller.client;

import com.google.gson.Gson;
import model.*;
import service.client.ClientGoodsService;
import service.client.ClientGoodsServiceImpl;
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

@WebServlet(value = "/api/mall/goods/*")
public class GoodsServlet extends HttpServlet {

    ClientGoodsService goodsService = new ClientGoodsServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        requestURI = requestURI.replace("/api/mall/goods/","");
        if("askGoodsMsg".equals(requestURI)){
            askGoodsMsg(request,response);
        }
    }

    private void askGoodsMsg(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Result result = new Result();
        Gson gson = new Gson();
        String jsonStr = HttpUtils.getRequestBody(request);
        Message message = gson.fromJson(jsonStr, Message.class);
        try {
            goodsService.askGoodsMsg(message);
            result.setCode(0);
            String jsonResult = gson.toJson(result);
            HttpUtils.toResponseBody(jsonResult, response);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        requestURI = requestURI.replace("/api/mall/goods/","");
        if("getGoodsByType".equals(requestURI)){
            getGoodsByType(request,response);
        }else if("getGoodsInfo".equals(requestURI)){
            getGoodsInfo(request,response);
        }else if ("getGoodsMsg".equals(requestURI)){
            getGoodsMsg(request,response);
        }else if ("getGoodsComment".equals(requestURI)){
            getGoodsComment(request,response);
        }else if ("searchGoods".equals(requestURI)){
            searchGoods(request,response);
        }else if ("getType".equals(requestURI)){
            getType(request,response);
        }
    }

    private void getType(HttpServletRequest request, HttpServletResponse response) {
        Gson gson = new Gson();
        Result result = new Result();
        try {
            List<GoodsType> goodsTypes = goodsService.getType();
            result.setCode(0);
            result.setData(goodsTypes);
            String jsonResult = gson.toJson(result);
            HttpUtils.toResponseBody(jsonResult, response);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void searchGoods(HttpServletRequest request, HttpServletResponse response) {
        Gson gson = new Gson();
        Result result = new Result();
        String goodsName = request.getParameter("keyword");
        try {
            List<Goods> goodsList = goodsService.searchGoods(goodsName);
            result.setCode(0);
            result.setData(goodsList);
            String jsonResult = gson.toJson(result);
            HttpUtils.toResponseBody(jsonResult, response);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getGoodsComment(HttpServletRequest request, HttpServletResponse response) {
        Gson gson = new Gson();
        Result result = new Result();
        String goodsId = request.getParameter("goodsId");
        try {
            Integer.parseInt(goodsId);
        } catch (Exception e) {
            return;
        }
        try {
            RelComment relComment = goodsService.getGoodsComment(goodsId);
            result.setCode(0);
            result.setData(relComment);
            String jsonResult = gson.toJson(result);
            HttpUtils.toResponseBody(jsonResult, response);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getGoodsMsg(HttpServletRequest request, HttpServletResponse response) {
        Gson gson = new Gson();
        Result result = new Result();
        String id = request.getParameter("id");
        try {
            Integer.parseInt(id);
        } catch (Exception e) {
            return;
        }
        try {
            List<Message> msgs = goodsService.getGoodsMsg(id);
            result.setCode(0);
            result.setData(msgs);
            String jsonResult = gson.toJson(result);
            HttpUtils.toResponseBody(jsonResult, response);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getGoodsInfo(HttpServletRequest request, HttpServletResponse response) {
        Gson gson = new Gson();
        Result result = new Result();
        String id = request.getParameter("id");
        //检验数据
        try {
            Integer.parseInt(id);
        } catch (Exception e) {
            return;
        }
        SpecList rel = null;
        try {
            rel = goodsService.getGoodsInfo(id);
            result.setCode(0);
            result.setData(rel);
            String jsonResult = gson.toJson(result);
            HttpUtils.toResponseBody(jsonResult, response);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void getGoodsByType(HttpServletRequest request, HttpServletResponse response) {
        Gson gson = new Gson();
        Result result = new Result();
        String typeId = request.getParameter("typeId");
        //检验数据
        try {
            Integer.parseInt(typeId);
        } catch (Exception e) {
            return;
        }
        List<Goods> goodsList = null;
        try {
            goodsList = goodsService.getGoodsByType(typeId);
            result.setCode(0);
            result.setData(goodsList);
            String jsonResult = gson.toJson(result);
            HttpUtils.toResponseBody(jsonResult, response);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
