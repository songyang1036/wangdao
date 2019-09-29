package util;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class FileUploadUtils {

    public static HashMap<String, Object> parseRequest(HttpServletRequest request) {
        HashMap<String, Object> map = new HashMap<>();
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletContext servletContext = request.getServletContext();
        File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
        factory.setRepository(repository);
        ServletFileUpload upload = new ServletFileUpload(factory);
        try {
            List<FileItem> fileItems = upload.parseRequest(request);
            Iterator<FileItem> iterator = fileItems.iterator();
            while (iterator.hasNext()) {
                FileItem item = iterator.next();
                if (item.isFormField()) {
                    processFormField(item, map);
                } else {
                    processFileUpload(item, map, request);
                }
            }
        } catch (FileUploadException e) {
            e.printStackTrace();
        }
        return map;
    }

    private static void processFileUpload(FileItem item, HashMap<String, Object> map, HttpServletRequest request) {
        String fileName = item.getName();
        String fieldName = item.getFieldName();
        String uuid = UUID.randomUUID().toString();
        fileName = uuid + "-" + fileName;//添加随机编号
        String upload = "/static/image";
        /*int hashCode = fileName.hashCode();//将上传文件均匀分配到八个文件夹
        String s = Integer.toHexString(hashCode);//转十六进制
        char[] chars = s.toCharArray();
        for (char aChar : chars) {
            upload = upload + "/" + aChar;
        }*/
        fileName = upload + "/" + fileName;
        upload = request.getServletContext().getRealPath(fileName);
        File file = new File(upload);
        if(!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }
        try {
            item.write(file);
            map.put(fieldName, fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void processFormField(FileItem item, HashMap<String, Object> map) {
        String value = null;
        String name = item.getFieldName();
        try {
            value = item.getString("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        map.put(name,value);
/*        System.out.println(name);
        System.out.println(value);*/
    }
}
