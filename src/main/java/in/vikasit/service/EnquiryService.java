package in.vikasit.service;

import java.util.List;

import in.vikasit.binding.DashboardResponse;
import in.vikasit.binding.EnquiryForm;

import in.vikasit.entity.StudentEnqEntity;

public interface EnquiryService {
	
  public DashboardResponse getDashboardData(Integer userId);
  
  
    public List<String>getCourses();

    public List<String>getEnqStatuses();
    
    public boolean saveEnquriry(EnquiryForm form);


	public List<StudentEnqEntity> getEnquiries();
    
  
  
  
  
  
  
  
  
  
  
  
  //public String upsertEnquiry(EnquiryForm form);
  //public List<EnquiryForm>getEnquries(Integer userId,EnquirySearchCriteria criteria);
  
 // public EnquiryForm getEnquiry(Integer enqId);
} 
