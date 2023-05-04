package in.ashokit.binding;

import lombok.Data;

@Data
public class UnlockForm {

	private String temporaryPwd;
	private String newPassword;
	private String confirmPassword;
	private String email;
}
