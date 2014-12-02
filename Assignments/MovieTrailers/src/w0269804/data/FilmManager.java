package w0269804.data;

import java.util.ArrayList;
import java.util.Collections;
import w0269804.business.Film;
import android.content.Context;
import android.content.ContextWrapper;
import android.database.Cursor;
import android.database.SQLException;

public class FilmManager {

	DBAdapter db;
	
	// constructor
	public FilmManager(Context ctx){
		db = new DBAdapter(ctx);
	}
	
	// remove a single film from the database
	public boolean removeSingleFilm(int filmID){
		
		try {
			db.open();
			if(db.deleteFilm((long) filmID)){
				db.close();
				return true;
			} else {
				db.close();
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			db.close();
		}
		
		return false;
		
	}

	// set the rating of a single film from the database
	public void setFilmRating(int filmID, int rating){
		
		try {
			db.open();
			db.setRating(filmID, rating);
			db.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	// return the rating of a single film
	public int getFilmRating(int filmID){
		
		int filmRating = 0;

		try {
			db.open();
			Cursor c = db.getRating(filmID);

			if (c.moveToFirst()) // if there are records
			{

				filmRating = (c.getInt(0));

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			db.close();
			e.printStackTrace();
		}
		
		db.close();

		return filmRating;
		
		
	}
	
	// remove each film from the database
	public void removeAllFilms(){
		
		try {

			db.open(); // open DB
			Cursor c = db.getAllFilms(); // query for all films

			if (c.moveToFirst()) // if there are records
			{
				do {
					db.deleteFilm(Integer.parseInt(c.getString(0)));
				} while (c.moveToNext());
			}

			db.close();

		} catch (NumberFormatException e) {
			e.printStackTrace();
			db.close();
		} catch (SQLException e) {
			e.printStackTrace();
			db.close();
		}
		
	}
	
	// takes an array list of film objects and inserts it into the database
	public void insertFilms(ArrayList<Film> films){
		
		films.removeAll(Collections.singleton(null));
		
		try {
			for (Film film : films) {
					db.open();
					db.insertFilm(film.getName(), film.getDescription(), film.getThumb(), film.getTrailer());
					db.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	// inserts an individual film
	public void insertFilm(Film film){
		
		try {
			db.open();
			db.insertFilm(film.getName(), film.getDescription(), film.getThumb(), film.getTrailer());
			db.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// returns a collection of film objects
	public ArrayList<Film> getAllFilms(){
		
		db.open();
		Cursor c = db.getAllFilms();
	
		
		ArrayList<Film> allFilms = new ArrayList<Film>();
		allFilms.removeAll(Collections.singleton(null));
		
		if(c.moveToFirst())
		{
			do{
				allFilms.add(new Film(Integer.parseInt(c.getString(0)), c.getString(1),
						c.getString(2), c.getString(3), c.getString(4), c.getInt(5)));
			}while(c.moveToNext());
		}
		
		db.close();
		
		return allFilms;
		
	}
	
	// returns all the thumb strings in database
	public String[] getAllThumbs(){
		
		ArrayList<Film> films = getAllFilms();
		String thumbs[] = new String[films.size()];
		
		for (int i = 0; i < films.size(); i++) {
			thumbs[i] = films.get(i).getThumb();
		}
	
		return thumbs;
		
	}
	
	public String[] getAllTrailers(){
		
		ArrayList<Film> films = getAllFilms();
		String trailers[] = new String[films.size()];
		
		for (int i = 0; i < films.size(); i++) {
			trailers[i] = films.get(i).getTrailer();
		}
		
		return trailers;
	}
	
	
	public int[] getAllIDs(){
		
		ArrayList<Film> films = getAllFilms();
		int ids[] = new int[films.size()];
		
		for (int i = 0; i < films.size(); i++) {
			ids[i] = films.get(i).getId();
		}
		
		return ids;
	}
	
	// returns and the title strings in the database
	public String[] getAllTitles(){
		
		ArrayList<Film> films = getAllFilms();
		String titles[] = new String[films.size()];
		
		for (int i = 0; i < films.size(); i++) {
			titles[i] = films.get(i).getName();
		}
	
		return titles;
	}
	
	
	
}
