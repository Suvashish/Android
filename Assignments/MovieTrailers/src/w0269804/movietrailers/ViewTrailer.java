package w0269804.movietrailers;

import android.app.Activity;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

public class ViewTrailer extends Activity {

int selectedFilm; // the selected film

Button btnPlay; // media controls
Button btnStop;
Button btnPause;
Button btnBack;
Button btnForward;

final int BACK_FORWARD_AMOUNT = 30000; // the back/forward amount
int currentPosition = 0; // the players current position

VideoView mVideoView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_trailer);

		Bundle extras = getIntent().getExtras(); // get the selected film from the intent

		// handles page reload, if bundle is null then grab the selected film from the film list
		if (extras == null) {
			selectedFilm = FilmList.selectedFilm;
		} else {
			selectedFilm = extras.getInt(FilmList.BUNDLE_SELECTED_KEY);
			FilmList.selectedFilm = selectedFilm;
		}

		// window requires a pixel format setting
		getWindow().setFormat(PixelFormat.UNKNOWN);

		// set the video uri for the player.
		mVideoView = (VideoView) findViewById(R.id.vViewTrailer);
		String uriPath = "android.resource://" + getPackageName() + "/raw/"
				+ FilmList.films.get(selectedFilm).getTrailer();
		Uri uri = Uri.parse(uriPath);
		
		mVideoView.setVideoURI(uri);
		mVideoView.requestFocus();
		
		mVideoView.start(); // start playing film
		
		// action listeners for media control buttons
		btnStop = (Button) findViewById(R.id.btnStop);
		btnStop.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// pause film, move to 0 and refresh drawing
				mVideoView.pause();
				currentPosition = 0;
				mVideoView.seekTo(currentPosition);
				mVideoView.refreshDrawableState();

			}
		});
		
		btnPlay = (Button) findViewById(R.id.btnPlay);
		btnPlay.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// if it is not playing, go to current position and start
				if (!mVideoView.isPlaying()) {

					mVideoView.seekTo(currentPosition);
					mVideoView.start();

				}

			}
		});
		
		
		btnPause = (Button) findViewById(R.id.btnAddFilm);
		btnPause.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// hold on to current position and pause video
				currentPosition = mVideoView.getCurrentPosition();
				mVideoView.pause();
			}
		});
		
		
		btnBack = (Button) findViewById(R.id.btnBack);
		btnBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				currentPosition = mVideoView.getCurrentPosition();
				
				// move the current position back by fixed amount
				if(currentPosition - BACK_FORWARD_AMOUNT > 0){
					currentPosition -= BACK_FORWARD_AMOUNT;
					mVideoView.seekTo(currentPosition);
					mVideoView.start();
				} else {
					currentPosition = 0;
					mVideoView.seekTo(currentPosition);
					mVideoView.start();
				}
				
				
		}});
			
		btnForward = (Button) findViewById(R.id.btnForward);
		btnForward.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				currentPosition = mVideoView.getCurrentPosition();
				
				// move the current position ahead by a fixed amount
				if(currentPosition + BACK_FORWARD_AMOUNT < mVideoView.getDuration()){
					currentPosition += BACK_FORWARD_AMOUNT;
					mVideoView.seekTo(currentPosition);
					mVideoView.start();
				} else {
					currentPosition = mVideoView.getDuration();
					mVideoView.seekTo(currentPosition);
					mVideoView.start();
				}
				
				
		}});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_trailer, menu);
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
