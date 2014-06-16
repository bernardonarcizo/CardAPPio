/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cardAPPio.DAO;

import cardAPPio.POJO.Order;
import cardAPPio.POJO.Response;
import cardAPPio.Global;
import cardAPPio.POJO.Bill;
import cardAPPio.POJO.Product;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

/**
 *
 * @author Pedro
 */
public class OrderDAO {
    public static Response<Order> placeAnOrder(final int bill_id, final int prod_id ){
        Database_Base<Order> dbb = new Database_Base(){
            @Override
            void evaluate() throws SQLException{
                //check if the bill is authorized( bill_status == 2 ) to place orders
                ResultSet rs = stmt.executeQuery("SELECT * FROM bill WHERE bill_id = " + bill_id + ";");
                if(rs.next()){
                    //System.out.println(rs.getInt("bill_status"));
                    if(rs.getInt("bill_status") == 2 ){
                       
                        //get Bill
                        Bill bill = new Bill();
                        bill.setBill_id(rs.getInt("bill_id"));
                        bill.setBill_table(rs.getString("bill_table"));
                        //bill.setBill_open_time(rs.getTimestamp("bill_open_time"));
                        //bill.setBill_close_time(rs.getTimestamp("bill_close_time"));
                        bill.setBill_status(rs.getInt("bill_status"));
                        //bill.setBill_payment_method(rs.getString("bill_payment_method"));
                        
                        //get Product
                        rs = stmt.executeQuery("SELECT * FROM product WHERE prod_id = " + prod_id + ";");
                        rs.next();
                        Product prod = new Product();
                        prod.setProd_id(rs.getInt("prod_id"));
                        prod.setCat_id(rs.getInt("cat_id"));
                        prod.setProd_name(rs.getString("prod_name"));
                        //prod.setProd_description(rs.getString("prod_description"));
                        //prod.setProd_image(rs.getString("prod_image"));
                        prod.setProd_price(rs.getFloat("prod_price"));
                        
                        //insert order
                         System.out.println("inserindo");
                        rs = stmt.executeQuery("INSERT INTO orders (bill_id, prod_id, ord_unity_price, ord_quantity, ord_status, ord_time)"
                                + " VALUES (" + bill_id +", "+ prod_id + ", "+prod.getProd_price()+", 1 , 1, CURRENT_TIMESTAMP) RETURNING *");
                        rs.next();
                        
                        Order ord = new Order();
                        ord.setOrd_id(rs.getInt("ord_id"));
                        ord.setBill_id(rs.getInt("bill_id"));
                        ord.setProd_id(rs.getInt("prod_id"));
                        ord.setOrd_unity_price(rs.getFloat("ord_unity_price"));
                        ord.setOrd_time(rs.getTimestamp("ord_time"));
                        ord.setBill(bill);
                        ord.setProduct(prod);
                        ret.setData(ord);
                        ret.setStatus(Global.OK);
                        
                        
                        
                    }else{
                        ret.setStatus(Global.OK);
                    }
                }
                    
            }
        };
        return dbb.response();
    }
    public static Response<Order[]> getOpenOrders(){
        Database_Base<Order[]> dbb = new Database_Base(){
            @Override
            void evaluate() throws SQLException{
                ResultSet rs = stmt.executeQuery("SELECT count(*) " +
                                                 "from orders " +
                                                 "inner join product on orders.prod_id = product.prod_id " +
                                                 "inner join bill on orders.bill_id = bill.bill_id " +
                                                 "WHERE bill_status != 4;");
               rs.next();
               int nLines = rs.getInt(1);
              Order[] data = new Order[nLines];
              rs = stmt.executeQuery("SELECT prod_name, orders.prod_id, bill_table, orders.bill_id, ord_id, ord_unity_price, ord_quantity " +
                                    "from orders " +
                                    "inner join product on orders.prod_id = product.prod_id " +
                                    "inner join bill on orders.bill_id = bill.bill_id " +
                                    "WHERE bill_status != 4;");
               rs.next();
               for (int i = 0; i < nLines; i++) {
                   Product prod = new Product();
                   prod.setProd_id(rs.getInt("prod_id"));
                   prod.setProd_name(rs.getString("prod_name"));
                   
                   Bill bill = new Bill();
                   bill.setBill_id(rs.getInt("bill_id"));
                   bill.setBill_table(rs.getString("bill_table"));
                   
                   Order ord = new Order();
                   ord.setOrd_id(rs.getInt("ord_id"));
                   ord.setBill_id(rs.getInt("bill_id"));
                   ord.setProd_id(rs.getInt("prod_id"));
                   ord.setOrd_quantity(rs.getInt("ord_quantity"));
                   ord.setOrd_unity_price(rs.getFloat("ord_unity_price"));
                   ord.setBill(bill);
                   ord.setProduct(prod);
                   data[i] = ord;
                   rs.next();
                  
               }
                   ret.setData(data);
                   ret.setStatus(Global.OK);
            }
         };
        return dbb.response();
    }
}
