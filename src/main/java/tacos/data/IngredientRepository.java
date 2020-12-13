package tacos.data;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import tacos.domain.Ingredient;

import java.util.List;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {
    @Query(value = "CALL find_all_ingredients();", nativeQuery = true)
    List<Ingredient> findAllIngredients();
}
