package in.ashokit.entiry;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="ALT_USER_DTLS")
public class UserDtlsEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer userId;
	private String name;
	@Column(unique=true)
	private String email;
	private Long phono;
	private String password;
	private String accStatus;
	
	@OneToMany(cascade = CascadeType.ALL,  mappedBy="user")
	private List<StudentEnqEntity> enquiries;
}
