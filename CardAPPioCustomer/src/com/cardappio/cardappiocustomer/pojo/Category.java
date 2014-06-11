package com.cardappio.cardappiocustomer.pojo;


/**
 *
 * @author pedromrnd
 */
public class Category {
    private int cat_id;
    private String cat_name;

    /**
     * @return the cat_id
     */
    public int getCat_id() {
        return cat_id;
    }

    /**
     * @param cat_id the cat_id to set
     */
    public void setCat_id(int cat_id) {
        this.cat_id = cat_id;
    }

    /**
     * @return the cat_name
     */
    public String getCat_name() {
        return cat_name;
    }

    /**
     * @param cat_name the cat_name to set
     */
    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }
    @Override
    public String toString() {
        return this.cat_name;
    }
}
