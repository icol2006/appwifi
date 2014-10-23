package com.appwifi;

import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.database.SQLException;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class listadoRedes extends Activity {
	
	BaseDAO db = new BaseDAO(this);
   	String[] red=new String[4];
	ArrayList<String[]> listadoRedes=new ArrayList<String[]>();
	String resultado = "";
	TextView text;
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listadoredes);
        text = (TextView) findViewById(R.id.textView1);
        db.open();
        listadoRedes=db.consultarRedes();
		
		for(int i=0; i<listadoRedes.size();i++){
			red=new String[4];
			red=listadoRedes.get(i);
			resultado+=red[1]+" * " +red[0]+" * "+red[2]+" * "+red[3]+ " * "+red[4]+"\n"+ "\n";
		}
		
		db.close();
		
		text.setText(resultado);

    } 

}
