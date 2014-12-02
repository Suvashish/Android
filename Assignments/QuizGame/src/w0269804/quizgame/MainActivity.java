package w0269804.quizgame;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

	EditText eText; // the name entry editText
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /** Called when the user clicks the Send button */
    /** Defined in the MainActivity.XML */
    
	public void sendName(View view) {
    	
    	eText = (EditText) findViewById(R.id.edtTextName); // link element
    	String name = eText.getText().toString(); // grab its text
    	
    	if(name != null && !name.trim().equals("") && name.length() < 25){
    		Intent i = new Intent(this, QuizActivity.class);
    		Bundle extras = new Bundle();// create bundle object
    		extras.putString("NAME",  name);// fill bundle
    		i.putExtras(extras); // put the extras in the bundle
    		startActivity(i); // send the bundle to the next activity
    	} else { 
    		Toast.makeText(getApplicationContext(), "Invalid format for name!",
    				Toast.LENGTH_LONG).show(); // show the toast
    	} // end if
    
    }

}
