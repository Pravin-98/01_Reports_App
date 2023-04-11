package in.ashokit.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import in.ashokit.entity.CitizenPlan;
import in.ashokit.repository.CitizenPlanRepository;
import in.ashokit.request.SearchRequest;

@Service
public class ReportServiceImpl implements ReportService {

	@Autowired
	private CitizenPlanRepository planRepo;
	
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
		
		List<CitizenPlan> plans = planRepo.findAll();
		Workbook workbook = new HSSFWorkbook();
		Sheet sheet = workbook.createSheet("plans-data");
		Row headerRow = sheet.createRow(0);
		
		headerRow.createCell(0).setCellValue("ID");
		headerRow.createCell(1).setCellValue("Citizen Name");
		headerRow.createCell(2).setCellValue("Plan Name");
		headerRow.createCell(3).setCellValue("Plan Status");
		headerRow.createCell(4).setCellValue("Start Date");
		headerRow.createCell(5).setCellValue("End Date");
		headerRow.createCell(6).setCellValue("Benefit Amt");
		
		int indexRow = 1;
		
		for(CitizenPlan plan : plans) {
			
			Row dataRow = sheet.createRow(indexRow);
			
			dataRow.createCell(0).setCellValue(plan.getCitizenId());
			dataRow.createCell(1).setCellValue(plan.getCitizenName());
			dataRow.createCell(2).setCellValue(plan.getPlanName());
			dataRow.createCell(3).setCellValue(plan.getPlanStatus());
			
			if (null != plan.getPlanStartDate()) {
				
				dataRow.createCell(4).setCellValue(plan.getPlanStartDate()+"");
			} else {
				
				dataRow.createCell(4).setCellValue("N/A");
			}
			
			if (null != plan.getPlanEndDate()) {
				
				dataRow.createCell(5).setCellValue(plan.getPlanEndDate()+"");
			} else {
				
				dataRow.createCell(5).setCellValue("N/A");
			}
			
			
			if (null != plan.getBenefitAmount()) {

				dataRow.createCell(6).setCellValue(plan.getBenefitAmount());
			} else {

				dataRow.createCell(6).setCellValue("N/A");
			}
			
			indexRow++;
		}
		
		
		ServletOutputStream outputStream = responce.getOutputStream();
		workbook.write(outputStream);
		workbook.close();
		
		return false;
	}

	@Override
	public boolean exportPdf(HttpServletResponse responce)throws Exception  {
		
		Document document = new Document(PageSize.A4);
		PdfWriter.getInstance(document, responce.getOutputStream());
		document.open();
		
		Font fontTitle = FontFactory.getFont(FontFactory.TIMES_ROMAN);
		fontTitle.setSize(20);
		fontTitle.setColor(255, 159, 23);
		
		Paragraph p = new Paragraph("Citizens Plans Info",fontTitle);
		p.setAlignment(Paragraph.ALIGN_CENTER);
		document.add(p);
		
		PdfPTable table = new PdfPTable(6);
		table.setSpacingBefore(10);
		
		table.addCell("ID");
		table.addCell("Citizen Name");
		table.addCell("Plan Name");
		table.addCell("plan Status");
		table.addCell("Start Date");
		table.addCell("End Date");
		
		List<CitizenPlan> plans = planRepo.findAll();
		
		for(CitizenPlan plan : plans) {
			
			table.addCell(String.valueOf(plan.getCitizenId()));
			table.addCell(plan.getCitizenName());
			table.addCell(plan.getPlanName());
			table.addCell(plan.getPlanStatus());
			table.addCell(plan.getPlanStartDate()+"");
			table.addCell(plan.getPlanEndDate()+"");			
		}
		
		document.add(table);
		document.close();
		return false;
	}

}
