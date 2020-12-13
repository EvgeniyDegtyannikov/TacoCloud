//package tacos.data.jdbc;
//
//import org.springframework.jdbc.core.JdbcTemplate;
//import tacos.domain.Ingredient;
//
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
////@Repository
//public class JdbcIngredientRepository {
//    private JdbcTemplate jdbc;
//
//    //    @Autowired
//    public JdbcIngredientRepository(JdbcTemplate jdbc) {
//        this.jdbc = jdbc;
//    }
//
//    //    @Override
//    public Iterable<Ingredient> findAll() {
//        return jdbc.query("select id, name, type from Ingredient", this::mapRowToIngredient);
//    }
//
//    //    @Override
//    public Ingredient findOne(String id) {
//        return jdbc.queryForObject("select id, name, type from Ingredient where id=?", this::mapRowToIngredient, id);
//    }
//
//    private Ingredient mapRowToIngredient(ResultSet set, int rowNum) throws SQLException {
//        return new Ingredient(
//                set.getString("id"),
//                set.getString("name"),
//                Ingredient.Type.valueOf(set.getString("type"))
//        );
//    }
//
//    //    @Override
//    public Ingredient save(Ingredient ingredient) {
//        jdbc.update(
//                "insert into Ingredient (id, name, type) values (?,?,?)",
//                ingredient.getId(),
//                ingredient.getName(),
//                ingredient.getType().toString().toLowerCase()
//        );
//        return ingredient;
//    }
//}
