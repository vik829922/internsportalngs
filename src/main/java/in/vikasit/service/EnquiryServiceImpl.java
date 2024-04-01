package in.vikasit.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.vikasit.binding.DashboardResponse;
import in.vikasit.binding.EnquiryForm;
import in.vikasit.entity.CourseEntity;
import in.vikasit.entity.EnqStatusEntity;
import in.vikasit.entity.StudentEnqEntity;
import in.vikasit.entity.UserDtlsEntity;
import in.vikasit.repo.CourseRepo;
import in.vikasit.repo.EnqStatusRepo;
import in.vikasit.repo.StudentEnqRepo;
import in.vikasit.repo.UserDtlsRepo;
@Service
public class EnquiryServiceImpl implements EnquiryService {
	@Autowired
	private UserDtlsRepo userDtlsRepo;
	@Autowired
	private StudentEnqRepo enqRepo;
	@Autowired
	private CourseRepo coursesRepo;
	@Autowired
	private EnqStatusRepo statusRepo;
	@Autowired
	private HttpSession session;
	
	
	@Override
	public DashboardResponse getDashboardData(Integer userId) {
		DashboardResponse response = new DashboardResponse();
	 
		Optional<UserDtlsEntity> findById=userDtlsRepo.findById(userId);
		if(findById.isPresent()) {
			UserDtlsEntity userEntity=findById.get();
			List<StudentEnqEntity>enquiries=userEntity.getEnquiries();
			Integer totalCnt=enquiries.size();
			Integer enrolledCnt=enquiries.stream()
			         .filter(e-> e.getEnqStatus().equals("Enrolled"))
			         .collect(Collectors.toList()).size();
			        
			
			Integer lostCnt =enquiries.stream()
			 .filter(e -> e.getEnqStatus().equals("Lost`6"))
	         .collect(Collectors.toList()).size();     
			
			response.setTotalEnquriesCnt(totalCnt);
			response.setEnrolledCnt(enrolledCnt);
			response.setLostCnt(lostCnt);
			
			
		}
		
		return response ;
	}


	@Override
	public List<String> getCourses() {
		
		List<CourseEntity> findAll=coursesRepo.findAll();
		List<String> names = new ArrayList();
	    for(CourseEntity entity : findAll) {
			names.add(entity.getCourseName());
		}
		// TODO Auto-generated method stub
		return names;
	}


	//@Override
	public List<String> getEnqStatuses() {
		
		List<EnqStatusEntity> findAll= statusRepo.findAll();
		List<String> statusList =new ArrayList<>();
		for (EnqStatusEntity entity : findAll) {
			statusList.add(entity.getStatusName());   
		}
		// TODO Auto-generated method stub
		return statusList;
	}


	@Override
	public boolean saveEnquriry(EnquiryForm form) {
		// TODO Auto-generated method stub
		StudentEnqEntity enqEntity = new StudentEnqEntity();
		BeanUtils.copyProperties(form, enqEntity);
		
		Integer userId =(Integer)session.getAttribute("userId");
		UserDtlsEntity userEntity = userDtlsRepo.findById(userId).get();
		
		
		enqEntity.setUser(userEntity);
		
		enqRepo.save(enqEntity);
		return true;
	}


	@Override
	
     public List<StudentEnqEntity>getEnquiries(){
		Integer userId=(Integer) session.getAttribute("userId");
		Optional<UserDtlsEntity>findById=userDtlsRepo.findById(userId);
		if(findById.isPresent()) {
			UserDtlsEntity userDtlsEntity=findById.get();
			List<StudentEnqEntity> enquiries =userDtlsEntity.getEnquiries();
			return enquiries;
		}
	     return null; 	
	}
}	
	


