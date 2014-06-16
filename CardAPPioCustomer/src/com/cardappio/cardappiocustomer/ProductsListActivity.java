package com.cardappio.cardappiocustomer;

import java.util.ArrayList;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import com.cardappio.cardappiocustomer.CategoriesActivity.GetCategories;
import com.cardappio.cardappiocustomer.pojo.Category;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.os.Build;

public class ProductsListActivity extends ActionBarActivity {
	private String METHOD_NAME = "getProducts"; // our webservice method name
	private String NAMESPACE = "http://cardAPPio"; // Here package name in webservice with reverse order.
	private String SOAP_ACTION = NAMESPACE + METHOD_NAME; // NAMESPACE + method name
	private static final String URL = Global.axis2host+"/services/CardAPPio?wsdl"; // you must use ipaddress here, don’t use Hostname or localhost
	private int cat_id;
	private ArrayList<Product> products;
	private ArrayAdapter<Product> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_products_list);
		
		Intent intent = getIntent();
		cat_id = intent.getIntExtra(CategoriesActivity.CAT_ID_MESSAGE,-1);
		
		products = new ArrayList<Product>();
		adapter = new ArrayAdapter<Product>(this, android.R.layout.simple_list_item_1, products);
        ListView lView = (ListView) findViewById(R.id.listViewProducts);
        lView.setAdapter(adapter);
        //lView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

           // @Override
            //public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
            	//final Product prod = (Product) parent.getItemAtPosition(position);
            			//Toast toast = Toast.makeText(getApplicationContext(),"Ver produto "+String.valueOf(prod.getProd_id()), 5);
                    	//toast.show();
                    	//Intent intent = new Intent(view.getContext(), ProductsListActivity.class);
                    	//intent.putExtra("cat_id",cat.getCat_id());
            			//startActivity(intent);
                    	//adapter.notifyDataSetChanged();
  
            //}
        //});
		new GetProducts().execute();
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
		//int id = item.getItemId();
		//if (id == R.id.action_settings) {
			//return true;
		//}
		//return super.onOptionsItemSelected(item);
		switch(item.getItemId()){
		case R.id.orders_action:
			Intent intent = new Intent(this, Orders.class);
			startActivity(intent);
		
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	public class GetProducts extends AsyncTask<Void, Void, ArrayList<Product>> {

	    ProgressDialog progress;
	        String response = "";

	      public void onPreExecute() {

	        super.onPreExecute();     
	      }

	    @Override
	    protected ArrayList<Product> doInBackground(Void... arg0) {
	    	 try{
				   	SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
				   	request.addProperty("cat_id", cat_id); 
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
							Product prod = new Product();
				            prod.setProd_id(Integer.parseInt(pii.getProperty("prod_id").toString()));
				            prod.setProd_name(pii.getProperty("prod_name").toString());
				            prod.setProd_price(Float.parseFloat(pii.getProperty("prod_price").toString()));
				            products.add(prod);
							//Log.w("Msg",pii.toString());
						}
						return products;
					}
					//Toast.makeText(this, "Toast text to show2", Toast.LENGTH_SHORT).show();
					//((TextView) findViewById (R.id.txtAddition)).setText(“Addition : “+result.toString());
					} catch (Exception E) {
					E.printStackTrace();
					//((TextView) findViewById (R.id.txtAddition)).setText(“ERROR:”    + E.getClass().getName() + “:” + E.getMessage());
					}


	        return products;

	    }
	    @Override
	    public void onPostExecute(ArrayList<Product> res) {
	    	adapter.notifyDataSetChanged();

	    }
	 }

}
