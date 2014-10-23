package com.appwifi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class CopyOfregistrarWifi extends Activity {

	TextView text;
	int num1 = 1;
	int num2 = 0;
	String resultado = "";
	BaseDAO db = new BaseDAO(this);
   	String[] red=new String[4];
	ArrayList<String[]> listadoRedes=new ArrayList<String[]>();

	final Handler mHandler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registrar_wifi);
		int dbm = 0;
		int strengthInPercentage = 0;
		int cont = 0;


		text = (TextView) findViewById(R.id.textView1);
		text.setText("Loading!");
		try {

			/*
			 * WifiManager wifiManager = (WifiManager)
			 * getSystemService(Context.WIFI_SERVICE); List<ScanResult> apList =
			 * wifiManager.getScanResults(); for (ScanResult lista : apList)
			 * Toast.makeText( getApplicationContext(), "SSID:" + lista.SSID +
			 * " Señal" + lista.level, Toast.LENGTH_LONG) .show();
			 */

			Runnable runnable = new Runnable() {
				@Override
				public void run() {
					{
						WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
						List<ScanResult> apList = wifiManager.getScanResults();
						resultado = "";
						db.open();
						for (ScanResult lista : apList) {
							resultado += "****SSID:" + lista.SSID + " Señal"
									+ lista.level + " MAC " + lista.BSSID
									+ " Seguridad " + lista.capabilities + "\n";
							
							/*db.insertarRed(lista.BSSID, lista.SSID, lista.level
									+ "", lista.capabilities);*/
							

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
	


}
