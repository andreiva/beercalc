package oulu.beercalc.view;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import oulu.beercalc.R;
import oulu.beercalc.db.BeerDataSource;
import oulu.beercalc.model.BeerData;
import oulu.beercalc.model.Category;
import oulu.beercalc.model.Item;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity  extends ListActivity {

	private AlertDialog alert;
	private ArrayAdapter<Item> adapter;
	private BeerData data;
	private Button button;
	private final BeerDataSource dbs = new BeerDataSource(this);
	private Item selectedItem;
	private TextView totalPrice;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
  
		data = BeerData.getInstance();
		dbs.open();
//		dbs.addCategory(new Category("Beer"));
//		dbs.addCategory(new Category("K-Market"));
//		dbs.addCategory(new Category("Guns"));
		List<Category> cats = dbs.getCategories();
		data.setCategorys(cats);
		data.setItems(dbs.getItems());
		
		updateTotalPrice();
		  
		
		adapter = new ArrayAdapter<Item>(this, android.R.layout.simple_expandable_list_item_1, data.getItems());
		setListAdapter(adapter);
		
		final CharSequence[] menuItems = { "Edit", "Delete", "Cancel" };
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Options");
		builder.setItems(menuItems, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int item) {

				if (item == 0) // edit
				{
//	           		data.getSelectedTaskList().setEditing(true);
//        	 		Intent intent = new Intent(tasksActivity,
//					TaskEditorActivity.class);
//					startActivity(intent);
				}
				else if (item == 1) // Delete
				{
					dbs.deleteItem(selectedItem.getId());
					data.deleteItem(selectedItem);
					adapter.notifyDataSetChanged();
					updateTotalPrice();
				}
			}
		});
		alert = builder.create();

		this.getListView().setLongClickable(true);
		this.getListView().setOnItemLongClickListener(
				new OnItemLongClickListener() {
					public boolean onItemLongClick(AdapterView<?> parent, View v, int position, long id) {
						Item item = (Item) getListAdapter().getItem(position);
						Log.d("DEBUG", "item: " + item + " id: " + item.getId());
						selectedItem = item;
						alert.show();
						return true;
					}
				});
		
		
		button = (Button) findViewById(R.id.button1);
		button.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View arg0) {
			
				List<String> names = new ArrayList<String>();
				Iterator<Category> iterator = data.getCategorys().iterator();
				while (iterator.hasNext()) {
					names.add(iterator.next().getName());
				}
				
				DialogAddItem dialogAddItem = new DialogAddItem(MainActivity.this, names, new DialogAddItem.SpinnerListener() {
					  public void cancelled() {
					    System.out.println("cancel");
					  }
					  public void ready(String name, String price) {
						  
						  SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yy_HH:mm");
						  String[] currentDateandTime = sdf.format(new Date()).split("_");
						  
						  Item i = new Item(name, Double.parseDouble(price), currentDateandTime[1], currentDateandTime[0]);
						  dbs.addItem(i);
						  data.addItem(i);
						    System.out.println("n: "+ name + ", "+ price);
						    updateTotalPrice();
					  }
					});
				dialogAddItem.show();
			
			}
		});
		
	}
	
	@Override
	protected void onResume() {
	    super.onResume();  // Always call the superclass method first
		adapter.notifyDataSetChanged();
	}
	
	@Override
	public void onListItemClick(ListView parent, View v, int position, long id) {
//		TaskList list = (TaskList) getListAdapter().getItem(position);
//		data.setSelectedTaskList(list);
//		Log.d("TasksActivity", "setSelectedTaskList: " + list);
//		Intent intent = new Intent(this, ItemsActivity.class);
//		startActivity(intent);
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);

		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		Log.d("MainActivity", "onOptionsItemSelected: " + item);

		switch (item.getItemId()) {
		case R.id.action_items:
			// Switch to Items Activitiy, since we're already in it, do nothing 
			return true;
			
		case R.id.action_categories:
			Intent intent = new Intent(this, ListCategoryActivity.class);
			startActivity(intent);
			return true;
			
		default:
			return super.onOptionsItemSelected(item);
		}
		
	}
	
	private void updateTotalPrice()
	{
		totalPrice = (TextView) findViewById(R.id.textView1);
		double total = 0;
		Iterator<Item> iterator = data.getItems().iterator();
		while (iterator.hasNext()) {
			Item i = iterator.next();
			total += i.getCost();
		}
		totalPrice.setText(new String("Total: "+total +"€"));
	}
}
