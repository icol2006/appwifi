package com.appwifi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Main extends Activity {

	BaseDAO db = new BaseDAO(this);
   	String[] red=new String[4];
	ArrayList<String[]> listadoRedes=new ArrayList<String[]>();


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.inicio);

		try {
			db.createDataBase();
		} catch (IOException ioe) {
			throw new Error("Unable to create database");
		}

		
	}
	
    public void iniciarRegistroWifi(View view) {
    	Intent intent = new Intent(this, registrarWifi.class);
    	startActivity(intent);
     }
    
    public void verListadoRedes(View view) {
    	Intent intent = new Intent(this, listadoRedes.class);
    	startActivity(intent);
     }
    
    public void TomarPuntoGPS(View view) {
    	Intent intent = new Intent(this, ShowLocationActivity.class);
    	startActivity(intent);
     }
	
   public void guardarBase(View view) {
    	String x;
        try
        {
          File localFile1 = Environment.getExternalStorageDirectory();
          File localFile2 = new File(Environment.getDataDirectory(), "/data/"+getPackageName()+"/databases/base.sqlite");
          File localFile3 = new File(localFile1.getAbsoluteFile(), "base.sqlite");
          FileChannel localFileChannel1 = new FileInputStream(localFile2).getChannel();
          FileChannel localFileChannel2 = new FileOutputStream(localFile3).getChannel();
          localFileChannel2.transferFrom(localFileChannel1, 0L, localFileChannel1.size());
          localFileChannel1.close();
          localFileChannel2.close();
          return;
        }
        catch (Exception localException){
        	 x=localException.toString();
        }
     }
    
    

}
