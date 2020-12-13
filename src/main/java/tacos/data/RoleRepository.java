package tacos.data;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import tacos.domain.Role;

import java.util.List;

public interface RoleRepository extends CrudRepository<Role, Long> {
    @Procedure(procedureName = "add_role")
    void addRole(Long rid, Long uid);

    @Query(value = "CALL get_role_by_name(:name);", nativeQuery = true)
    Role findByName(@Param("name") String name);

    @Query(value = "CALL find_roles_for_user(:uid);", nativeQuery = true)
    List<Role> findRolesForUser(@Param("uid") Long id);
}
