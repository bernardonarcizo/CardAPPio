/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cardAPPio.POJO;

/**
 *
 * @author pedromrnd
 */
public class Response <T> {
    private String status;
    private T data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
    
}
