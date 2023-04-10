package in.ashokit.request;

import lombok.Data;

// Form binding class 
@Data
public class SearchRequest {

	private String planName;
	private String planStatus;
	private String gender;
	private String startDate;
	private String endDate;
	
}
