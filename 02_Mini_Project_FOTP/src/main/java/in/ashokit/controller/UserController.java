package in.ashokit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.ashokit.binding.LoginForm;
import in.ashokit.binding.SignUpForm;
import in.ashokit.binding.UnlockForm;
import in.ashokit.sevices.UserService;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/login")
	public String loginPage(Model model) {
		model.addAttribute("loginForm", new LoginForm());
		return "login";
	}
	
	@PostMapping("/login")
	public String login(@ModelAttribute("loginForm") LoginForm loginForm, Model model) {
		System.out.println(loginForm);
		String status = userService.login(loginForm);
		if (status.contains("Success")) {
			return "redirect:/dashboard";
		}
		
		model.addAttribute("errorMsg",status);
		return "login";
	}
	
	
	@GetMapping("/signup")
	public String signup(Model model) {
		model.addAttribute("user",new SignUpForm());
		return "signup";
	}
	
	@PostMapping("/signup")
	public String handleSignup(@ModelAttribute("user")SignUpForm form,Model model) {
		
		String status = userService.signup(form);
		model.addAttribute("succMsg",status);
		
		return "signup";
	}
	
	@GetMapping("/unlock")
	public String unlock(@RequestParam String email, Model model) {
	
		UnlockForm unlockFormObj = new UnlockForm();
		unlockFormObj.setEmail(email);
		model.addAttribute("unlock", unlockFormObj);
		
		return "unlock";
	}
	
	@PostMapping("/unlock")
	public String unlockUserAccount(@ModelAttribute("unlock") UnlockForm unlock , Model model) {
		
		if (unlock.getNewPassword().equals(unlock.getConfirmPassword())) {
			boolean status = userService.unlockAccount(unlock);
			if (status) {
				model.addAttribute("successMsg", "Your Account Unlocked Successfully");
			} else {
				model.addAttribute("errorMsg", "Given TempraryPassword is Incorrect | Check Your Email");
			}
		} else {
			model.addAttribute("errorMsg", "NewPassword and ConfirmPassword shoud be same");
		}
		
		return "unlock";
	}
	
	@GetMapping("/forgot")
	public String forgotPasswordPage() {
		
		return "forgotPwd";
	}
	
	@PostMapping("/forgot")
	public String forgotPassword(@RequestParam("email")String email, Model model) {
		String status = userService.forgotPwd(email);
		if (status.contains("Invalid Email ID")) {
			model.addAttribute("errorMsg", status);
		}else {		
			model.addAttribute("successMsg", status);
		}
		return "forgotPwd";
	}
	
}
