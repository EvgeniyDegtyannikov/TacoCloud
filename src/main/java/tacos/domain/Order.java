package tacos.domain;

import lombok.Data;
import org.hibernate.validator.constraints.CreditCardNumber;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "Taco_Order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date placedAt;

    @ManyToOne
    private User user;

    @CreditCardNumber(message = "Not a valid credit card number")
    private String ccNumber;
//    @Pattern(regexp = "^(0[1-9]|1[0-2])([/])([1-9][0-9])$",
//            message = "Must be formatted MM/YY")
//    private String ccExpiration;
//    @Digits(integer = 3, fraction = 0, message = "Invalid CVV")
//    private String ccCVV;

    @ManyToMany(targetEntity = Taco.class)
    private List<Taco> tacos = new ArrayList<>();

    @NotBlank(message = "Full name is required")
    private String fullname;
    @NotBlank(message = "Address is required")
    private String address;
    @NotBlank
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Must be 10 digits")
    private String phone;

    public void addDesign(Taco design) {
        this.tacos.add(design);
    }

    public Long getId() {
        return id;
    }

    public Date getPlacedAt() {
        return placedAt;
    }

    public User getUser() {
        return user;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCcNumber() {
        return ccNumber;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    //    public String getCcExpiration() {
//        return ccExpiration;
//    }
//
//    public String getCcCVV() {
//        return ccCVV;
//    }

    public List<Taco> getTacos() {
        return tacos;
    }

    @PrePersist
    public void placedAt() {
        this.placedAt = new Date();
    }
}
