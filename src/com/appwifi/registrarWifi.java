package com.appwifi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class registrarWifi extends Activity implements LocationListener {

	TextView text;
	int num1 = 1;
	int num2 = 0;
	String resultado = "";
	BaseDAO db = new BaseDAO(this);
   	String[] red=new String[4];
	ArrayList<String[]> listadoRedes=new ArrayList<String[]>();
	Double longitud=0.0;
	Double latitud=0.0;
    LocationManager locationManager;
    String provider;
	

	final Handler mHandler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registrar_wifi);
		int dbm = 0;
		int strengthInPercentage = 0;
		int cont = 0;


		// Get the location manager
	    locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
	    // Define the criteria how to select the locatioin provider -> use
	    // default
	    Criteria criteria = new Criteria();
	    provider = locationManager.getBestProvider(criteria, false);
	    Location location = locationManager.getLastKnownLocation(provider);

	    // Initialize the location fields
	    if (location != null) {
	    //    System.out.println("Provider " + provider + " has been selected.");
	      onLocationChanged(location);
	    }/*else {
	      latituteField.setText("Location not available");
	      longitudeField.setText("Location not available");
	    }*/	    
	    
		
		
		text = (TextView) findViewById(R.id.textView1);
		text.setText("Loading!");
		try {

			/*
			 * WifiManager wifiManager = (WifiManager)
			 * getSystemService(Context.WIFI_SERVICE); List<ScanResult> apList =
			 * wifiManager.getScanResults(); for (ScanResult lista : apList)
			 * Toast.makeText( getApplicationContext(), "SSID:" + lista.SSID +
			 * " Se�al" + lista.level, Toast.LENGTH_LONG) .show();
			 */

			Runnable runnable = new Runnable() {
				@Override
				public void run() {
					{
						String x;
						WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
						List<ScanResult> apList = wifiManager.getScanResults();
						resultado = "";
						db.open();
						for (ScanResult lista : apList) {
							resultado += "****SSID:" + lista.SSID + " Se�al"
									+ lista.level + " MAC " + lista.BSSID
									+ " Seguridad " + lista.capabilities +" "+latitud+",jj"+longitud+ "\n";
					
								db.insertarRed(lista.BSSID, lista.SSID, lista.level
										+ "", lista.capabilities,latitud+","+longitud);

						}
				
						
						/*listadoRedes=db.consultarRedes();
						
						for(int i=0; i<listadoRedes.size();i++){
							red=new String[4];
							red=listadoRedes.get(i);
							resultado+=red[0]+" "+red[1]+" "+red[2]+" "+red[3]+ "\n";
						}*/
						text.setText(String.valueOf(num1) + "\n" + resultado);
						num1++;
						db.close();
						
						
						if (num1 > 0) {
							mHandler.postDelayed(this, 1000);
						}
					}
				}
			};
			mHandler.post(runnable);

		} catch (Exception ex) {
			String xi = ex.toString();
		}

	}
	
	 /* Request updates at startup */
	  @Override
	  protected void onResume() {
	    super.onResume();
	    locationManager.requestLocationUpdates(provider, 400, 1, this);
	  }

	  /* Remove the locationlistener updates when Activity is paused */
	  @Override
	  protected void onPause() {
	    super.onPause();
	    locationManager.removeUpdates(this);
	  }

	  @Override
	  public void onLocationChanged(Location location) {
	    int lat = (int) (location.getLatitude());
	    int lng = (int) (location.getLongitude());
	    
	    latitud=location.getLatitude();
	    longitud=location.getLongitude();
	    
	   /* latituteField.setText(String.valueOf(lat));
	    longitudeField.setText(String.valueOf(lng));*/
	  }

	  @Override
	  public void onStatusChanged(String provider, int status, Bundle extras) {
	    // TODO Auto-generated method stub

	  }
	  
	  @Override
	  public void onProviderEnabled(String provider) {
	    Toast.makeText(this, "Enabled new provider " + provider,
	        Toast.LENGTH_SHORT).show();

	  }

	  @Override
	  public void onProviderDisabled(String provider) {
	    Toast.makeText(this, "Disabled provider " + provider,
	        Toast.LENGTH_SHORT).show();
	  }

}
