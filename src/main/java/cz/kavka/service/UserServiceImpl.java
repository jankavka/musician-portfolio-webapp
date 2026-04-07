package cz.kavka.service;

import cz.kavka.constant.role.Role;
import cz.kavka.dto.UserDto;
import cz.kavka.entity.UserEntity;
import cz.kavka.entity.repository.UserRepository;
import cz.kavka.service.exception.NoCurrentUserException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

import static cz.kavka.service.exception.message.ExceptionMessage.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final MailSender mailSender;

    private final SimpleMailMessage templateMessage;

    private UserService userService;

    private final Random random = new Random();


    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username.isEmpty()) {
            throw new UsernameNotFoundException(USER_NOT_FOUND);
        }
        UserEntity user = userRepository
                .findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND));


        if (user == null) {
            log.error(USER_NOT_FOUND);
            throw new UsernameNotFoundException(USER_NOT_FOUND);
        } else {

            return userRepository.findByEmail(username).orElseThrow(EntityNotFoundException::new);
        }

    }

    @Transactional
    @Override
    public void createUser(UserDto userDto) {
        var userEntityToSave = new UserEntity();
        userEntityToSave.setEmail(userDto.email());
        userEntityToSave.setRole(Role.USER);
        userEntityToSave.setPassword(passwordEncoder.encode(userDto.password()));
        userRepository.save(userEntityToSave);

    }

    @Transactional
    @Override
    public void changePassword(Long id, UserDto userDto) {
        var userToEdit = userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        userToEdit.setPassword(passwordEncoder.encode(userDto.password()));


    }

    @Transactional
    @Override
    public void handleForgottenPassword(String username) {
        var user = userRepository.findByEmail(username).orElseThrow(
                () -> new UsernameNotFoundException(USER_NOT_FOUND));
        if (user == null) {
            throw new UsernameNotFoundException(USER_NOT_FOUND);
        }

        var generatedPassword = generatePassword();

        user.setPassword(passwordEncoder.encode(generatedPassword));

        SimpleMailMessage message = new SimpleMailMessage(templateMessage);
        message.setTo(user.getEmail());
        message.setText("Resetované heslo pro uživatele: " + user.getEmail() + " je: " + generatedPassword);

        mailSender.send(message);


    }

    @Transactional(readOnly = true)
    @Override
    public UserDto getCurrentUser() {
        UserEntity currentUser;
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() != null) {
            currentUser = (UserEntity) authentication.getPrincipal();
            if (currentUser != null) {
                return new UserDto(currentUser.getId(), currentUser.getUsername(), "===SECRET===", null);
            } else {
                throw new EntityNotFoundException();
            }
        } else {
            throw new NoCurrentUserException("Žádný uživatel není přihlášený");
        }

    }

    @Transactional(readOnly = true)
    @Override
    public void handleLogout() {
        if(userService.getCurrentUser() != null) {
            SecurityContextHolder.clearContext();
        }
    }

    private String generatePassword() {
        // numeral '0'
        int leftLimit = 48;
        // letter 'z'
        int rightLimit = 122;

        int targetStringLength = 10;

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }


}
