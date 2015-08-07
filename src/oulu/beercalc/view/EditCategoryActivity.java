package oulu.beercalc.view;

import oulu.beercalc.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

public class EditCategoryActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_category);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_category, menu);
		return true;
	}

}
