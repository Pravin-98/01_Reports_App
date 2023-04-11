package in.ashokit.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import in.ashokit.entity.CitizenPlan;
import in.ashokit.request.SearchRequest;
import in.ashokit.service.ReportService;

@Controller
public class ReportController {

	@Autowired
	private ReportService service;

	@PostMapping("/demo")
	public String handleSearch(@ModelAttribute("search") SearchRequest search, Model model) {
		List<CitizenPlan> plans = service.search(search);
		model.addAttribute("plans", plans);
		init(model);
		return "index";
	}

	@GetMapping("/")
	public String indexPage(Model model) {
		model.addAttribute("search", new SearchRequest());
		init(model);
		return "index";
	}

	@GetMapping("/excel")
	public void excelExport(HttpServletResponse responce) throws Exception {
		responce.setContentType("application/octet-stream");
		responce.addHeader("Content-Disposition", "attachment;filename=plans.xls");
		service.exportExcel(responce);
	}
	
	@GetMapping("/pdf")
	public void pdfExport(HttpServletResponse responce) throws Exception {
		responce.setContentType("application/pdf");
		responce.addHeader("Content-Disposition", "attachment;filename=plans.pdf");
		service.exportPdf(responce);
	}

	private void init(Model model) {
		model.addAttribute("names", service.getPlanNames());
		model.addAttribute("status", service.getPlanStatuses());
	}
}
