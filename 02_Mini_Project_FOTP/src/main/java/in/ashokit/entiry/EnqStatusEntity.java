package in.ashokit.entiry;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="AIT_ENQUIRY_STATUS")
public class EnqStatusEntity {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Integer statusId;
	private String statusName;
}
