package tacos.data;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import tacos.domain.Ingredient;

import java.util.List;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {
    @Query(value = "CALL find_all_ingredients();", nativeQuery = true)
    List<Ingredient> findAllIngredients();

    @Query(value = "CALL find_ingrs_for_taco(:iid);", nativeQuery = true)
    List<Ingredient> findIngrsForTaco(@Param("iid") Long id);
}
