package in.ashokit.util;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import in.ashokit.entity.CitizenPlan;

@Component
public class PdfGenerator {
	
	public boolean exportPdf(HttpServletResponse response, List<CitizenPlan> plans,File file) throws Exception {
		
		Document document = new Document(PageSize.A4);
		PdfWriter.getInstance(document, response.getOutputStream());
		PdfWriter.getInstance(document, new FileOutputStream(file));
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
		
		return true;
	}

}
