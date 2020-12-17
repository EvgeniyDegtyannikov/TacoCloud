package tacos.data;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import tacos.domain.Taco;

import java.util.Date;
import java.util.List;

public interface TacoRepository extends CrudRepository<Taco, Long> {
    @Query(value = "CALL save_taco(:createdAt, :name);", nativeQuery = true)
    Taco saveTaco(@Param("createdAt") Date createdAt, @Param("name") String name);

    @Procedure(procedureName = "save_taco_ingredient")
    void saveTacoIngredient(Long tacoId, String ingredientsId);

    @Query(value = "CALL find_tacos_for_order(:oid);", nativeQuery = true)
    List<Taco> findTacosForOrder(@Param("oid") Long id);

    @Query(value = "CALL get_all_tacos();", nativeQuery = true)
    List<Taco> getAllTacos();
}
