package tacos.data;

import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import tacos.domain.LogRecord;

import java.util.Date;

public interface LogRecordRepository extends CrudRepository<LogRecord, Long> {
    @Procedure(procedureName = "log_action")
    void logAction(Date createdAt, String name, String userName);
}
