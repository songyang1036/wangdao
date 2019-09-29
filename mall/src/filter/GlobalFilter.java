package filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2019/8/30
 * Time: 10:48
 */
@WebFilter(filterName = "GlobalFilter",urlPatterns = "/api/admin/*")
public class GlobalFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        request.setCharacterEncoding("utf-8");
        String url = request.getRequestURI();

        String[] img = {"png","gif","css","js","jpg"};
        if(!isContains(url,img)) {
            resp.setContentType("application/json;charset=UTF-8");
        }
        //String requestURI = request.getRequestURI();
        //这一步用来替换掉前面的/api/admin然后后面只有login allAdmins updateAdmin 做相应的分发
        url = url.replace("/api/admin/admin/","");
        //System.out.println(url);
//        if("login".equals(url)){
//            chain.doFilter(request, response);
//        }



        //这里仅仅对get和post做处理，options不做处理，因为options请求不会带上cookie
        //把后台系统的所有接口拦住，但是需要放行登录login接口
        if("GET".equals(request.getMethod()) || "POST".equals(request.getMethod())){

            HttpSession session = request.getSession();
            String username = (String) session.getAttribute("username");
            //System.out.println(username);
            if( username == null && !"login".equals(url)){

                //直接返回一串响应报文
                response.getWriter().print("请先登录");
                return;
            }
            //System.out.println(request.getMethod() + "===" + session.getId());
        }



        //response.setContentType("application/json;charset=UTF-8");
        chain.doFilter(request, response);
        //这个可以放在配置文件里，读取到context域里。然后直接取
        String origin = "http://localhost:8080";
        //String origin = "http://192.168.3.48:8080";
        //直接把origin写在配置文件里，从配置文件里读取
        //处于安全性考虑，当从该域名发送过来的请求，可以带上cookie,跨域（）默认不会带上cookie，需要设置一些参数
        //这三个头与跨域访问有关，如果不设置，则访问不成功
        response.addHeader("Access-Control-Allow-Origin", origin);
        response.addHeader("Access-Control-Allow-Methods", "POST,GET,OPTIONS,PUT,DELETE");
        response.addHeader("Access-Control-Allow-Headers", "x-requested-with,Authorization,Content-Type");
        response.setHeader("Access-Control-Allow-Credentials", "true");
    }
    private boolean isContains(String url, String[] img) {
        String last = url.substring(url.lastIndexOf(".")+1);
        for (String s : img) {
            //System.out.println(s);
            if(last.equals(s)){
                return true;
            }
        }
        return false;
    }
    public void init(FilterConfig config) throws ServletException {

    }

}
