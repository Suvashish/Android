package w0269804.movietrailers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

public class DeleteActivity extends Activity {

	int selectedFilm;
	ImageView thumbImage;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_delete);
		
		thumbImage = (ImageView) findViewById(R.id.ivThumbToDelete);
		
		// Bundle handling
		Bundle extras = getIntent().getExtras();
	    
		// See MovieTrailers.java for an explanation of this issue
	    if(extras == null) {
	    	selectedFilm = FilmList.selectedFilm;
	    } else {
	    	selectedFilm = extras.getInt(FilmList.BUNDLE_SELECTED_KEY);
	    	FilmList.selectedFilm = selectedFilm;
	    }
		
	    
		String filmThumbnail = FilmList.films.get(selectedFilm).getThumb(); // get the image file.
		filmThumbnail = filmThumbnail.substring(0, filmThumbnail.indexOf(".")); // remove extension
		int imageId = getResources().getIdentifier(filmThumbnail, "drawable", getPackageName()); // get imageId
		thumbImage.setImageResource(imageId); // set the thumbnail

	}
	
	// deletes the film and goes to the list
	public void goToList(View view) {
		
		int deleteID = FilmList.films.get(selectedFilm).getId(); 
		FilmList.fm.removeSingleFilm(deleteID); 
		Intent i = new Intent(this ,FilmList.class);
		startActivity(i); 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.delete, menu);
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
