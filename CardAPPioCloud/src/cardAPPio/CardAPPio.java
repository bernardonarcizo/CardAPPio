/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cardAPPio;
import cardAPPio.POJO.Response;
import cardAPPio.DAO.CategoryDAO;
import cardAPPio.DAO.ProductDAO;

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
    public static Response getProducts(int cat_id){
        return ProductDAO.getCatProducts(cat_id);
    }
    public static Response getProduct(int prod_id){
        return ProductDAO.getProduct(prod_id);
    }
}
