package in.vikasit.service;

import in.vikasit.binding.LoginForm;
import in.vikasit.binding.SignUpForm;
import in.vikasit.binding.UnlockForm;

public interface UserService {
	
	public boolean signup(SignUpForm form);
	public boolean unlockAccount(UnlockForm form); 
	
	
	public String login(LoginForm form);
	
	public boolean forgotPwd(String email);

}
