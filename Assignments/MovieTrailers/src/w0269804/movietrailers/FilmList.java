package w0269804.movietrailers;

/* Custom Imports */
import java.util.ArrayList;
import w0269804.data.FilmManager;
import w0269804.presentation.CustomList;
import w0269804.business.Film;

/* System Imports */
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class FilmList extends Activity {

	// STRINGS XML ARRAY MAPPING
	final static int NAME_INDEX = 0;
	final static int DESCRIPTION_INDEX = 1;
	final static int THUMB_INDEX = 2;
	final static int TRAILER_INDEX = 3;
	final static int RATING_INDEX = 4;
	
	// STATE HANDLING
	static int selectedFilm; // handles the selected film
	static FilmManager fm; // handles data in database
	static ArrayList<Film> films; // the list of films
	static boolean filmsLoaded = false; // handles whether the films have been loaded.
	final static String BUNDLE_SELECTED_KEY = "SELECTED"; // the bundle key

	//LIST VIEW COMPONENTS
	ListView list; // the list view
	String[] web; // the list of film titles
	Integer[] imageId; // the film image
	String[] trailers; // the trailer ID
	Intent i; // the intent object


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_film_list);

		fm = new FilmManager(FilmList.this); // manages interactions with DB
		
		// load films into DB only once
		if (!filmsLoaded) {
			fm.removeAllFilms(); // removes all existing films from DB
			fm.insertFilms(getFilmsFromResource()); // insert all films
			filmsLoaded = true; // set boolean switch
		}
		
		i = new Intent(this, SingleFilm.class); // intent to send to viewer
		
		// GRID LIST BUILDING
		films = fm.getAllFilms(); // all films as list of objects
		web = fm.getAllTitles(); // list of film titles
		imageId = getImages(); // list of film titles
		trailers = fm.getAllTrailers(); // list of films

		CustomList adapter = new CustomList(this, web, imageId);

		list = (ListView) findViewById(R.id.lstFilms);
		list.setAdapter(adapter);
		
		// BUTTON CLICK
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				i.putExtra(BUNDLE_SELECTED_KEY, position);
				FilmList.selectedFilm = position; // TODO Find a work around for this issue using bundles. 
				                                  // (See MovieTrailers.java for explanation of issue. 
				startActivity(i);

			}
		});
	
	}

	
	// determine if no films remain
	public boolean noFilmsRemain(){
		return FilmList.films.size() == 0;
	}
	
	
	@Override // rebuilds grid list on resume
	public void onResume() { // After a pause OR at startup
		super.onResume();
		rebuildListView(); // reconstruct the list view
	}

	@Override
	// Empty implementation to avoid navigation	to a delete film
	public void onBackPressed() {

	}

	// recreates grid list to reflect database contents
	public void rebuildListView() {

		if(noFilmsRemain()){
			Intent i = new Intent(this ,AddFilm.class);
			startActivity(i); 
		}
		
		films = fm.getAllFilms(); // all films as list of objects
		web = fm.getAllTitles(); // list of film titles
		imageId = getImages(); // list of film titles
		trailers = fm.getAllTrailers(); // list of films

		i = new Intent(this, SingleFilm.class); // intent to send to viewer
		CustomList adapter = new CustomList(this, web, imageId);

		list = (ListView) findViewById(R.id.lstFilms);
		list.setAdapter(adapter);

	}

	// gets all image ids from the DB
	public Integer[] getImages() {

		String thumbs[] = fm.getAllThumbs();
		Integer ids[] = new Integer[thumbs.length];

		for (int i = 0; i < thumbs.length; i++) {
			ids[i] = getResources().getIdentifier(
					thumbs[i].substring(0, thumbs[i].indexOf(".")), "drawable",
					getPackageName());
		}
		return ids;
	}

	// creates a list of film objects from resources
	public ArrayList<Film> getFilmsFromResource() {

		ArrayList<Film> films = new ArrayList<Film>(); // list of film objects
		String allFilms[][] = loadFilmsFromStringResource(); // return an array of string from strings.xml

		for (int i = 0; i < allFilms.length; i++) {
			
			allFilms[i][DESCRIPTION_INDEX] = allFilms[i][DESCRIPTION_INDEX]
					.replace("\n", "").replace("\r", ""); // remove line breaks from description
			films.add(new Film(allFilms[i][0], allFilms[i][1],
					allFilms[i][2], allFilms[i][3]));
		}

		return films;
	}

	// returns a 2D array representing all the films
	public String[][] loadFilmsFromStringResource() {

		Resources res = getResources(); // returns a resource instance
		TypedArray allFilmsArray = res.obtainTypedArray(R.array.allFirms); // get 2d array of strings from String.xml
		int numberOfFilms = allFilmsArray.length(); // the number of items in the array
	
		String[][] allFilmsLoadedArray = new String[numberOfFilms][]; // will hold the 2D array from resources

		for (int i = 0; i < numberOfFilms; ++i) {
			int id = allFilmsArray.getResourceId(i, 0); // get resource by id
			
			if (id > 0) {
				allFilmsLoadedArray[i] = res.getStringArray(id);
			} else {
				// TODO Catch here.
			}
		}
		
		allFilmsArray.recycle(); // Important!

		return allFilmsLoadedArray;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.film_list, menu);
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
