package oulu.beercalc.view;

import java.util.List;

import oulu.beercalc.R;
import oulu.beercalc.db.BeerDataSource;
import oulu.beercalc.model.BeerData;
import oulu.beercalc.model.Category;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
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

public class ListCategoryActivity extends ListActivity {

	private AlertDialog alert;
	private ArrayAdapter<Category> adapter;
	private BeerData data;
	private final BeerDataSource dbs = new BeerDataSource(this);	
	private Button button;
	private Category selectedCategory = null;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_category);
  
		data = BeerData.getInstance();
		dbs.open();
		
		adapter = new ArrayAdapter<Category>(this, android.R.layout.simple_expandable_list_item_1, data.getCategorys());
		setListAdapter(adapter);

		final CharSequence[] menuItems = { "Edit", "Delete", "Cancel" };
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Options");
		builder.setItems(menuItems, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int item) {

				if (item == 0) // edit
				{
					// Edit category dialog
					AlertDialog.Builder builder = new AlertDialog.Builder(ListCategoryActivity.this);
					builder.setTitle("Title");

					final EditText input = new EditText(ListCategoryActivity.this);
					input.setInputType(InputType.TYPE_CLASS_TEXT);
					input.setText(selectedCategory.getName(), TextView.BufferType.EDITABLE);
					builder.setView(input);

					builder.setPositiveButton("OK", new DialogInterface.OnClickListener() { 
					    @Override
					    public void onClick(DialogInterface dialog, int which) {
					    	String name = input.getText().toString();

					    	// dont allow to save categories with empty name
					    	if(name.length() > 0){
					    		dbs.deleteCategory(selectedCategory.getName());
					    		data.deleteCategory(selectedCategory);

					    		Category cat = new Category(name);
					    		data.addCategory(cat);
					    		dbs.addCategory(cat);
					    		adapter.notifyDataSetChanged();
					    	}
					    }
					});
					builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					    @Override
					    public void onClick(DialogInterface dialog, int which) {
					        dialog.cancel();
					    }
					});
					builder.show();
				}
				else if (item == 1) // Delete
				{
					dbs.deleteCategory(selectedCategory.getName());
					data.deleteCategory(selectedCategory);
					adapter.notifyDataSetChanged();
				}
			}
		});
		alert = builder.create();

		this.getListView().setLongClickable(true);
		this.getListView().setOnItemLongClickListener(
				new OnItemLongClickListener() {
					public boolean onItemLongClick(AdapterView<?> parent, View v, int position, long id) {
						Category cat = (Category) getListAdapter().getItem(position);
						Log.d("DEBUG", "item: " + cat);
						selectedCategory = cat;
						alert.show();
						return true;
					}
				});
		
		 
		button = (Button) findViewById(R.id.button1);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {

				// Add category dialog
				AlertDialog.Builder builder = new AlertDialog.Builder(ListCategoryActivity.this);
				builder.setTitle("Title");

				// Set up the input
				final EditText input = new EditText(ListCategoryActivity.this);
				input.setInputType(InputType.TYPE_CLASS_TEXT);
				builder.setView(input);

				builder.setPositiveButton("OK", new DialogInterface.OnClickListener() { 
				    @Override
				    public void onClick(DialogInterface dialog, int which) {
				    	String name = input.getText().toString();

				    	// dont allow to save categories with empty name
				    	if(name.length() > 0){
				    		Category cat = new Category(name);
				    		dbs.addCategory(cat);
				    		data.addCategory(cat);
				    		adapter.notifyDataSetChanged();
				    	}
				    }
				});
				builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				    @Override
				    public void onClick(DialogInterface dialog, int which) {
				        dialog.cancel();
				    }
				});
				builder.show();
			}
		});	
	}
	
	@Override
	public void onListItemClick(ListView parent, View v, int position, long id) {
		
		// Short click on category
		Category cat = (Category) getListAdapter().getItem(position);
		Log.d("ListCategoryActivity", "selectedCategory: " + cat);
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
			Intent intent = new Intent(this, MainActivity.class);
			startActivity(intent);
			return true;		
			
		case R.id.action_categories:
			// Switch to Category Activitiy, since we're already in it, do nothing 
			return true;
			
		default:
			return super.onOptionsItemSelected(item);
		}
		
	}

}
