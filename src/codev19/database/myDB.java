package codev19.database;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

// DB 관리
public class myDB {
    private Connection con = null;
    private Statement st = null;
    private ResultSet rs = null;
    public myDB(){}

    // 소멸자
    public void finalize(){
        try {
            // connection 멈추기
            con.close();
        } catch (SQLException sqlEX){
            // 에러나면 소멸
            Logger lgr = Logger.getLogger(myDB.class.getName());
            lgr.log(Level.SEVERE, sqlEX.getMessage(), sqlEX);
        }
    }

    // DB 연결하기
    public boolean connectDB(String server,String dbName, String uid, String password) {
        try {
            // DB 연결
            con = DriverManager.getConnection(server + "/" + dbName, uid, password);
        } catch (SQLException sqlEX) {
            // 에러나면 false 반환
            Logger lgr = Logger.getLogger(myDB.class.getName());
            lgr.log(Level.SEVERE, sqlEX.getMessage(), sqlEX);
            return false;
        }
        // 성공하면 true 반환
        return true;
    }

    // Query 실행하기
    public boolean execQuery(String sql){
        try{
            st = con.createStatement();
            rs = st.executeQuery(sql);
        } catch (SQLException sqlEX){
            Logger lgr = Logger.getLogger(myDB.class.getName());
            lgr.log(Level.SEVERE, sqlEX.getMessage(), sqlEX);
            return false;
        }
        return true;
    }

    // 다음 Row 로 이동
    public boolean next(){
        boolean retval;
        try {
            // 성공하면 true 반환
            retval  = rs.next();
        }
        catch (SQLException sqlEX){
            // 에러 나면 로그 기록
            Logger lgr = Logger.getLogger(myDB.class.getName());
            lgr.log(Level.SEVERE, sqlEX.getMessage(), sqlEX);
            // 실패하면 false 반환
            retval = false;
        }
        return retval;
    }

    public String getResult(int col){
        try{
            // 결과를 가져온다
            return rs.getString(col);
        } catch (SQLException sqlEX){
            // 에러 나면 로그 기록
            Logger lgr = Logger.getLogger(myDB.class.getName());
            lgr.log(Level.SEVERE, sqlEX.getMessage(), sqlEX);
        }
        // 에러날 경우 공백을 반환
        return "";
    }

}
