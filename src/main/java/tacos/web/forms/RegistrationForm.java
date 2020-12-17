package tacos.web.forms;

import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;
import tacos.domain.User;

import javax.validation.constraints.NotBlank;

@Data
public class RegistrationForm {

    @NotBlank(message = "Username is required")
    private String username;
    @NotBlank(message = "Password is required")
    private String password;

    public User toUser(PasswordEncoder passwordEncoder) {
        return new User(
                username,
                passwordEncoder.encode(password)
        );
    }
}
