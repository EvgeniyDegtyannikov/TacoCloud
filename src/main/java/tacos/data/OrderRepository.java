package tacos.data;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import tacos.domain.Order;

import java.util.Date;
import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Long> {
    @Query(value = "CALL save_order(:placedAt, :ccNumber, :address, :fullname, :phone, :status, :userId);", nativeQuery = true)
    Order saveOrder(@Param("placedAt") Date placedAt, @Param("ccNumber") String ccNumber,
                    @Param("address") String address, @Param("fullname") String fullname, @Param("phone") String phone,
                    @Param("status") String status, @Param("userId") Long userId);

    @Procedure(procedureName = "save_order_tacos")
    void saveOrderTacos(Long tacoId, Long orderId);

    @Procedure(procedureName = "update_order_status")
    void updateOrderStatus(String status, Long orderId);

    @Query(value = "CALL get_all_orders();", nativeQuery = true)
    List<Order> getAllOrders();

    @Query(value = "CALL get_orders_for_user(:uid);", nativeQuery = true)
    List<Order> getOrdersForUser(@Param("uid") Long id);
}
