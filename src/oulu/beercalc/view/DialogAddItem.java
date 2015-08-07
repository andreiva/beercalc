package oulu.beercalc.view;

import java.util.ArrayList;
import java.util.List;

import oulu.beercalc.R;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class DialogAddItem extends Dialog {
    private List<String> mList;
    private Context mContext;
    private Spinner mSpinner;
	private EditText nameText;
	private EditText priceText;


   public interface SpinnerListener {
        public void ready(String name, String price);
        public void cancelled();
    }

    private SpinnerListener mReadyListener;

    public DialogAddItem(Context context, List<String> list, SpinnerListener readyListener) {
        super(context);
        mReadyListener = readyListener;
        mContext = context;
        mList = new ArrayList<String>();
        mList = list;
        // dummy item, empty text
        mList.add(0, "");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.spinner_dialog);
        this.setTitle(R.string.add_new_item);
        mSpinner = (Spinner) findViewById (R.id.dialog_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String> (mContext, android.R.layout.simple_spinner_dropdown_item, mList);
        mSpinner.setAdapter(adapter);        

        nameText = (EditText) findViewById(R.id.editText1);
        priceText = (EditText) findViewById(R.id.editText2);
        
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> arg0, View v, int position, long id)
            {
            	if(position > 0)
            	{
                	nameText.setText(mSpinner.getSelectedItem().toString());
                	priceText.requestFocus();
            		System.out.println("asd "+ mSpinner.getSelectedItem());

            	}
                Log.v("routes", "route selected" + position);
            }

            public void onNothingSelected(AdapterView<?> arg0)
            {
                Log.v("routes", "nothing selected");
            }
        });
        
        
        
        Button buttonOK = (Button) findViewById(R.id.dialogOK);
        Button buttonCancel = (Button) findViewById(R.id.dialogCancel);
        buttonOK.setOnClickListener(new android.view.View.OnClickListener(){
            public void onClick(View v) {
                mReadyListener.ready(nameText.getText().toString(), priceText.getText().toString());
                DialogAddItem.this.dismiss();
            }
        });
        buttonCancel.setOnClickListener(new android.view.View.OnClickListener(){
            public void onClick(View v) {
                mReadyListener.cancelled();
                DialogAddItem.this.dismiss();
            }
        });
    }
    

}