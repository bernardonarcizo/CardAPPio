package com.cardappio.cardappiocustomer;

import java.util.ArrayList;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import com.cardappio.cardappiocustomer.ProductActivity.GetProduct;
import com.cardappio.cardappiocustomer.pojo.Bill;
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
import android.widget.EditText;
import android.widget.Toast;
import android.os.Build;
import android.provider.Settings.Secure;

public class MainActivity extends ActionBarActivity {
	private String METHOD_NAME = "openBill"; // our webservice method name
	private String NAMESPACE = "http://cardAPPio"; // Here package name in webservice with reverse order.
	private String SOAP_ACTION = NAMESPACE + METHOD_NAME; // NAMESPACE + method name
	private static final String URL = Global.axis2host+"/services/CardAPPio?wsdl"; // you must use ipaddress here, don’t use Hostname or localhost
	private String n_table;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

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
	
	public void checkin(View view) {
	    //Intent intent = new Intent(this, DisplayMessageActivity.class);
	    EditText editText = (EditText) findViewById(R.id.table_n);
	    n_table = editText.getText().toString();
	    //intent.putExtra(EXTRA_MESSAGE, message);
	    //startActivity(intent);
		String deviceId = Secure.getString(this.getContentResolver(),
                Secure.ANDROID_ID);
		if(n_table != null && !n_table.isEmpty()){
			new OpenBill().execute(n_table,deviceId);
		}else{
			Toast toast = Toast.makeText(getApplicationContext(),"Escolha uma Mesa", 5);
        	toast.show();
		}
	}
	public class OpenBill extends AsyncTask<String, Void, Bill> {

	    ProgressDialog progress;
	        String response = "";

	      public void onPreExecute() {

	        super.onPreExecute();     
	      }

	    @Override
	    protected Bill doInBackground(String... arg) {
	    	 try{
	    		 	
	    		 	SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
				   	request.addProperty("bill_table", arg[0]);
				   	request.addProperty("bill_device_id", arg[1]); 
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
							Bill bill = new Bill();
							bill.setBill_id(Integer.parseInt(data.getProperty("bill_id").toString()));
							bill.setBill_table(data.getProperty("bill_table").toString());
							bill.setBill_status(Integer.parseInt(data.getProperty("bill_status").toString()));
							
							return bill;
					}		
					//Toast.makeText(this, "Toast text to show2", Toast.LENGTH_SHORT).show();
				} catch (Exception E) {
				E.printStackTrace();
				//((TextView) findViewById (R.id.txtAddition)).setText(“ERROR:”    + E.getClass().getName() + “:” + E.getMessage());
				}


	        return new Bill();

	    }
	    @Override
	    public void onPostExecute(Bill res) {
	    	if(!res.getBill_table().equals(n_table)){
				Toast.makeText(getBaseContext(), "Você já tem uma conta aberta na mesa " + res.getBill_table(), 5).show();
			}
	    	((CardAPPioCustomer) getApplication()).setActive_bill(res);
	    	Intent intent = new Intent(getBaseContext(), CategoriesActivity.class);
			startActivity(intent);
	    }
	}
}
	

