/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cardAPPio;
import cardAPPio.POJO.Category;
import cardAPPio.POJO.Response;
import cardAPPio.DAO.CategoryDAO;
import java.util.Arrays;

/**
 *
 * @author Pedro
 */
public class CardAPPio {
    public static Response getCategories(){
        return CategoryDAO.getCategories();
    }
    public static Response getCategory(int cat_id){
        return CategoryDAO.getCategory(cat_id);
    }
}
