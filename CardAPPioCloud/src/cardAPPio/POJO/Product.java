/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cardAPPio.POJO;

/**
 *
 * @author Pedro
 */
public class Product {
    private int prod_id;
    private int cat_id;
    private String prod_name;
    private String prod_description;
    private String prod_image;
    private float prod_price;

    public int getProd_id() {
        return prod_id;
    }

    public void setProd_id(int prod_id) {
        this.prod_id = prod_id;
    }

    public int getCat_id() {
        return cat_id;
    }

    public void setCat_id(int cat_id) {
        this.cat_id = cat_id;
    }

    public String getProd_name() {
        return prod_name;
    }

    public void setProd_name(String prod_name) {
        this.prod_name = prod_name;
    }

    public String getProd_description() {
        return prod_description;
    }

    public void setProd_description(String prod_description) {
        this.prod_description = prod_description;
    }

    public String getProd_image() {
        return prod_image;
    }

    public void setProd_image(String prod_image) {
        this.prod_image = prod_image;
    }

    public float getProd_price() {
        return prod_price;
    }

    public void setProd_price(float prod_price) {
        this.prod_price = prod_price;
    }
    
    
    
}
