/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cardAPPio.DAO;

import cardAPPio.Global;
import static cardAPPio.Global.log;
import cardAPPio.POJO.Category;
import cardAPPio.POJO.Product;
import cardAPPio.POJO.Response;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

/**
 *
 * @author Pedro
 */
public class ProductDAO {
    
    public static Response getCatProducts(final int cat_id){
        Database_Base<Product[]> dbb = new Database_Base(){
            @Override
            void evaluate() throws SQLException{
               ResultSet rs = stmt.executeQuery("SELECT count(*) FROM product WHERE cat_id = " + cat_id + ";");
               rs.next();
               int nLines = rs.getInt(1);
               log.log(Level.INFO, "=== getProducts lines in table=" + nLines);
               Product[] data = new Product[nLines];
               rs = stmt.executeQuery("SELECT * FROM product WHERE cat_id = " + cat_id + ";");
               rs.next();
               int rowCount = 0;
               // for each line of ResultSet
               for (int i = 0; i < nLines; i++) {
                   Product prod = new Product();
                   prod.setProd_id(rs.getInt("prod_id"));
                   prod.setCat_id(rs.getInt("cat_id"));
                   prod.setProd_name(rs.getString("prod_name"));
                   prod.setProd_description(rs.getString("prod_description"));
                   prod.setProd_image(rs.getString("prod_image"));
                   prod.setProd_price(rs.getFloat("prod_price"));
                   rs.next();
                   data[i] = prod;
                   System.out.println(data);
                   log.log(Level.INFO, "=== getProducts data=", data);
               }
               ret.setStatus(Global.OK);
               ret.setData(data);    
            }
        };
                return dbb.response();
    }
    
    public static Response getProduct(final int prod_id){
        Database_Base<Product> dbb = new Database_Base(){
            @Override
            void evaluate() throws SQLException{
                ResultSet rs = stmt.executeQuery("SELECT * FROM product WHERE prod_id=" + prod_id + ";");
                rs.next();
                Product prod = new Product();
                prod.setProd_id(rs.getInt("prod_id"));
                prod.setCat_id(rs.getInt("cat_id"));
                prod.setProd_name(rs.getString("prod_name"));
                prod.setProd_description(rs.getString("prod_description"));
                prod.setProd_image(rs.getString("prod_image"));
                prod.setProd_price(rs.getFloat("prod_price"));
                ret.setData(prod);
                ret.setStatus(Global.OK);
            } 
        };
        return dbb.response();
    }
    
}
