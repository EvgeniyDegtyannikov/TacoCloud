package tacos.data;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import tacos.domain.User;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {
    @Procedure(procedureName = "save_user")
    void saveUser(String username, String password);

    @Procedure(procedureName = "update_user")
    void updateUser(Long id, String username);

    @Query(value = "CALL find_user_by_username(:username);", nativeQuery = true)
    User findByUsername(@Param("username") String name);

    @Query(value = "CALL find_user_by_id(:id);", nativeQuery = true)
    User findByStringId(@Param("id") String id);

    @Query(value = "CALL get_all_users();", nativeQuery = true)
    List<User> getAllUsers();
}
