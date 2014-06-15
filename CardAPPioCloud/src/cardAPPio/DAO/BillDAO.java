/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cardAPPio.DAO;

import cardAPPio.Global;
import cardAPPio.POJO.Bill;
import cardAPPio.POJO.Response;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 *
 * @author Pedro
 */
public class BillDAO {
    
    public static Response getBill(final int bill_id){
        Database_Base<Bill> dbb = new Database_Base(){
            @Override
            void evaluate() throws SQLException{
                ResultSet rs = stmt.executeQuery("SELECT * FROM bill WHERE bill_id = " + bill_id+";");
                rs.next();
                Bill bill = new Bill();
                bill.setBill_id(rs.getInt("bill_id"));
                bill.setBill_table(rs.getString("bill_table"));
                bill.setBill_open_time(rs.getTimestamp("bill_open_time"));
                bill.setBill_close_time(rs.getTimestamp("bill_close_time"));
                bill.setBill_status(rs.getString("bill_status"));
                bill.setBill_payment_method(rs.getString("bill_payment_method"));
                ret.setData(bill);
                ret.setStatus(Global.OK);
            } 
        };
        return dbb.response();
    }
    public static Response chBillStatus(final int bill_id, final String bill_status){
        Database_Base<Bill> dbb = new Database_Base(){
            @Override
            void evaluate() throws SQLException{
                String query = "UPDATE bill SET (bill_status) = ('" + bill_status + "') WHERE bill_id = " + bill_id+ " RETURNING * ;";
                if(bill_status.equals("4")){
                    java.util.Date date = new java.util.Date();
                    Timestamp bill_close_time = new Timestamp(date.getTime());
                    query = "UPDATE bill SET (bill_status, bill_close_time) = ('" + bill_status + "', '" + bill_close_time + "') WHERE bill_id = " + bill_id+ " RETURNING * ;";
                }
                ResultSet rs = stmt.executeQuery(query);
                rs.next();
                Bill bill = new Bill();
                bill.setBill_id(rs.getInt("bill_id"));
                bill.setBill_table(rs.getString("bill_table"));
                bill.setBill_open_time(rs.getTimestamp("bill_open_time"));
                bill.setBill_close_time(rs.getTimestamp("bill_close_time"));
                bill.setBill_status(rs.getString("bill_status"));
                bill.setBill_payment_method(rs.getString("bill_payment_method"));
                ret.setData(bill);
                ret.setStatus(Global.OK);    
                
            }
        };
        return dbb.response();
    }
    
    public static Response openBill(final String bill_table, final String bill_device_id){
        Database_Base<Bill> dbb = new Database_Base(){
            @Override
            void evaluate() throws SQLException{
                System.out.println("SELECT * FROM bill WHERE bill_device_id = '" + bill_device_id + "' AND bill_status != '4';");
                ResultSet rs = stmt.executeQuery("SELECT * FROM bill WHERE bill_device_id = '" + bill_device_id + "' AND bill_status != '4';");
                
                
                // This device already has a open bill
                if(rs.next()){
                    
                }else{  // open a bill
                    String bill_status = "0";
                    java.util.Date date = new java.util.Date();
                    Timestamp bill_open_time = new Timestamp(date.getTime());
                    rs = stmt.executeQuery("INSERT INTO bill(bill_open_time, bill_table, bill_device_id, bill_status)"
                            + "VALUES('"+bill_open_time+"', '"+bill_table+"', '"+bill_device_id+"', '"+bill_status+"') RETURNING *;");

                    rs.next();
                }
                Bill bill = new Bill();
                bill.setBill_id(rs.getInt("bill_id"));
                bill.setBill_table(rs.getString("bill_table"));
                bill.setBill_open_time(rs.getTimestamp("bill_open_time"));
                bill.setBill_close_time(rs.getTimestamp("bill_close_time"));
                bill.setBill_status(rs.getString("bill_status"));
                bill.setBill_payment_method(rs.getString("bill_payment_method"));
                ret.setData(bill);
                ret.setStatus(Global.OK);    
            }
           
        };
        return dbb.response();        
    }
    
    
    
}
