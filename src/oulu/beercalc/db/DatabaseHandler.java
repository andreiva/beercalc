package oulu.beercalc.db;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "beercalc.db";
	private static final int DATABASE_VERSION = 2;
	
	public static final String TABLE_CATEGORY = "category";
	public static final String CATEGORY_ID = "id";
	public static final String CATEGORY_NAME = "name";
	
	public static final String TABLE_ITEM = "item";
	public static final String ITEM_ID = "id";
	public static final String ITEM_NAME = "name";
	public static final String ITEM_COST = "cost";
	public static final String ITEM_TIME = "time";
	public static final String ITEM_DATE = "date";
	
	private static final String DATABASE_CREATE_CATEGORY = "create table "+TABLE_CATEGORY + 
													" ( " + CATEGORY_ID	+" integer primary key autoincrement, "+
													CATEGORY_NAME + " text not null);";
	
								
	private static final String DATABASE_CREATE_ITEM = "create table "+TABLE_ITEM + 
													" ( "+ ITEM_ID +" integer primary key autoincrement, "+
														ITEM_NAME + " text not null, "+
														ITEM_COST + " text not null, "+
														ITEM_TIME + " text not null, "+
														ITEM_DATE + " text not null);";

	
	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		
		
		
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		
		// if database not exist -> create
		if( checkDataBase() == false )
		{
			database.execSQL(DATABASE_CREATE_CATEGORY);
			database.execSQL(DATABASE_CREATE_ITEM);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.d(DatabaseHandler.class.getName(), 
				"Upgrading database from version " + oldVersion + " to "
				+ newVersion + ", which will destroy all old data");
		
		System.out.println("Hello");
		
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORY);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEM);
		onCreate(db);
	}
	
    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     * @return true if it exists, false if it doesn't
     */
	private boolean checkDataBase() {

		SQLiteDatabase checkDB = null;

		try {
			checkDB = SQLiteDatabase.openOrCreateDatabase(DATABASE_NAME, null);
		} catch (SQLiteException e) {

		}
		if (checkDB != null) {
			checkDB.close();
		}
		return checkDB != null ? true : false;
	}
}

