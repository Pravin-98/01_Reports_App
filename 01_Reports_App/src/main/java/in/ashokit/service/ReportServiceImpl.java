package in.ashokit.service;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import in.ashokit.entity.CitizenPlan;
import in.ashokit.repository.CitizenPlanRepository;
import in.ashokit.request.SearchRequest;
import in.ashokit.util.EmailUtils;
import in.ashokit.util.ExcelGenerator;
import in.ashokit.util.PdfGenerator;

@Service
public class ReportServiceImpl implements ReportService {

	@Autowired
	private CitizenPlanRepository planRepo;
	
	@Autowired
	private ExcelGenerator excelGenrator;
	
	@Autowired
	private PdfGenerator pdfGenerator;
	
	@Autowired
	private EmailUtils emailUtils;
	
	@Override
	public List<String> getPlanNames() {
		
		return planRepo.getPlanName();
	}

	@Override
	public List<String> getPlanStatuses() {
		
		return planRepo.getPlanStatus();
	}

	@Override
	public List<CitizenPlan> search(SearchRequest request) {
		
		CitizenPlan entity = new CitizenPlan();
		
		if (null != request.getPlanName() && !"".equals(request.getPlanName()) ) {
			
			entity.setPlanName(request.getPlanName());
		}
		
		if (null != request.getPlanStatus() && !"".equals(request.getPlanStatus()) ) {
			
			entity.setPlanStatus(request.getPlanStatus());
		}
		
		if (null != request.getGender() && !"".equals(request.getGender()) ) {
			
			entity.setGender(request.getGender());
		}
		
		if (null != request.getStartDate() && !"".equals(request.getStartDate()) ) {
			
			String startDate =request.getStartDate();
			DateTimeFormatter fromatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			// Converting String to LocalDate
			LocalDate localDate = LocalDate.parse(startDate, fromatter);
			entity.setPlanStartDate(localDate);
		}
		
		if (null != request.getEndDate() && !"".equals(request.getEndDate()) ) {
			
			String endDate =request.getEndDate();
			DateTimeFormatter fromatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			// Converting String to LocalDate
			LocalDate localDate = LocalDate.parse(endDate, fromatter);
			entity.setPlanEndDate(localDate);
		}
		
		// Example is used to create dynamic query.
		return planRepo.findAll(Example.of(entity));
	}

	@Override
	public boolean exportExcel(HttpServletResponse responce) throws Exception {
		
		File file = new File("Plans.xls");
		
		List<CitizenPlan> plans = planRepo.findAll();
		boolean status = excelGenrator.exportExcel(responce, plans, file);
		
		String subject = "Test Mail Subject";
		String body = "<h1>Test Mail Body</h1>";
		String to = "pravinkumarbirajdar98@gmail.com";
		
		emailUtils.sendMail(subject, body, to, file);
		file.delete();
		return status;
	}

	@Override
	public boolean exportPdf(HttpServletResponse responce)throws Exception  {
		
		File file = new File("Plans.pdf");
		
		List<CitizenPlan> plans = planRepo.findAll();
		boolean status = pdfGenerator.exportPdf(responce, plans, file);
		
		String subject = "Test Mail Subject";
		String body = "<h1>Test Mail Body</h1>";
		String to = "pravinkumarbirajdar98@gmail.com";
		
		emailUtils.sendMail(subject, body, to, file);
		file.delete();
		return status;
	}

}
