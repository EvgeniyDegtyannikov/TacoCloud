package tacos.web.forms;

import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;
import tacos.domain.User;

import javax.validation.constraints.NotBlank;

@Data
public class UserUpdateForm {
    @NotBlank(message = "Id is required")
    private String id;
    @NotBlank(message = "Username is required")
    private String username;

    public User toUser(PasswordEncoder passwordEncoder) {
        return new User(
                username,
                null
        );
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getId() {
        return id;
    }
}
