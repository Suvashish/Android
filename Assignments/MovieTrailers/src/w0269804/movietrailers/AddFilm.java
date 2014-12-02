package w0269804.movietrailers;

import w0269804.business.Film;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddFilm extends Activity {

	EditText etFilmTitle;
	EditText etFilmDescription;
	final String FILM_THUMB = "film5.jpg";
	final String FILM_TRAILER = "film5";
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_film);
		
		// hook up controls
		etFilmDescription = (EditText) findViewById(R.id.etAddDescription);
		etFilmTitle = (EditText) findViewById(R.id.etAddTitle);
		
	}

	
	// go to trailer activity
	public void addFilm(View view){
		
	
		boolean validEntry = true;
		
		// check description
		if(etFilmDescription.getText() == null || etFilmDescription.getText().toString().trim().equals("")){
			
			Toast.makeText(this,
					"Invalid description, please enter a valid description.",
					Toast.LENGTH_LONG).show();
			
			validEntry = false;
		}
		
		// check title
		if(etFilmTitle.getText() == null || etFilmTitle.getText().toString().trim().equals("")){
			
			Toast.makeText(this,
					"Invalid description, please enter a valid description.",
					Toast.LENGTH_LONG).show();
			
			validEntry = false;
		}
		
		// insert the film into the database and move to list page
		if(validEntry){
			
			String filmTitle = etFilmTitle.getText().toString().trim();
			String filmDescription = etFilmDescription.getText().toString().trim();
			FilmList.fm.insertFilm( new Film(filmTitle, filmDescription, FILM_THUMB, FILM_TRAILER));
			Intent i = new Intent(this ,FilmList.class);
			startActivity(i);
			
		}
	}
		
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_film, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
