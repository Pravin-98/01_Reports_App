package in.ashokit.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import in.ashokit.entity.CitizenPlan;
import in.ashokit.request.SearchRequest;

public interface ReportService {

	public List<String> getPlanNames();
	
	public List<String> getPlanStatuses();
	
	public List<CitizenPlan> search(SearchRequest request);
	
	public boolean exportExcel(HttpServletResponse responce)throws Exception ;
	
	public boolean exportPdf(HttpServletResponse responce)throws Exception ;
}
