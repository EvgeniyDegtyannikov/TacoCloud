package tacos.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@RequiredArgsConstructor
@Table(name = "log_record")
public class LogRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;

    private String name;

    private Date placedAt;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getPlacedAt() {
        return placedAt;
    }

    public void setPlacedAt(Date placedAt) {
        this.placedAt = placedAt;
    }
}
