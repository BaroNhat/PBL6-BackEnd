package PBL6.example.UNIME.service;

import PBL6.example.UNIME.entity.User;
import com.resend.Resend;
import com.resend.core.exception.ResendException;
import com.resend.services.emails.model.CreateEmailOptions;
import com.resend.services.emails.model.CreateEmailResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailService {

    private final UserServiceImpl userService;

    public String sendOtp(String mail){
        Resend resend = new Resend("re_UwUSKQNg_EMC7CsQgrUDzsMzcZwXQfBmx");
        String randomOtp = generateNum(4);
        CreateEmailOptions params = CreateEmailOptions.builder()
                .from("UNIME <hospital@unime.site>")
                .to(mail)
                .subject("Receive OTP from Unime Hospital ")
                .html("<strong>Your OTP is: " + randomOtp + "</strong>")
                .build();
        try {
            CreateEmailResponse data = resend.emails().send(params);
        } catch (ResendException e) {
            e.printStackTrace();
        }
        return randomOtp;
    }

    public String sendPasswork(String mail){
        Resend resend = new Resend("re_UwUSKQNg_EMC7CsQgrUDzsMzcZwXQfBmx");
        String newPasswork = generateNum(8);
        CreateEmailOptions params = CreateEmailOptions.builder()
                .from("UNIME <hospital@unime.site>")
                .to(mail)
                .subject("Receive new passwork from Unime Hospital ")
                .html("<strong>Your passwork is: " + newPasswork + "</strong>")
                .build();
        User user = userService.getUserByEmail(mail);
        try {
            CreateEmailResponse data = resend.emails().send(params);
        } catch (ResendException e) {
            e.printStackTrace();
        }
        userService.saveNewPassword(user, newPasswork);
        return "New passwword: " +newPasswork;
    }

    private String generateNum(int digitCount) {

        SecureRandom random = new SecureRandom();
        int minValue = (int) Math.pow(10, digitCount - 1);
        int maxValue = (int) Math.pow(10, digitCount) - 1;
        int otp = minValue + random.nextInt(maxValue - minValue + 1);

        return String.valueOf(otp);
    }

}
