package com.appwifi;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import android.telephony.TelephonyManager;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.telephony.TelephonyManager;
import android.util.Log;

public class BaseDAO extends SQLiteOpenHelper {

	// The Android's default system path of your application database.

	private static String DB_PATH = "/data/data/com.appwifi/databases/";

	private static String DB_NAME = "base.sqlite";

	private SQLiteDatabase db;

	private final Context myContext;

	/**
	 * Constructor Takes and keeps a reference of the passed context in order to
	 * access to the application assets and resources.
	 * 
	 * @param context
	 */
	public BaseDAO(Context context) {

		super(context, DB_NAME, null, 1);
		this.myContext = context;
	}

	/**
	 * Creates a empty database on the system and rewrites it with your own
	 * database.
	 * */
	public void createDataBase() throws IOException {

		boolean dbExist = checkDataBase();

		if (dbExist) {
			// do nothing - database already exist
		} else {

			// By calling this method and empty database will be created into
			// the default system path
			// of your application so we are gonna be able to overwrite that
			// database with our database.
			this.getReadableDatabase();

			try {

				copyDataBase();

			} catch (IOException e) {

				throw new Error("Error copying database");

			}
		}

	}

	/**
	 * Check if the database already exist to avoid re-copying the file each
	 * time you open the application.
	 * 
	 * @return true if it exists, false if it doesn't
	 */
	private boolean checkDataBase() {

		SQLiteDatabase checkDB = null;

		try {
			String myPath = DB_PATH + DB_NAME;
			checkDB = SQLiteDatabase.openDatabase(myPath, null,
					SQLiteDatabase.OPEN_READONLY);

		} catch (SQLiteException e) {

			// database does't exist yet.

		}

		if (checkDB != null) {

			checkDB.close();

		}

		return checkDB != null ? true : false;
	}

	/**
	 * Copies your database from your local assets-folder to the just created
	 * empty database in the system folder, from where it can be accessed and
	 * handled. This is done by transfering bytestream.
	 * */
	private void copyDataBase() throws IOException {

		// Open your local db as the input stream
		InputStream myInput = myContext.getAssets().open(DB_NAME);

		// Path to the just created empty db
		String outFileName = DB_PATH + DB_NAME;

		// Open the empty db as the output stream
		OutputStream myOutput = new FileOutputStream(outFileName);

		// transfer bytes from the inputfile to the outputfile
		byte[] buffer = new byte[1024];
		int length;
		while ((length = myInput.read(buffer)) > 0) {
			myOutput.write(buffer, 0, length);
		}

		// Close the streams
		myOutput.flush();
		myOutput.close();
		myInput.close();

	}

	public void open() throws SQLException {

		// Open the database
		String myPath = DB_PATH + DB_NAME;
		db = SQLiteDatabase.openDatabase(myPath, null,
				SQLiteDatabase.OPEN_READWRITE);

	}

	@Override
	public synchronized void close() {

		if (db != null)
			db.close();

		super.close();

	}

	@Override
	public void onCreate(SQLiteDatabase db) {

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

	// Add your public helper methods to access and get content from the
	// database.
	// You could return cursors by doing "return myDataBase.query(....)" so it'd
	// be easy
	// to you to create adapters for your views.

	public void insertarRed(String mac, String nombre, String senal,
			String seguridad, String ubicacion) {
		boolean resultado = false;
		int numeroRedes = 0;
		Cursor fila;
		String ex = "";

		try {
		fila = db.rawQuery("select count(*) from redes where mac='" + mac
				+ "' and ubicacion='" + ubicacion + "'", null);
 
		if (fila.moveToFirst()) {
			// resultado=true;
			numeroRedes = fila.getInt(0);

		}
		
		if (numeroRedes < 1) {


				db.execSQL("insert into redes (mac,nombre,senal,seguridad,ubicacion) values('"
						+ mac
						+ "','"
						+ nombre
						+ "','"
						+ senal
						+ "','"
						+ seguridad + "','" + ubicacion + "')");
			} 
		}catch (Exception e) {
			ex = e.toString();
		}

	}
 
	public ArrayList<String[]> consultarRedes() {
		Cursor fila = null;
		String sqlTexto = "";
		String[] red;
		ArrayList<String[]> listadoRedes = new ArrayList<String[]>();

		sqlTexto = "select mac,nombre,senal,seguridad,ubicacion from redes";

		fila = db.rawQuery(sqlTexto, null);

		if (fila.moveToNext()) {
			do {
				red = new String[5];

				red[0] = fila.getString(0);
				red[1] = fila.getString(1);
				red[2] = fila.getString(2);
				red[3] = fila.getString(3);
				red[4] = fila.getString(4);
				listadoRedes.add(red);

			} while (fila.moveToNext());

		}

		return listadoRedes;

	}

}