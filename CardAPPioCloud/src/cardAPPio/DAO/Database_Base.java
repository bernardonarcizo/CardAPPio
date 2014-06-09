/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cardAPPio.DAO;

import cardAPPio.Global;
import cardAPPio.POJO.Response;
import java.sql.Connection;
import java.sql.SQLException;


/**
 *
 * @author sbvb
 */
public abstract class Database_Base <T>{

    Response<T> ret = new Response() ;
    Connection con;
    java.sql.Statement stmt;

    abstract void evaluate() throws SQLException;

     Response<T> response() {

        ret.setStatus(Global.NOT_OK);

        try {

            con = Global.connectDB();
            stmt = con.createStatement();

            evaluate();

        } catch (SQLException e) {
            ret.setStatus(Global.NOT_OK + "SQLException");
             e.printStackTrace();
        } catch (ClassNotFoundException e) {
            ret.setStatus(Global.NOT_OK + "ClassNotFoundException");
             e.printStackTrace();
        } catch (Exception e) {
            ret.setStatus(Global.NOT_OK + "Unhandled Exception");
             e.printStackTrace();
             
        }
        return ret;
    }
}
