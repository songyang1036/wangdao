/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2019/8/30
 * Time: 11:19
 */
package util;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HttpUtils {
    public static String getRequestBody(HttpServletRequest request) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        byte[] bytes = new byte[1024];
        int length = 0;
        StringBuffer stringBuffer = new StringBuffer();
        while ((length =inputStream.read(bytes))!= -1){
            stringBuffer.append(new String(bytes,0,length));
        }
        return stringBuffer.toString();
    }

    public static void toResponseBody(String jsonResult, HttpServletResponse response) throws IOException {
        response.getWriter().println(jsonResult);
    }
}
