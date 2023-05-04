package in.ashokit.sevices;

import in.ashokit.binding.LoginForm;
import in.ashokit.binding.SignUpForm;
import in.ashokit.binding.UnlockForm;

public interface UserService {

	public String login(LoginForm loginForm);
	
	public String signup(SignUpForm signForm);
	
	public boolean unlockAccount(UnlockForm unlockForm);
	
	public String forgotPwd(String email);
}
