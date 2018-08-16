import com.genequ.ticketmanagement.util.MD5Util;
import org.junit.Test;


import java.sql.*;
import java.text.SimpleDateFormat;

import java.util.Random;

public class insetSQL {
    private String url = "jdbc:mysql://localhost:3306/ticketmanagement?serverTimezone=GMT%2B8&useSSL=false";
    private String user = "root";
    private String password = "password";
    @Test
    public void Test(){
        Connection conn = null;
        PreparedStatement pstm =null;
        ResultSet rt = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, password);
            String sql = "INSERT INTO user(id,username,password,email,phone,question,answer,role,create_time,update_time) VALUES(?,?,?,?,?,?,?,?,?,?)";
            pstm = conn.prepareStatement(sql);
            Long startTime = System.currentTimeMillis();
            Random rand = new Random();
            int a,b,c,d;
            for (int i = 1; i <= 100000; i++) {
                a = rand.nextInt(10);
                b = rand.nextInt(10);
                c = rand.nextInt(10);
                d = rand.nextInt(10);
                pstm.setInt(1, (int)((Math.random()*9+1)*100000000));
                pstm.setString(2,"gene"+i);
                pstm.setString(3, MD5Util.MD5EncodeUtf8("123321"));
                pstm.setString(4,"geneq"+i+"@hotmail.com");
                pstm.setString(5, "188"+a+"88"+b+c+"66"+d);
                pstm.setString(6,"go?"+i);
                pstm.setString(7,"go!"+i);
                pstm.setInt(8,0);
                pstm.setString(9,"2018-08-16 21:04:54");
                pstm.setString(10,"2018-08-16 21:04:54");
                pstm.addBatch();
            }
            pstm.executeBatch();
            Long endTime = System.currentTimeMillis();
            System.out.println("OK,用时：" + (endTime - startTime));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }finally{
            if(pstm!=null){
                try {
                    pstm.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
            if(conn!=null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
