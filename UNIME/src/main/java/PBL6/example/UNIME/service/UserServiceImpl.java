package PBL6.example.UNIME.service;

import PBL6.example.UNIME.dto.request.PasswordRequest;
import PBL6.example.UNIME.entity.User;
import PBL6.example.UNIME.exception.AppException;
import PBL6.example.UNIME.exception.ErrorCode;
import PBL6.example.UNIME.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level =  AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;

    @Override
    public User createUser( User request) {
        if(userRepository.existsByusername(request.getUsername())) {
            throw new AppException(ErrorCode.USERNAME_ALREADY_TAKEN);
        }
        if(userRepository.existsByemail(request.getEmail())){
            throw new AppException(ErrorCode.EMAIL_ALREADY_REGISTERED);
        }
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setRole(request.getRole());
        return userRepository.save(user);
    }

    @Override
    public void updateUser(Integer userId, User request) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXITED));
        if( userRepository.existsByemail(request.getEmail())){
            User userByEmail = getUserByEmail(request.getEmail());
            log.info(" === id:{} với id:{} ", userId, userByEmail.getUserId());
            if(!userByEmail.getUserId().equals(userId)) {
                throw new AppException(ErrorCode.EMAIL_ALREADY_REGISTERED);
            }
        }
        user.setEmail(request.getEmail());
        user.setStatus(request.getStatus());
        userRepository.save(user);
    }

    @Override
    public String saveNewPassword(String username, PasswordRequest passwordRequest) {
        User user = userRepository.findByusername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXITED));

        if(!verifyPassword(passwordRequest.getOldPassword(), user.getPassword())) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        saveNewPassword(user, passwordRequest.getNewPassword());
        return "Thành công";
    }

    @Override
    public void saveNewPassword(User user, String newPass) {
        user.setPassword(passwordEncoder.encode(newPass));
        userRepository.save(user);
    }

    @Override
    public User changeUserStatus(User user) {
        if(user.getStatus().equals("LOCKED")) {
            user.setStatus("ACTIVE");
        } else user.setStatus("LOCKED");
        return userRepository.save(user);
    }

    @Override
    public boolean ExitByEmail(String email){
        return userRepository.existsByemail(email);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByemail(email)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXITED));
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByusername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXITED));
    }

    public boolean verifyPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
