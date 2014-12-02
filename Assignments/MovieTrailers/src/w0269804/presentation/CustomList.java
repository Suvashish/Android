package w0269804.presentation;

/* System Imports */
import w0269804.movietrailers.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomList extends ArrayAdapter<String>{

	private final Activity context; /* The context */
private final String[] title; /* The edit text Strings */
private final Integer[] imageId; /* The resource ID */

// TODO Ask about using film objects to create custom list views.

	public CustomList(Activity context, String[] title, Integer[] imageId) {
		super(context, R.layout.list_single, title);
		
		this.context = context;
		this.title = title;
		this.imageId = imageId;
	}

	
	@SuppressLint("ViewHolder")
	@Override
	public View getView(int position, View view, ViewGroup parent) {
		LayoutInflater inflater = context.getLayoutInflater();
		View rowView = inflater.inflate(R.layout.list_single, null, true);
		TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);
		ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
		
		txtTitle.setText(title[position]);
		imageView.setImageResource(imageId[position]);
		return rowView;
	}
}
