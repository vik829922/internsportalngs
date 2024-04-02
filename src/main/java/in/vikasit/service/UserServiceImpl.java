package in.vikasit.service;

import java.net.InetAddress;
import java.net.UnknownHostException;

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
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDtlsRepo UserDtlsRepo;
    @Autowired
    private EmailUtils emailUtils;
    @Autowired
    private HttpSession session;

    @Override
    public boolean unlockAccount(UnlockForm form) {

        UserDtlsEntity entity = UserDtlsRepo.findByEmail(form.getEmail());
        if (entity.getPwd().equals(form.getTempPwd())) {
            entity.setPwd(form.getNewPwd());
            entity.setAccStatus("UNLOCKED");
            UserDtlsRepo.save(entity);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean signup(SignUpForm form) {

        UserDtlsEntity user = UserDtlsRepo.findByEmail(form.getEmail());
        if (user != null) {

            return false;
        }
        UserDtlsEntity entity = new UserDtlsEntity();
        BeanUtils.copyProperties(form, entity);

        String tempPwd = PwdUtils.generateRandompwd();
        entity.setPwd(tempPwd);
        entity.setAccStatus("LOCKED");
        UserDtlsRepo.save(entity);

        // Get the public IP address of the AWS instance
        String publicIPAddress = getPublicIPAddress();

        // Construct the link with the public IP address
        String unlockLink = "http://" + publicIPAddress + "/unlock?email=" + form.getEmail();

        String to = form.getEmail();
        String subject = "UNLOCK YOUR ACCOUNT  --Neurogaint Systems";
        StringBuffer body = new StringBuffer("");
        body.append("<h1> Use Below Password To Unlock Your Account</h1>");
        body.append("Temporary pwd: " + tempPwd);
        body.append("<br/><br/>");
        body.append("<a href=\"" + unlockLink + "\">Click Here To Unlock Your Account</a>");

        emailUtils.sendEmail(to, subject, body.toString());

        return true;
    }

    @Override
    public String login(LoginForm form) {

        UserDtlsEntity entity = UserDtlsRepo.findByEmailAndPwd(form.getEmail(), form.getPwd());
        if (entity == null) {
            return "Invalid Credentials";
        }
        if (entity.getAccStatus().equals("LOCKED")) {
            return "Your Account Locked";
        }

        // Session management
        session.setAttribute("userId", entity.getUserId());

        return "success";
    }

    @Override
    public boolean forgotPwd(String email) {
        UserDtlsEntity entity = UserDtlsRepo.findByEmail(email);
        if (entity == null) {
            return false;
        }
        String subject = "Recover Password";
        String body = "Your Password: " + entity.getPwd();

        emailUtils.sendEmail(email, subject, body);
        return true;
    }

    // Method to get the public IP address of the AWS instance
    private String getPublicIPAddress() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            // Handle the exception accordingly
        }
        return null;
    }
}
