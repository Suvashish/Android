package w0269804.movietrailers;

import w0269804.data.FilmManager;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MovieTrailers extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_movie_trailers);
		//FilmList.fm = new FilmManager(MovieTrailers.this); // manages interactions with DB
	}

	
	// starts the viewing activity (list view)
	public void viewFilmList(View view) {
		
		Intent i = new Intent(this ,FilmList.class);
		startActivity(i); 
	}
	
	// starts the viewing activity (list view)
	public void viewAddForm(View view) {
		
		Intent i = new Intent(this ,AddFilm.class);
		startActivity(i); 
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.movie_trailers, menu);
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
