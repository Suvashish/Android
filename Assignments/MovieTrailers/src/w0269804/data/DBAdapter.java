package w0269804.data;

import java.io.File;

import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class DBAdapter {

	private final Context context;
	private DatabaseHelper DBHelper;
	private SQLiteDatabase db;

	/* DB INFO */
	public static final String DATABASE_NAME = "FilmDB";
	public static final String DATABASE_TABLE = "films";
        
	/* FIELDS */
	public static final String KEY_ROWID = "_id";
	public static final String KEY_NAME = "name";
	public static final String KEY_DESCRIPTION = "description";
	public static final String KEY_THUMBNAIL = "thumbnail";
	public static final String KEY_TRAILER = "trailer";
	public static final String KEY_RATING = "rating";

	/* TECH */
	public static final String TAG ="DBAdapter";
	public static final int DATABASE_VERSION = 1;
	
	/* UTILITY QUERIES */
	public static final String CREATE_DATABASE = 
	
	"CREATE TABLE " + DATABASE_TABLE + 
	  " (" 
	      + KEY_ROWID + " integer primary key autoincrement, "
	      + KEY_NAME + " text,"
	      + KEY_DESCRIPTION + " text not null,"
	      + KEY_TRAILER + " text not null,"
	      + KEY_THUMBNAIL + " text not null," 
	      + KEY_RATING + " integer DEFAULT 0" + 
	  ");";
	  
	
	
    /* CONSTRUCTOR */
	public DBAdapter(Context ctx)
	{
		this.context = ctx;
		DBHelper = new DatabaseHelper(context);
	}
	
    /* DATABASE CREATION */
	private static class DatabaseHelper extends SQLiteOpenHelper
	{
                //  super constructor
		DatabaseHelper(Context context)
		{
			super(context,DATABASE_NAME,null,DATABASE_VERSION);
		}

		/* DATABASE CREATION */
		@Override
		public void onCreate(SQLiteDatabase db)
		{
			try{
				db.execSQL(CREATE_DATABASE);
				
			}catch (SQLException e){
				e.printStackTrace();
			}
			
		}//end onCreate
		
		/* DATABASE UPGRADE */
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion,int newVersion)
		{
			Log.w(TAG,"Upgrading database from version "+oldVersion+" to "
			+ newVersion + ", which will destroy all old data");
			db.execSQL("DROP TABLE IF EXISTS" + DATABASE_TABLE + ";");
			onCreate(db);
		}

	}
	
	/* OPEN DATABASE */
	public DBAdapter open() throws SQLException
	{
		db=DBHelper.getWritableDatabase();
		return this;
	}

	/* CLOSE DATABASE */
	public void close()
	{
		DBHelper.close();
	}
	
	/* INSERT FILM */
	public long  insertFilm(String name, String description, String thumbnail, String trailer)
	{
		ContentValues initialValues = new ContentValues();
		
		initialValues.put(KEY_NAME, name);
		initialValues.put(KEY_DESCRIPTION, description);
		initialValues.put(KEY_THUMBNAIL, thumbnail);
		initialValues.put(KEY_TRAILER, trailer);
		
		long result = db.insert(DATABASE_TABLE,null,initialValues);
		return result;
	}
	
	
	/* Retrieve All Films */
	public Cursor getAllFilms()
	{
		return db.query(DATABASE_TABLE,new String[] {KEY_ROWID,KEY_NAME,KEY_DESCRIPTION, 
		KEY_THUMBNAIL, KEY_TRAILER, KEY_RATING}, KEY_NAME + "  <> ''",null,null,null,null);
	}
	
	/* Set an Individual Rating */
	public long setRating(long rowId, int rating){
		
		ContentValues updateValues = new ContentValues();
		updateValues.put(KEY_RATING, rating);
		return db.update(DATABASE_TABLE,updateValues, KEY_ROWID +"="+ rowId, null);
	}
	
	/* Get an Individual Rating */
	public Cursor getRating(long rowId){
		
		Cursor mCursor = db.query(true,DATABASE_TABLE,new String[]
				{KEY_RATING}, KEY_ROWID +"="+ rowId,null,
				null,null,null,null);
		
		return mCursor;
		
	}
	
	/* Retrieve Single Film */
	public Cursor getFilm(long rowId) throws SQLException
	{
		Cursor mCursor = db.query(true,DATABASE_TABLE,new String[]
		{KEY_ROWID,KEY_NAME,KEY_DESCRIPTION, KEY_THUMBNAIL, KEY_TRAILER, KEY_RATING}, KEY_ROWID +"="+ rowId,null,
		null,null,null,null);
		
		if(mCursor != null)
		{
			mCursor.moveToFirst();
		}
		
		return mCursor;
	}
	
	
	/* Delete Film */
	public boolean deleteFilm(long rowId)
	{
		return db.delete(DATABASE_TABLE,KEY_ROWID+"="+rowId,null) > 0;
	}
	
	 public void DisplayFilm(Cursor c)
	{
		Toast.makeText(context,
		"ID: " + c.getString(0) + "\n" +
		"NAME: " + c.getString(1) + "\n" +
		"DESCRIPTION: " + c.getString(2),
		Toast.LENGTH_LONG).show();
	} 
}
	