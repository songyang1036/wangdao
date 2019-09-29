package controller.admin;

import com.google.gson.Gson;
import model.Message;
import service.admin.MessageService;
import service.admin.MessageServiceImpl;
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

@WebServlet(value = "/api/admin/message/*")
public class MessageServlet extends HttpServlet {

    private MessageService messageService = new MessageServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        requestURI = requestURI.replace("/api/admin/message/","");
        if("reply".equals(requestURI)){
            reply(request,response);
        }
    }

    private void reply(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String jsonStr = HttpUtils.getRequestBody(request);
        Gson gson = new Gson();
        Result result = new Result();
        Message message = gson.fromJson(jsonStr, Message.class);
        try {
            messageService.reply(message);
            result.setCode(0);
            String jsonResult = gson.toJson(result);
            HttpUtils.toResponseBody(jsonResult,response);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        requestURI = requestURI.replace("/api/admin/message/","");
        if("noReplyMsg".equals(requestURI)){
            noReplyMsg(request,response);
        }else if("repliedMsg".equals(requestURI)){
            repliedMsg(request,response);
        }
    }

    private void repliedMsg(HttpServletRequest request, HttpServletResponse response) {
        Gson gson = new Gson();
        Result result = new Result();
        try {
            List<Message> messages = messageService.repliedMsg();
            result.setCode(0);
            result.setData(messages);
            String jsonResult = gson.toJson(result);
            HttpUtils.toResponseBody(jsonResult,response);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void noReplyMsg(HttpServletRequest request, HttpServletResponse response) {
        Gson gson = new Gson();
        Result result = new Result();
        try {
            List<Message> messages = messageService.noReplyMsg();
            result.setCode(0);
            result.setData(messages);
            String jsonResult = gson.toJson(result);
            HttpUtils.toResponseBody(jsonResult,response);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
