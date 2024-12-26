package PBL6.example.UNIME.service;

import PBL6.example.UNIME.dto.request.PasswordRequest;
import PBL6.example.UNIME.entity.User;

public interface UserService {
    /**
     * Creates a new user in the system.
     *
     * @param request the {@code User} object containing the user's details.
     * @return the created {@code User} object.
     */
    public User createUser(User request);

    /**
     * Updates the details of an existing user.
     *
     * @param userId the ID of the user to update.
     * @param request the {@code User} object containing the updated user details.
     */
    public void updateUser(Integer userId, User request);

    /**
     * Updates the password for a user identified by the username.
     * Verifies the old password before updating to the new password.
     *
     * @param username the username of the user
     * @param passwordRequest contains the old and new passwords
     * @return a success message if the update is successful
     */
    public String saveNewPassword(String username, PasswordRequest passwordRequest);

    /**
     * Saves a new encrypted password for the given user.
     *
     * @param user the User entity whose password is to be updated
     * @param newPass the new password to set
     */
    public void saveNewPassword(User user, String newPass);

    /**
     * Blocks the specified user account by setting its status to "LOCKED"
     *
     * @param user the {@code User} to delete.
     */
    public User changeUserStatus(User user);

    /**
     * Checks if a user exists in the system by their email.
     *
     * @param email the email address to check.
     * @return {@code true} if a user with the given email exists, {@code false} otherwise.
     */
    public boolean ExitByEmail(String email);

    /**
     * Retrieves a user from the system by their email.
     *
     * @param email the email address of the user.
     * @return the {@code User} object corresponding to the given email.
     */
    public User getUserByEmail(String email);

    /**
     * Retrieves a user from the system by their username.
     *
     * @param username the username of the user.
     * @return the {@code User} object corresponding to the given username.
     */
    public User getUserByUsername(String username);

}
