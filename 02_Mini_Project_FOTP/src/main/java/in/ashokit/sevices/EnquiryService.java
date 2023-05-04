package in.ashokit.sevices;

import java.util.List;

import in.ashokit.binding.DashboardResponse;
import in.ashokit.binding.EnquiryForm;
import in.ashokit.binding.EnquirySearchCriteria;
import in.ashokit.entiry.StudentEnqEntity;

public interface EnquiryService {

	public DashboardResponse dashboardData(Integer userId);
	
	public List<String> getCourseNames();
	
	public List<String> getEnqStatus();
	
	public String upsertEnquiry(EnquiryForm enquiryForm, Integer userId );
	
	public List<StudentEnqEntity> getEnquriesBySearchCriteria(Integer userId, EnquirySearchCriteria searchCriteria);
	
	public List<StudentEnqEntity> getEnquries(Integer userId);
	
	public EnquiryForm getEnquiry(Integer enqId); 
	
	public List<StudentEnqEntity> deleteEnquiries(Integer enqId);
}
