package controller.admin;

import com.google.gson.Gson;
import model.Goods;
import model.GoodsSpecs;
import model.GoodsType;
import model.SpecList;
import org.apache.commons.beanutils.BeanUtils;
import service.admin.GoodsService;
import service.admin.GoodsServiceImpl;
import util.FileUploadUtils;
import util.HttpUtils;
import util.Result;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

@WebServlet(value = "/api/admin/goods/*")
public class GoodsServlet extends HttpServlet {

    GoodsService goodsService = new GoodsServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        requestURI = requestURI.replace("/api/admin/goods/", "");
        if ("addType".equals(requestURI)) {
            addType(request, response);
        } else if ("addGoods".equals(requestURI)) {
            addGoods(request, response);
        } else if ("imgUpload".equals(requestURI)) {
            imgUpload(request, response);
        }else if("updateGoods".equals(requestURI)){
            updateGoods(request,response);
        }else if("addSpec".equals(requestURI)){
            addSpec(request,response);
        }else if("deleteSpec".equals(requestURI)){
            deleteSpec(request,response);
        }
    }

    private void deleteSpec(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Result result = new Result();
        Gson gson = new Gson();
        String jsonStr = HttpUtils.getRequestBody(request);
        GoodsSpecs goodsSpecs = gson.fromJson(jsonStr, GoodsSpecs.class);
        try {
            goodsService.deleteSpec(goodsSpecs);
            result.setCode(0);
            String jsonResult = gson.toJson(result);
            HttpUtils.toResponseBody(jsonResult, response);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addSpec(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Result result = new Result();
        Gson gson = new Gson();
        String jsonStr = HttpUtils.getRequestBody(request);
        GoodsSpecs goodsSpecs = gson.fromJson(jsonStr, GoodsSpecs.class);
        try {
            boolean b = goodsService.addSpec(goodsSpecs);
            if (b) {
                result.setCode(0);
                result.setData(goodsSpecs);
                String jsonResult = gson.toJson(result);
                HttpUtils.toResponseBody(jsonResult, response);
            }else {
                result.setCode(10000);
                result.setData(null);
                result.setMessage("分类重复!");
                String jsonResult = gson.toJson(result);
                HttpUtils.toResponseBody(jsonResult, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateGoods(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Result result = new Result();
        Gson gson = new Gson();
        String jsonStr = HttpUtils.getRequestBody(request);
        Goods goods = gson.fromJson(jsonStr, Goods.class);
        try {
            Boolean flag = goodsService.updateGoods(goods);
            if(flag){
                result.setCode(0);
                String jsonResult = gson.toJson(result);
                HttpUtils.toResponseBody(jsonResult, response);
            } else {
                response.getWriter().print("修改失败");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void imgUpload(HttpServletRequest request, HttpServletResponse response) {
        HashMap<String, Object> map = FileUploadUtils.parseRequest(request);
        Goods goods = new Goods();
        try {
            BeanUtils.populate(goods, map);
            Result result = new Result();
            Gson gson = new Gson();
            result.setCode(0);
            result.setData(goods.getFile());
            String jsonResult = gson.toJson(result);
            HttpUtils.toResponseBody(jsonResult, response);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void addGoods(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Result result = new Result();
        Gson gson = new Gson();
        String jsonStr = HttpUtils.getRequestBody(request);
        Goods goods = gson.fromJson(jsonStr, Goods.class);
        try {
            goodsService.addGoods(goods);
            result.setCode(0);
            String jsonResult = gson.toJson(result);
            HttpUtils.toResponseBody(jsonResult, response);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void addType(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        Result result = new Result();
        String jsonStr = HttpUtils.getRequestBody(request);
        GoodsType goodsType = gson.fromJson(jsonStr, GoodsType.class);
        try {
            Boolean flag = goodsService.addType(goodsType);
            if (flag) {
                result.setCode(0);
                String jsonResult = gson.toJson(result);
                HttpUtils.toResponseBody(jsonResult, response);
            } else {
                //response.getWriter().print("添加失败");
                result.setCode(10000);
                result.setMessage("该种类已经存在!");
                String jsonResult = gson.toJson(result);
                HttpUtils.toResponseBody(jsonResult, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String requestURI = request.getRequestURI();
        requestURI = requestURI.replace("/api/admin/goods/", "");
        if ("getType".equals(requestURI)) {
            getType(request, response);
        } else if ("getGoodsByType".equals(requestURI)) {
            getGoodsByType(request, response);
        } else if ("deleteGoods".equals(requestURI)) {
            deleteGoods(request, response);
        }else if("getGoodsInfo".equals(requestURI)){
            getGoodsInfo(request,response);
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
        try {
            SpecList relGoods = goodsService.getGoodsInfo(id);
            result.setCode(0);
            result.setData(relGoods);
            String jsonResult = gson.toJson(result);
            HttpUtils.toResponseBody(jsonResult, response);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteGoods(HttpServletRequest request, HttpServletResponse response) {
        Gson gson = new Gson();
        Result result = new Result();
        String id = request.getParameter("id");
        //检验数据
        try {
            Integer.parseInt(id);
        } catch (Exception e) {
            return;
        }
        try {
            Boolean flag = goodsService.deleteGoods(id);
            if (flag) {
                result.setCode(0);
                String jsonResult = gson.toJson(result);
                HttpUtils.toResponseBody(jsonResult, response);
            } else {
                response.getWriter().print("删除失败");
            }
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
        try {
            List<Goods> goodsList = goodsService.getGoodsByType(typeId);
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
}
