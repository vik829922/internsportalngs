package in.vikasit.runners;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import in.vikasit.entity.CourseEntity;
import in.vikasit.entity.EnqStatusEntity;
import in.vikasit.repo.CourseRepo;
import in.vikasit.repo.EnqStatusRepo;

@Component
public class DataLoader implements ApplicationRunner{
	@Autowired
	private CourseRepo courseRepo;
	@Autowired
	private EnqStatusRepo statusRepo;
	@Override
	public void run(ApplicationArguments args)throws Exception{
		courseRepo.deleteAll();
		
		CourseEntity c1 = new CourseEntity();
		c1.setCourseName("java");
		
		CourseEntity c2 = new CourseEntity();
		c2.setCourseName("Python");
		
		CourseEntity c3 = new CourseEntity();
		c3.setCourseName("AWS");
		
		CourseEntity c4 = new CourseEntity();
		c4.setCourseName("Devops");
		
		CourseEntity c5 = new CourseEntity();
		c5.setCourseName(".Net");
		
		courseRepo.saveAll(Arrays.asList(c1,c2,c3,c4,c5));
		
		statusRepo.deleteAll();
		
		EnqStatusEntity s1=new EnqStatusEntity();
		s1.setStatusName("new");
		
		EnqStatusEntity s2=new EnqStatusEntity();
		s2.setStatusName("Enrolled");
		
		EnqStatusEntity s3=new EnqStatusEntity();
		s3.setStatusName("Lost");
		statusRepo.saveAll(Arrays.asList(s1,s2,s3));
		
	}
    
}
