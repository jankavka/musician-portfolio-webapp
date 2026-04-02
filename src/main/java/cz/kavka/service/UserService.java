package cz.kavka.service;

import cz.kavka.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    void createUser(UserDto userDto);

    void changePassword(Long id, UserDto userDto);

    void handleForgottenPassword(String username);

    UserDto getCurrentUser();

    void handleLogout();


}
