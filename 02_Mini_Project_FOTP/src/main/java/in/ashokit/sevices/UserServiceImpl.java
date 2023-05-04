package in.ashokit.sevices;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.ashokit.binding.LoginForm;
import in.ashokit.binding.SignUpForm;
import in.ashokit.binding.UnlockForm;
import in.ashokit.entiry.UserDtlsEntity;
import in.ashokit.repository.UserDtlsRepo;
import in.ashokit.util.EmailUtils;
import in.ashokit.util.PwdUtils;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDtlsRepo userDtlsRepo;
	
	@Autowired
	private EmailUtils emailUtils;
	
	@Autowired
	private HttpSession session;
	
	@Override
	public String login(LoginForm loginForm) {
		UserDtlsEntity entity = userDtlsRepo.findByEmailAndPassword(loginForm.email, loginForm.password);
		if (entity == null) {
			return "Invalid Credentials";
		} 
		
		if (entity.getAccStatus().equals("LOCKED")) {
			return "Yout Account is Locked";
		}
		session.setAttribute("userId", entity.getUserId());
		return "Success";
	}

	@Override
	public String signup(SignUpForm signupForm) {
		
		// TODO checking user is already exist or not.
			UserDtlsEntity user = userDtlsRepo.findByEmail(signupForm.getEmail());
			if (user != null) {
				
				return "With this email user alredy exist!";
			}
		try {
			
			//TODO Converting studentForm object to StudentEntity object.
			UserDtlsEntity entity = new UserDtlsEntity();
			BeanUtils.copyProperties(signupForm, entity);
			
			// TODO generate random Password and set to object.
			String tempPassword = PwdUtils.generatePassword();
			entity.setPassword(tempPassword);
			
			// TODO set accountSatatus as Locked
			entity.setAccStatus("LOCKED");
			
			// TODO insert recored
			userDtlsRepo.save(entity);
			
			// TODO Send email to User to unlock the Account
			String to = signupForm.getEmail();
			String subject = "Unlock Your Account | Ashok IT";
			StringBuffer body = new StringBuffer();
			body.append("<h1>Use below temporary password to unlock your Account</h1>");
			body.append("Temporary Password : "+ tempPassword);
			body.append("<br/>");
			body.append("<a href=\"http://localhost:9090/unlock?email="+to+"\">Cilck Hear To Unlock Your Account");
			emailUtils.sendEmail(to, subject, body.toString());
			
			return "Account Created Check Your Email";
		} catch (Exception e) {
			e.printStackTrace();
			return "Somting went wrong";
		}	
				
	}

	@Override
	public boolean unlockAccount(UnlockForm unlockForm) {
		// TODO validate email and password
			UserDtlsEntity entity = userDtlsRepo.findByEmail(unlockForm.getEmail());
			
			if (entity.getPassword().equals(unlockForm.getTemporaryPwd())) {
				// Storing newPassword.
				entity.setPassword(unlockForm.getNewPassword());
				entity.setAccStatus("UNLOCKED");
				userDtlsRepo.save(entity);
				return true;
			}else {
				return false;
			}		
	}

	@Override
	public String forgotPwd(String email) {
		// TODO check with this email account is available or not.
		UserDtlsEntity entity = userDtlsRepo.findByEmail(email);
		if (entity == null) {
			return "Invalid Email ID";
		}
		
		// TODO Send Email with password.
		String to = email;
		String subject = "This is Your Password | Ashok IT";
		StringBuffer body = new StringBuffer();
		body.append("<h1>Use Below Password To Login </h1>");
		body.append("Your Password :" + entity.getPassword());
		body.append("<br/>");
		body.append("<a href=\"http://localhost:9090/login\">Cilck Hear To Login Your Account");
		emailUtils.sendEmail(to, subject, body.toString());
		
		return "Password Send To Your Email. Check Your Email";
	}

}
