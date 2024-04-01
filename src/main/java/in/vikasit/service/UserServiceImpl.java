 package in.vikasit.service;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.vikasit.binding.LoginForm;
import in.vikasit.binding.SignUpForm;
import in.vikasit.binding.UnlockForm;
import in.vikasit.entity.UserDtlsEntity;
import in.vikasit.repo.UserDtlsRepo;
import in.vikasit.util.EmailUtils;
import in.vikasit.util.PwdUtils;
@Service
public  class UserServiceImpl implements UserService {
	@Autowired
	private UserDtlsRepo UserDtlsRepo;
	@Autowired
	private EmailUtils emailUtils;
	@Autowired
	private HttpSession session;
	
	
	@Override
	public boolean unlockAccount(UnlockForm form) {
		
		UserDtlsEntity entity =UserDtlsRepo.findByEmail(form.getEmail());
		if(entity.getPwd().equals(form.getTempPwd())) {
			entity.setPwd(form.getNewPwd());
			entity.setAccStatus("UNLOCKED");
			UserDtlsRepo.save(entity);
			return true;
		}else {
			return false;
		}
		}
		
	
	
	
	@Override
	public boolean signup(SignUpForm form) {
		
		UserDtlsEntity user = UserDtlsRepo.findByEmail(form.getEmail());
		if (user!=null) {
			  
			return false;
		}
		UserDtlsEntity entity = new UserDtlsEntity();
		BeanUtils.copyProperties(form, entity);
		
		String tempPwd=PwdUtils.generateRandompwd();
		entity.setPwd(tempPwd);
		entity.setAccStatus("LOCKED");
		UserDtlsRepo.save(entity);
		String to= form.getEmail();
		String subject ="UNLOCK YOUR ACCOUNT  --Neurogaint Systems";
		StringBuffer body = new StringBuffer("");
		body.append("<h1> Use Below Password To Unlock Your Account</h1>");
		body.append("Temporory pwd :"+ tempPwd);
		body.append("<br/>");
		body.append("<br/>");
		
		body.append("<a href=\"http://localhost:9090/unlock?email=" + to + "\">Click Here To Unlock Your Account</a>");
		
		emailUtils.sendEmail(to, subject, body.toString());
		
		return false;
	}
	@Override
	public String login(LoginForm form) {
		
		UserDtlsEntity entity =UserDtlsRepo.findByEmailAndPwd(form.getEmail(), form.getPwd());
		 if(entity== null) {
			 return "Invalid Credentials";
		 }
		 if(entity.getAccStatus().equals("LOCKED")) {
			 return "Your Account Locked";
		 }
		 
		 //session management
		 session.setAttribute("userId", entity.getUserId());
		 
		 
		 return "success";
	}
	
	
	@Override
	public boolean forgotPwd(String email) {
		UserDtlsEntity entity=UserDtlsRepo.findByEmail(email);
		if(entity == null) {
			return false;
		}
			String Subject="Recover Paassword";
			String Body="Your Pwd :: "+ entity.getPwd();
			
			 
		emailUtils.sendEmail(email,Subject,Body);
		return true;
	}

}