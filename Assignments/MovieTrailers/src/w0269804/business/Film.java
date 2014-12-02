package w0269804.business;

public class Film {
	
	private int id;
	private String name;
	private String description;
	private String thumb;
	private String trailer;
	private int rating;
	
	public Film(int id, String name, String description, String thumb, String trailer, int rating){
		
		this.id = id;
		this.name = name;
		this.description = description;
		this.thumb = thumb;
		this.trailer = trailer;
		this.setRating(rating);
	}
	
	
	public Film(String name, String description, String thumb, String trailer){
		
		this.name = name;
		this.description = description;
		this.thumb = thumb;
		this.trailer = trailer;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getThumb() {
		return thumb;
	}
	public void setThumb(String thumb) {
		this.thumb = thumb;
	}
	public String getTrailer() {
		return trailer;
	}
	public void setTrailer(String trailer) {
		this.trailer = trailer;
	}


	public int getRating() {
		return rating;
	}


	public void setRating(int rating) {
		this.rating = rating;
	}
	

}
