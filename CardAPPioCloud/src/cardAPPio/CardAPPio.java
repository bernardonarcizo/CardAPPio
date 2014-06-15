/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cardAPPio;
import cardAPPio.DAO.BillDAO;
import cardAPPio.POJO.Response;
import cardAPPio.DAO.CategoryDAO;
import cardAPPio.DAO.ProductDAO;
import cardAPPio.POJO.Bill;

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
    public static Response getBill(int bill_id){
        return BillDAO.getBill(bill_id);
    }
    public static Response openBill(String bill_table, String bill_device_id){
        return BillDAO.openBill(bill_table, bill_device_id);
    }
    public static void main(String [ ] args){
        Bill bill =(Bill) BillDAO.chBillStatus(3, "4").getData();
        System.out.println(bill.getBill_status());
    }
}
