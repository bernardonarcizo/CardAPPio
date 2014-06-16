package com.cardappio.cardappiocustomer;

import java.util.ArrayList;
import java.util.List;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import com.cardappio.cardappiocustomer.pojo.Category;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class CategoriesActivity extends ActionBarActivity {
	private String METHOD_NAME = "getCategories"; // our webservice method name
	private String NAMESPACE = "http://cardAPPio"; // Here package name in webservice with reverse order.
	private String SOAP_ACTION = NAMESPACE + METHOD_NAME; // NAMESPACE + method name
	private static final String URL = Global.axis2host+"/services/CardAPPio?wsdl"; // you must use ipaddress here, don’t use Hostname or localhost
	private ArrayList<Category> categories;
	private ArrayAdapter<Category> adapter;
	public final static String CAT_ID_MESSAGE = "com.cardappio.cardappiocustomer.CAT_ID_MESSAGE";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		

		
		categories = new ArrayList<Category>();
		setContentView(R.layout.activity_categories);
		adapter = new ArrayAdapter<Category>(this, android.R.layout.simple_list_item_1, categories);
        ListView lView = (ListView) findViewById(R.id.listViewCategories);
        lView.setAdapter(adapter);
        lView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
            	final Category cat = (Category) parent.getItemAtPosition(position);
            			Toast toast = Toast.makeText(getApplicationContext(),"Ver produto "+String.valueOf(cat.getCat_id()), 5);
                    	toast.show();
                    	Intent intent = new Intent(view.getContext(), ProductsListActivity.class);
                    	intent.putExtra(CAT_ID_MESSAGE, cat.getCat_id());
            			startActivity(intent);
                    	//adapter.notifyDataSetChanged();
  
            }
        });
		new GetCategories().execute();
		
       
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
	
	public class GetCategories extends AsyncTask<Void, Void, ArrayList<Category>> {

	    ProgressDialog progress;
	        String response = "";

	      public void onPreExecute() {

	        super.onPreExecute();     
	      }

	    @Override
	    protected ArrayList<Category> doInBackground(Void... arg0) {
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
						//categories = new Category[data.getPropertyCount()];
						for(int i=0; i <data.getPropertyCount(); i++){
							SoapObject pii = (SoapObject)data.getProperty(i);
							Category category = new Category();
				            category.setCat_id(Integer.parseInt(pii.getProperty("cat_id").toString()));
				            category.setCat_name(pii.getProperty("cat_name").toString());
				            categories.add(category);
							Log.w("Msg",pii.toString());
						}
						return categories;
					}
					//Toast.makeText(this, "Toast text to show2", Toast.LENGTH_SHORT).show();
					//((TextView) findViewById (R.id.txtAddition)).setText(“Addition : “+result.toString());
					} catch (Exception E) {
					E.printStackTrace();
					//((TextView) findViewById (R.id.txtAddition)).setText(“ERROR:”    + E.getClass().getName() + “:” + E.getMessage());
					}


	        return categories;

	    }
	    @Override
	    public void onPostExecute(ArrayList<Category> res) {
	    	adapter.notifyDataSetChanged();

	    }
	 }


}


