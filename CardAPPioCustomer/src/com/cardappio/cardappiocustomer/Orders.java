package com.cardappio.cardappiocustomer;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import com.cardappio.cardappiocustomer.pojo.Bill;
import com.cardappio.cardappiocustomer.pojo.Order;
import com.cardappio.cardappiocustomer.pojo.Product;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.os.Build;

public class Orders extends ActionBarActivity {

	private String METHOD_NAME = "getBillOrders"; // our webservice method name
	private String NAMESPACE = "http://cardAPPio"; // Here package name in webservice with reverse order.
	private String SOAP_ACTION = NAMESPACE + METHOD_NAME; // NAMESPACE + method name
	private static final String URL = Global.axis2host+"/services/CardAPPio?wsdl"; // you must use ipaddress here, don�t use Hostname or localhost
	private ArrayList<Order> orders;
	private ArrayAdapter<Order> adapter;
	private Bill active_bill;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_orders);
		active_bill = ((CardAPPioCustomer) this.getApplication()).getActive_bill();
		orders = new ArrayList<Order>();
		adapter = new ArrayAdapter<Order>(this, android.R.layout.simple_list_item_1, orders);
        ListView lView = (ListView) findViewById(R.id.listViewOrders);
        lView.setAdapter(adapter);
        final Handler handler = new Handler();
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {       
             @Override
             public void run() {
               handler.post(new Runnable() {
                  public void run() {  
                     new GetBillOrders().execute();
                  }
                });
              }
        };
        timer.schedule(task, 0, 1000); //it executes this every 1000m

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	public class GetBillOrders extends AsyncTask<Void, Void, ArrayList<Order>> {

	    ProgressDialog progress;
	        String response = "";

	      public void onPreExecute() {
	    	  orders.clear();
	        super.onPreExecute();     
	      }

	    @Override
	    protected ArrayList<Order> doInBackground(Void... arg0) {
	    	 try{
				   	SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
					request.addProperty("bill_id", active_bill.getBill_id()); 
					SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
					envelope.dotNet = true;
					envelope.setOutputSoapObject(request);
					HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
					androidHttpTransport.call(SOAP_ACTION,envelope);
					SoapObject result = new SoapObject("http://cardappiocustomer.cardappio.com","Response");
					result = (SoapObject) envelope.getResponse();
				Log.w("Msg",result.toString());
					if("OK".equals(result.getPropertySafely("status").toString())){
						Log.w("Msg","foi");
						SoapObject data = (SoapObject) result.getPropertySafely("data");

						for(int i=0; i <data.getPropertyCount(); i++){
							SoapObject pii = (SoapObject)data.getProperty(i);
							Order order = new Order();
							Product prod = new Product();
							Bill bill =new Bill();
				            order.setProd_id(Integer.parseInt(pii.getProperty("prod_id").toString()));
				            prod.setProd_name(((SoapObject)pii.getPropertySafely("product")).getPropertySafely("prod_name").toString());
				            bill.setBill_id(Integer.parseInt(pii.getProperty("bill_id").toString()));
							bill.setBill_table(((SoapObject)pii.getPropertySafely("bill")).getProperty("bill_table").toString());
							bill.setBill_status(Integer.parseInt(((SoapObject)pii.getPropertySafely("bill")).getProperty("bill_status").toString()));
				            order.setOrd_id(Integer.parseInt(pii.getProperty("ord_id").toString()));
				            order.setOrd_quantity(Integer.parseInt(pii.getProperty("ord_quantity").toString()));
				            order.setOrd_unity_price(Float.parseFloat(pii.getProperty("ord_unity_price").toString()));
				            order.setBill(bill);
				            order.setProduct(prod);
				            orders.add(order);
							//Log.w("Msg",pii.toString());
						}
						return orders;
					}
					} catch (Exception E) {
					E.printStackTrace();
					//((TextView) findViewById (R.id.txtAddition)).setText(�ERROR:�    + E.getClass().getName() + �:� + E.getMessage());
					}


	        return orders;

	    }
	    @Override
	    public void onPostExecute(ArrayList<Order> res) {
	    	adapter.notifyDataSetChanged();

	    }
	 }



}
