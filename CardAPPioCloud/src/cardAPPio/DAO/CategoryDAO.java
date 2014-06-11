/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cardAPPio.DAO;

import cardAPPio.POJO.Category;
import cardAPPio.Global;
import cardAPPio.POJO.Response;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import static cardAPPio.Global.log;

/**
 *
 * @author Pedro
 */
public class CategoryDAO {
    
    public static Response getCategories(){
        
        Database_Base<Category[]> dbb = new Database_Base(){
            @Override
            void evaluate() throws SQLException {
               ResultSet rs = stmt.executeQuery("select count(*) from category");
               rs.next();
               int nLines = rs.getInt(1);
               log.log(Level.INFO, "=== getCategories lines in table=" + nLines);
               Category[] data = new Category[nLines];
               rs = stmt.executeQuery("select cat_id, cat_name from category");
               rs.next();
               int rowCount = 0;
               // for each line of ResultSet
               for (int i = 0; i < nLines; i++) {
                   Category cat = new Category();
                   cat.setCat_id(Integer.parseInt(rs.getString("cat_id")));
                   cat.setCat_name(rs.getString("cat_name"));
                   rs.next();
                   data[i] = cat;
                   System.out.println(data);
                   log.log(Level.INFO, "=== getCategories data=", data);
               }
               ret.setStatus(Global.OK);
               ret.setData(data);    
           }
        };
        return dbb.response();
       }
    
    public static Response getCategory(final int cat_id){
        Database_Base<Category> dbb = new Database_Base(){
            @Override
            void evaluate() throws SQLException{
                ResultSet rs = stmt.executeQuery("SELECT cat_id, cat_name FROM category WHERE cat_id="+cat_id+";");
                rs.next();
                Category cat = new Category();
                cat.setCat_id(Integer.parseInt(rs.getString("cat_id")));
                cat.setCat_name(rs.getString("cat_name"));
                ret.setData(cat);
                ret.setStatus(Global.OK);
            } 
        };
        return dbb.response();
    }
}
