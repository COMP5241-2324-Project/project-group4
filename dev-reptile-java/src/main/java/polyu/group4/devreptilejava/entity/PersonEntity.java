package polyu.group4.devreptilejava.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class PersonEntity {
    private String name;
    private Integer commitsCount;
    private List<MessageEntity> messages;

    public PersonEntity() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCommitsCount() {
        return commitsCount;
    }

    public void setCommitsCount(Integer commitsCount) {
        this.commitsCount = commitsCount;
    }

    public List<MessageEntity> getMessages() {
        return messages;
    }

    public void setMessages(List<MessageEntity> messages) {
        this.messages = messages;
    }

    public PersonEntity(String name, Integer commitsCount, List<MessageEntity> messages) {
        this.name = name;
        this.commitsCount = commitsCount;
        this.messages = messages;
    }
}
