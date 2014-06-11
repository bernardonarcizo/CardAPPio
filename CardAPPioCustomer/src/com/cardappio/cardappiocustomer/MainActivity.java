package com.cardappio.cardappiocustomer;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import com.cardappio.cardappiocustomer.pojo.Category;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {
	private String METHOD_NAME = "getCategories"; // our webservice method name
	private String NAMESPACE = "http://cardAPPio"; // Here package name in webservice with reverse order.
	private String SOAP_ACTION = NAMESPACE + METHOD_NAME; // NAMESPACE + method name
	private static final String URL = Global.axis2host+"/services/CardAPPio?wsdl"; // you must use ipaddress here, don’t use Hostname or localhost
	private Category[] categories;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		System.out.println("oito");
		new Thread(new Runnable() {
			   public void run() {
				   try{
				   SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
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
						//SoapObject array = (SoapObject) data.getPropertySafely("array");
						categories = new Category[data.getPropertyCount()];
						for(int i=0; i <categories.length; i++){
							SoapObject pii = (SoapObject)data.getProperty(i);
							Category category = new Category();
				            category.setCat_id(Integer.parseInt(pii.getProperty("cat_id").toString()));
				            category.Name = pii.getProperty(1).toString();
				            category.Description = pii.getProperty(2).toString();
				            categories[i] = category;
							Log.w("Msg",pii.toString());
						} 
					}
					//Toast.makeText(this, "Toast text to show2", Toast.LENGTH_SHORT).show();
					//((TextView) findViewById (R.id.txtAddition)).setText(“Addition : “+result.toString());
					} catch (Exception E) {
					E.printStackTrace();
					//((TextView) findViewById (R.id.txtAddition)).setText(“ERROR:”    + E.getClass().getName() + “:” + E.getMessage());
					}
			   }                        
			}).start();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
	    return super.onCreateOptionsMenu(menu);
		//return true;
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


}
