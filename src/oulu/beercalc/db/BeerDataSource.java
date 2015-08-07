package oulu.beercalc.db;

import java.util.ArrayList;
import java.util.List;

import oulu.beercalc.model.Category;
import oulu.beercalc.model.Item;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class BeerDataSource {
	
	private SQLiteDatabase database;
	private DatabaseHandler dbHelper;

	public BeerDataSource(Context context) {
		dbHelper = new DatabaseHandler(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}
	
	public void close() {
		dbHelper.close();
	}

	
	public List<Item> getItems()
	{
		List<Item> items = new ArrayList<Item>(); 
		
		String query = "SELECT id, name, cost, time, date FROM " + DatabaseHandler.TABLE_ITEM;
		Cursor cursor = database.rawQuery(query, null);
		cursor.moveToFirst();
		 
		while (!cursor.isAfterLast()) {
			Item i = new Item(cursor.getLong(0), 
					cursor.getString(1), 
					Double.parseDouble(cursor.getString(2)), 
					cursor.getString(3), 
					cursor.getString(4));
			items.add(i);
			cursor.moveToNext();
		}

		cursor.close();
		return items;
	} 
	
	public Item addItem(Item item) {
		ContentValues values = new ContentValues();
		values.put(DatabaseHandler.ITEM_NAME, item.getName());
		values.put(DatabaseHandler.ITEM_COST, item.getCost());
		values.put(DatabaseHandler.ITEM_TIME, item.getTime());
		values.put(DatabaseHandler.ITEM_DATE, item.getDate());

		long insertId = database.insert(DatabaseHandler.TABLE_ITEM, null, values);
		item.setId(insertId);
		Log.d("DEBUG", "TABLE_ITEM: "+ insertId);
		
		return item;
	}
	
	
	public int deleteItem(long id)
	{
		return database.delete(DatabaseHandler.TABLE_ITEM, DatabaseHandler.CATEGORY_ID
				+ " = " + id , null);
	}
	
	public Category addCategory(Category cat) {
		ContentValues values = new ContentValues();
		values.put(DatabaseHandler.CATEGORY_NAME, cat.getName());

		long insertId = database.insert(DatabaseHandler.TABLE_CATEGORY, null, values);
		cat.setId(insertId);
		Log.d("DEBUG", "rowId: "+ insertId);		
		
		return cat;
	}
	
	public List<Category> getCategories()
	{
		List<Category> cats = new ArrayList<Category>(); 
		
		String query = "SELECT id, name FROM " + DatabaseHandler.TABLE_CATEGORY;
		Cursor cursor = database.rawQuery(query, null);
		cursor.moveToFirst();

		while (!cursor.isAfterLast()) {
			Category c = new Category(cursor.getLong(0), cursor.getString(1));
			cats.add(c);
			cursor.moveToNext();
		}

		cursor.close();
		return cats;
	}
	
	public int deleteCategory(String name)
	{
		return database.delete(DatabaseHandler.TABLE_CATEGORY, DatabaseHandler.CATEGORY_NAME
				+ " = '" + name +"'", null);
	}



}