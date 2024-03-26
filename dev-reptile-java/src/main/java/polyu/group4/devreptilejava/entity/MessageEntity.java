package polyu.group4.devreptilejava.entity;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class MessageEntity {
    private String message;
    private Date date;

    public MessageEntity() {
    }

    public MessageEntity(String message, Date date) {
        this.message = message;
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
