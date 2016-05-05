package messaging.entity;


import com.datastax.driver.core.utils.UUIDs;
import org.springframework.cassandra.core.PrimaryKeyType;
import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.mapping.Table;

import java.util.Date;
import java.util.UUID;


@Table(value = "messages")
public class Message {

    @PrimaryKeyColumn(name = "id", ordinal = 1, type = PrimaryKeyType.CLUSTERED)
    private UUID id = UUIDs.timeBased();

    @PrimaryKeyColumn(name = "userId", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Column(value = "content")
    private String content;

    @Column(value = "creation_date")
    private Date creationDate;

}
