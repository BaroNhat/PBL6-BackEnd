package PBL6.example.UNIME.service;

import PBL6.example.UNIME.entity.Appointment;

public interface MailService {
    public String sendOtp(String mail);
    public String sendPasswork(String mail);
    public void sendCancelEmail(Appointment appointments);
    public void sendSuccessEmail(Appointment appointments);

}
