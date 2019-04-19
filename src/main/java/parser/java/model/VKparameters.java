package parser.java.model;

public class VKparameters {
	private int id;
	private String owner_id;
	private String message;
	private String access_token;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getOwner_id() {
		return owner_id;
	}

	public void setOwner_id(String owner_id) {
		this.owner_id = owner_id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	@Override
	public String toString() {
		return "Parameter: ID=" + this.id + " Owner_id=" + this.owner_id + " Message=" + this.message + " Access_token="
				+ this.access_token;
	}

}
