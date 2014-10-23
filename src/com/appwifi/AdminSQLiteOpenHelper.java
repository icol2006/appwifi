package com.appwifi;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {

	SQLiteDatabase bd;
	
    public AdminSQLiteOpenHelper(Context context, String nombre, CursorFactory factory, int version) {
        super(context, nombre, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table redes(mac integer text key, nombre text, senal text, seguridad text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAnte, int versionNue) {
        db.execSQL("drop table if exists redes");
        db.execSQL("create table redes(mac integer text key, nombre text, senal text, seguridad text)");       
    }    
    
    public void insertarRed(String mac, String nombre, String senal, String seguridad){ 
    	boolean resultado=false;
     	
     	Cursor valor=bd.rawQuery("select mac,nombre,senal,seguridad from redes where nombre="+mac, null);
         
     	if (valor.moveToFirst())
         {
     		resultado=true;

         }
     	
     	if(resultado==false)
     		bd.execSQL("insert into redes (mac,nombre,senal,seguridad) values('"+mac+"','"+nombre+"','"+senal+"','"+seguridad+"')");
     	
     }
    
    
    public ArrayList<String[]> consultarBaseRespaldadas(){
    	Cursor fila=null;
    	String sqlTexto="";
    	String[] red;
    	ArrayList<String[]> listadoRedes=new ArrayList<String[]>();
    	
    	sqlTexto="select mac,nombre,senal,seguridad from redes";
    	
    	
    		fila=bd.rawQuery(sqlTexto,null);
          
          if (fila.moveToNext())
          {
          	do{     
          		red=new String[4];
          		
          		red[0]=fila.getString(0);
          		red[1]=fila.getString(1);
          		red[2]=fila.getString(2);
          		red[3]=fila.getString(3);
          		listadoRedes.add(red);
          	
          	}while(fila.moveToNext());                
      
	     	}	               
    	
    	return listadoRedes;
    	
    }

 
}
    

