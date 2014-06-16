package com.cardappio.cardappiocustomer;

import java.util.ArrayList;

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
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class ProductActivity extends ActionBarActivity {
	private String METHOD_NAME = "getProduct"; // our webservice method name
	private String METHOD_NAME2 = "placeAnOrder";
	private String NAMESPACE = "http://cardAPPio"; // Here package name in webservice with reverse order.
	private String SOAP_ACTION = NAMESPACE + METHOD_NAME; // NAMESPACE + method name
	private String SOAP_ACTION2 = NAMESPACE + METHOD_NAME2;
	private static final String URL = Global.axis2host+"/services/CardAPPio?wsdl"; // you must use ipaddress here, don’t use Hostname or localhost
	private int prod_id;
	private Bill active_bill;
	private Product product;
	private TextView titleTV;
	private TextView desTV;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_product);
		Intent intent = getIntent();
		product = new Product();
		prod_id = intent.getIntExtra(ProductsListActivity.PROD_ID_MESSAGE,-1);
		titleTV = (TextView) findViewById(R.id.product_title);
		desTV = (TextView) findViewById(R.id.product_des);
		active_bill = ((CardAPPioCustomer) this.getApplication()).getActive_bill();
		new GetProduct().execute();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		switch(item.getItemId()){
		case R.id.orders_action:
			Intent intent = new Intent(this, Orders.class);
			startActivity(intent);
		
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	public void place_order(View view){
		new PlaceAnOrder().execute();
	}
	public class GetProduct extends AsyncTask<Void, Void, Product> {

	    ProgressDialog progress;
	        String response = "";

	      public void onPreExecute() {

	        super.onPreExecute();     
	      }

	    @Override
	    protected Product doInBackground(Void... arg0) {
	    	 try{
				   	SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
				   	request.addProperty("prod_id", prod_id); 
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
							product.setProd_id(Integer.parseInt(data.getProperty("prod_id").toString()));
							product.setProd_name(data.getProperty("prod_name").toString());
							product.setProd_price(Float.parseFloat(data.getProperty("prod_price").toString()));
							product.setProd_description(data.getProperty("prod_description").toString());
						
						return product;
					}
					//Toast.makeText(this, "Toast text to show2", Toast.LENGTH_SHORT).show();
					//((TextView) findViewById (R.id.txtAddition)).setText(“Addition : “+result.toString());
					} catch (Exception E) {
					E.printStackTrace();
					//((TextView) findViewById (R.id.txtAddition)).setText(“ERROR:”    + E.getClass().getName() + “:” + E.getMessage());
					}


	        return product;

	    }
	    @Override
	    public void onPostExecute(Product res) {
	    	Log.w("Msg",res.toString()); 
	    	titleTV.setText(res.toString());
	    	desTV.setText(res.getProd_description());
	    }
	 }
	public class PlaceAnOrder extends AsyncTask<Void, Void, Order> {

	    ProgressDialog progress;
	        String response = "";

	      public void onPreExecute() {

	        super.onPreExecute();     
	      }

	    @Override
	    protected Order doInBackground(Void... arg0) {
	    	 try{
				   	SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME2);
				   	request.addProperty("bill_id",  active_bill.getBill_id());
				   	request.addProperty("prod_id", prod_id);
				   	Log.w("Msg",String.valueOf(prod_id));
				   	Log.w("Msg",String.valueOf(active_bill.getBill_id()));
					SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
					envelope.dotNet = true;
					envelope.setOutputSoapObject(request);
					HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
					androidHttpTransport.call(SOAP_ACTION2,envelope);
					SoapObject result = new SoapObject("http://cardappiocustomer.cardappio.com","Response");
					result = (SoapObject) envelope.getResponse();
					Log.w("Msg",result.toString());
					if("OK".equals(result.getPropertySafely("status").toString())){
						Log.w("Msg","foi");
						SoapObject data = (SoapObject) result.getPropertySafely("data");
							Order order = new Order();
							order.setOrd_id(Integer.parseInt(data.getPropertySafely("ord_id").toString()));
							order.setProd_id(Integer.parseInt(data.getPropertySafely("prod_id").toString()));
							order.setBill_id(Integer.parseInt(data.getPropertySafely("bill_id").toString()));
							return order;
					}
					} catch (Exception E) {
					E.printStackTrace();
					//((TextView) findViewById (R.id.txtAddition)).setText(“ERROR:”    + E.getClass().getName() + “:” + E.getMessage());
					}


	        return new Order();

	    }
	    @Override
	    public void onPostExecute(Order res) {
	    	if(res.getOrd_id() != 0){
	    		Toast.makeText(getBaseContext(), "Pedido realizado.", 1).show();
	    	}else{
	    		Toast.makeText(getBaseContext(), "Erro no pedido.", 5).show();
	    	}
	    }
	 }
}
