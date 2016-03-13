package messaging.repository;


import messaging.entity.Message;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;

import java.util.UUID;

public interface MessageRepository extends CassandraRepository<Message> {

    @Query("SELECT*FROM messages WHERE userId=?0 LIMIT ?1")
    Iterable<Message> findByUser(String userId,Integer limit);

    @Query("SELECT*FROM messages WHERE userId=?0 AND id<?1 LIMIT ?2")
    Iterable<Message> findByUserFrom(String userId, UUID from, Integer limit);
}
