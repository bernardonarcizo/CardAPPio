/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cardAPPio;
import cardAPPio.DAO.BillDAO;
import cardAPPio.POJO.Response;
import cardAPPio.DAO.CategoryDAO;
import cardAPPio.DAO.OrderDAO;
import cardAPPio.DAO.ProductDAO;
import cardAPPio.POJO.Bill;
import cardAPPio.POJO.Order;

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
    public static Response chBillStatus(int bill_id, int bill_status){
       return BillDAO.chBillStatus(bill_id, bill_status);
    }
    public static Response placeAnOrder(int bill_id, int prod_id){
       return OrderDAO.placeAnOrder(bill_id, prod_id);
    }
    public static void main(String [ ] args){
        Order ord =(Order)  placeAnOrder(1,1).getData();
        System.out.println(ord.getOrd_unity_price());
    }
}
