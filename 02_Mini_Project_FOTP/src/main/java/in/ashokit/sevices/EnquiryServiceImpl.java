package in.ashokit.sevices;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.ashokit.binding.DashboardResponse;
import in.ashokit.binding.EnquiryForm;
import in.ashokit.binding.EnquirySearchCriteria;
import in.ashokit.entiry.CourseEntity;
import in.ashokit.entiry.EnqStatusEntity;
import in.ashokit.entiry.StudentEnqEntity;
import in.ashokit.entiry.UserDtlsEntity;
import in.ashokit.repository.CourseRepo;
import in.ashokit.repository.EnqStatusRepo;
import in.ashokit.repository.StudentEnqRepo;
import in.ashokit.repository.UserDtlsRepo;

@Service
public class EnquiryServiceImpl implements EnquiryService {

	@Autowired
	private StudentEnqRepo studentEnqRepo;
	@Autowired
	private EnqStatusRepo enqStatusRepo;
	@Autowired
	private UserDtlsRepo userDtlsRepo;
	@Autowired
	private CourseRepo courseRepo;
	
	
	@Override
	public DashboardResponse dashboardData(Integer userId) {
		DashboardResponse dashboard = new DashboardResponse();
		Optional<UserDtlsEntity> findById = userDtlsRepo.findById(userId);
		
		if (findById.isPresent()) {
			
			UserDtlsEntity userDtlsEntity = findById.get();
			List<StudentEnqEntity> enquiries = userDtlsEntity.getEnquiries();
			int totalCount = enquiries.size();
		
			int enrolledCount = enquiries.stream()
			.filter(p->p.getEnquiryStatus().equals("Enrolled") )
			.collect(Collectors.toList()).size();
			
			int lostCount = enquiries.stream()
			.filter(p-> p.getEnquiryStatus().equals("lost"))
			.collect(Collectors.toList()).size();
			
			dashboard.setTotalEnquiries(totalCount);
			dashboard.setEnrolled(enrolledCount);
			dashboard.setLost(lostCount);
		}
		
		
		return dashboard;
	}

	@Override
	public List<String> getCourseNames() {
		
		List<CourseEntity> courseEntityList = courseRepo.findAll();
		
		List<String> name = new ArrayList<>();
		for (CourseEntity entity : courseEntityList) {
			
			name.add(entity.getCourseName());
		}
		
		return name;
	}

	@Override
	public List<String> getEnqStatus() {
		
		List<EnqStatusEntity> statusEntity = enqStatusRepo.findAll();
		List<String> status = new ArrayList<>();
		
		for (EnqStatusEntity entity : statusEntity) {
			
			status.add(entity.getStatusName());
		}
		return status;
	}

	@Override
	public String upsertEnquiry(EnquiryForm enquiryForm , Integer userId) {
		// TODO Auto-generated method stub
		StudentEnqEntity entity = new StudentEnqEntity();
		BeanUtils.copyProperties(enquiryForm, entity);	
		
		Optional<UserDtlsEntity> findById = userDtlsRepo.findById(userId);
		if (findById.isPresent()) {
			
			UserDtlsEntity userDtlsEntity = findById.get();
			entity.setUser(userDtlsEntity);
		}
		
		studentEnqRepo.save(entity);		
		
		return "Added Successfully";
	}

	@Override
	public List<StudentEnqEntity> getEnquriesBySearchCriteria(Integer userId, EnquirySearchCriteria searchCriteria) {
		Optional<UserDtlsEntity> findById = userDtlsRepo.findById(userId);
		if (findById.isPresent()) {
			UserDtlsEntity userDtlsEntity = findById.get();
			List<StudentEnqEntity> enquiries = userDtlsEntity.getEnquiries();
			
			// filter Logic
			if (searchCriteria.getCourse() != null & !"".equals(searchCriteria.getCourse())) {
				
				enquiries = enquiries.stream()
							.filter(p -> p.getCourseName().equals(searchCriteria.getCourse()))
							.collect(Collectors.toList());
			}
			
			if (searchCriteria.getClassMode() != null & !"".equals(searchCriteria.getClassMode())) {
				
				enquiries = enquiries.stream()
							.filter(p -> p.getClassMode().equals(searchCriteria.getClassMode()))
							.collect(Collectors.toList());
			}
			
			if (searchCriteria.getEnuiryStatus() != null & !"".equals(searchCriteria.getEnuiryStatus())) {
				
				enquiries = enquiries.stream()
							.filter(p -> p.getEnquiryStatus().equals(searchCriteria.getEnuiryStatus()))
							.collect(Collectors.toList());
			}
			
			return enquiries;
		}	
		return null;
	}

	@Override
	public EnquiryForm getEnquiry(Integer enqId) {
		
		EnquiryForm enqForm = new EnquiryForm();
		Optional<StudentEnqEntity> findById = studentEnqRepo.findById(enqId);
		if (findById.isPresent()) {
			StudentEnqEntity studentEnqEntity = findById.get();
			BeanUtils.copyProperties(studentEnqEntity, enqForm);
		}
		
		return enqForm;
	}

	@Override
	public List<StudentEnqEntity> getEnquries(Integer userId) {
		
		Optional<UserDtlsEntity> findById = userDtlsRepo.findById(userId);
		if (findById.isPresent()) {
			UserDtlsEntity userDtlsEntity = findById.get();
			List<StudentEnqEntity> enquiries = userDtlsEntity.getEnquiries();
			
			return enquiries;			
		}
				
		return null;
	}

	@Override
	public List<StudentEnqEntity> deleteEnquiries(Integer enqId) {
		
		
		Optional<StudentEnqEntity> findById = studentEnqRepo.findById(enqId);
		if (findById.isPresent()) {		
			studentEnqRepo.deleteById(enqId);
		}		
		List<StudentEnqEntity> findAll = studentEnqRepo.findAll();
		
		return findAll ;
	}
	
	

}
