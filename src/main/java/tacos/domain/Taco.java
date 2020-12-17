package tacos.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "Taco")
public class Taco {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date createdAt;

    @NotNull(message = "Enter the qty")
    @Min(value = 1, message = "The qty must be greater than 0")
    @Max(value = 99, message = "The qty must be lesser than 100")
    private Integer qty;

    @NotNull
    @Size(min = 5, message = "Name must be at least 5 characters long")
    private String name;

    @NotNull(message = "You must choose at least 1 ingredient")
    @ManyToMany(targetEntity = Ingredient.class)
    @Size(min = 1, message = "You must choose at least 1 ingredient")
    private List<Ingredient> ingredients;

    public Long getId() {
        return id;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public String getName() {
        return name;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    @PrePersist
    public void createdAt() {
        this.createdAt = new Date();
    }
}
