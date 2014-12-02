package w0269804.movietrailers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class SingleFilm extends Activity {

	int selectedFilm;
	ImageButton trailerButton; // the thumbnail button
	TextView descriptionTV;
	RatingBar rBar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_single_film);
		
		
		Bundle extras = getIntent().getExtras();

		if (extras == null) {
			selectedFilm = FilmList.selectedFilm;
		} else {
			selectedFilm = extras.getInt(FilmList.BUNDLE_SELECTED_KEY);
			FilmList.selectedFilm = selectedFilm;
		}
		
		// attatch the rating bar
		
		rBar = (RatingBar) findViewById(R.id.rBarFilmRating);
		rBar.setRating(FilmList.films.get(selectedFilm).getRating());
		rBar.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
			
			@Override
			public void onRatingChanged(RatingBar ratingBar, float rating,
					boolean fromUser) {
			
				FilmList.fm.setFilmRating(FilmList.films.get(selectedFilm).getId(), (int) rating);
			}
		});
		
		
		trailerButton = (ImageButton)findViewById(R.id.imgButtonViewTrailer);
		descriptionTV = (TextView) findViewById(R.id.txtDescription);
		
		// display film information
		String filmThumbnail = FilmList.films.get(selectedFilm).getThumb(); // get the image file.
		filmThumbnail = filmThumbnail.substring(0, filmThumbnail.indexOf(".")); // remove extension
		int imageId = getResources().getIdentifier(filmThumbnail, "drawable", getPackageName()); // get imageId
		trailerButton.setImageResource(imageId); // set the thumbnail
		descriptionTV.setText(FilmList.films.get(selectedFilm).getDescription());
			
	}
	
	// go to trailer activity
	public void viewFilmTrailer(View view){
		Intent i = new Intent(this ,ViewTrailer.class);
		i.putExtra(FilmList.BUNDLE_SELECTED_KEY, selectedFilm);
		startActivity(i); 	
	}
	
	// starts the viewing activity (list view)
	public void goToDelete(View view) {
		Intent i = new Intent(this ,DeleteActivity.class);
		i.putExtra(FilmList.BUNDLE_SELECTED_KEY, selectedFilm);
		startActivity(i);
		//startActivity(i); 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.single_film, menu);
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
