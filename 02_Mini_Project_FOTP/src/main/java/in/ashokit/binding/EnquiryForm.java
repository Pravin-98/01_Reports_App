package in.ashokit.binding;

import java.time.LocalDate;

import lombok.Data;

@Data
public class EnquiryForm {

	private Integer studentId;
	private String studentName;
	private Long phno;
	private String classMode;
	private String courseName;
	private String enquiryStatus;
	private LocalDate createdDate;
}
