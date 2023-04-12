package in.ashokit.util;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;

import in.ashokit.entity.CitizenPlan;

@Component
public class ExcelGenerator {

	public boolean exportExcel(HttpServletResponse response,List<CitizenPlan> plans, File file)throws Exception {
		
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
		
		FileOutputStream fos = new FileOutputStream(file);
		workbook.write(fos);
		fos.close();
		
		ServletOutputStream outputStream = response.getOutputStream();
		workbook.write(outputStream);
		workbook.close();
		

		
		return true;
	}
}
