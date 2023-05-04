package in.ashokit.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.ashokit.binding.DashboardResponse;
import in.ashokit.binding.EnquiryForm;
import in.ashokit.binding.EnquirySearchCriteria;
import in.ashokit.entiry.StudentEnqEntity;
import in.ashokit.sevices.EnquiryService;

@Controller
public class EnquiryController {
	
	@Autowired
	private HttpSession session;
	@Autowired
	private EnquiryService service;
	
	@GetMapping("/logout")
	public String logout() {
		session.invalidate();
		return "index";
	}
	
	@GetMapping("/dashboard")
	public String dashbordPage(Model model) {
		Integer userid =(Integer) session.getAttribute("userId");
		DashboardResponse dashboardResponse = service.dashboardData(userid);
		model.addAttribute("dashboardResponse",dashboardResponse); 
		return "dashboard";
	}	
	
	@GetMapping("/enquiry")
	public String addEnquirypage(Model model) {
		model.addAttribute("enquiryForm", new EnquiryForm());
		getStatusAndCourseName(model);
		
		return "add-enquiry";
	}

	private void getStatusAndCourseName(Model model) {
		model.addAttribute("enqStatus", service.getEnqStatus());
		model.addAttribute("courses", service.getCourseNames());
	}
	
	@PostMapping("/AddEnquiry")
	public String addEnquiry(@ModelAttribute("enquiryForm") EnquiryForm enquiryForm, Model model) {
	
		Integer userid =(Integer) session.getAttribute("userId");
		String status = service.upsertEnquiry(enquiryForm , userid);
	
		model.addAttribute("succMag", status);
		getStatusAndCourseName(model);

		return "add-enquiry";
	}
	
	@GetMapping("/enquires")
	public String viewEnquiriesPage(Model model) {		
		getStatusAndCourseName(model);
		Integer userId =(Integer) session.getAttribute("userId");
		List<StudentEnqEntity> enquries = service.getEnquries(userId);
		model.addAttribute("enquires", enquries);
		return "view-enquiries";
	}
	
	@GetMapping("/filterEnquiryes")
	public String getFilteredEnq(@RequestParam("cname")String cname,
								 @RequestParam("status")String status,
								 @RequestParam("mode")String mode, Model model) {
		
		EnquirySearchCriteria criteria = new EnquirySearchCriteria();
		criteria.setCourse(cname);
		criteria.setEnuiryStatus(status);
		criteria.setClassMode(mode);
		
		Integer userid =(Integer) session.getAttribute("userId");
		
		List<StudentEnqEntity> enquiries = service.getEnquriesBySearchCriteria(userid, criteria);
		model.addAttribute("enquiries", enquiries);
		
		return "filterTable";
	}
	
	@GetMapping("/edit")
	public String editEnquiry(@RequestParam("studentId") Integer studentId, Model model) {
		
		EnquiryForm form = service.getEnquiry(studentId);
		model.addAttribute("enquiryForm", form);
		getStatusAndCourseName(model);
		return "add-enquiry";
	}
	
	@GetMapping("/delete")
	public String deleteEnquiry(@RequestParam("studentId") Integer studentId, Model model) {
		
		List<StudentEnqEntity> entity = service.deleteEnquiries(studentId);
		model.addAttribute("enquires", entity);		
		model.addAttribute("msg", "Recored Deleted...");
		return "view-enquiries";
	}
}
