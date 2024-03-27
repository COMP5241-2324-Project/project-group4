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
    private List<MessageEntity> commits;

    private Integer commentsCount;
    private List<MessageEntity> comments;

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

    public List<MessageEntity> getCommits() {
        return commits;
    }

    public void setCommits(List<MessageEntity> commits) {
        this.commits = commits;
    }

    public Integer getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(Integer commentsCount) {
        this.commentsCount = commentsCount;
    }

    public List<MessageEntity> getComments() {
        return comments;
    }

    public void setComments(List<MessageEntity> comments) {
        this.comments = comments;
    }

    public PersonEntity(String name, Integer commitsCount, List<MessageEntity> commits) {
        this.name = name;
        this.commitsCount = commitsCount;
        this.commits = commits;
    }

    public PersonEntity(String name, Integer commitsCount, List<MessageEntity> commits, List<MessageEntity> comments) {
        this.name = name;
        this.commitsCount = commitsCount;
        this.commits = commits;
        this.comments = comments;
    }
}
