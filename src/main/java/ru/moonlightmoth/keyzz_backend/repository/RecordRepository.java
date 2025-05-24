package ru.moonlightmoth.keyzz_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.moonlightmoth.keyzz_backend.model.entity.Record;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public interface RecordRepository extends JpaRepository<Record, Long> {

    List<Record> findByTimestampGreaterThan(Timestamp timestamp);


}
