package util;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2019/8/6
 * Time: 14:56
 */

public class DruidUtils {

    private static DataSource dataSource;
    private static ThreadLocal<Connection> threadLocal = new ThreadLocal<>();

    static {
        //dataSource = new DruidDataSource();
        InputStream resourceAsStream = DruidUtils.class.getClassLoader().getResourceAsStream("druid.properties");
        Properties properties = new Properties();
        try {
            properties.load(resourceAsStream);
            dataSource = new DruidDataSourceFactory().createDataSource(properties);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
    /**
     * flag表示是否开启事务，true表示开启事务
     * @param flag
     * @return
     */
    public static Connection getConnection(boolean flag) throws SQLException {
        if(flag){
            Connection connection = threadLocal.get();
            //如果是null，表示第一次，那么要放入ThreadLocal中
            if(connection == null){
                connection = getConnection();
                threadLocal.set(connection);
            }
            return connection;
        }
        return getConnection();
    }
    public static void setNull(){
        threadLocal.set(null);
    }
    public static DataSource getDataSource(){
        return dataSource;
    }
}
