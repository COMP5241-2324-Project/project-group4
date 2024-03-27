package polyu.group4.devreptilejava.entity;

import org.springframework.stereotype.Component;

@Component
public class GroupEntity {
    private String name;
    private Integer count;
    private MessageEntity latestMessage;

    private Integer commitsCount;

    private Integer commentsCount;

    private Integer issuesCount;

    private Integer pullsCount;

    private Integer score;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public MessageEntity getLatestMessage() {
        return latestMessage;
    }

    public void setLatestMessage(MessageEntity latestMessage) {
        this.latestMessage = latestMessage;
    }

    public GroupEntity() {
    }

    public GroupEntity(String name, Integer count, MessageEntity latestMessage) {
        this.name = name;
        this.count = count;
        this.latestMessage = latestMessage;
    }

    public Integer getCommitsCount() {
        return commitsCount;
    }

    public void setCommitsCount(Integer commitsCount) {
        this.commitsCount = commitsCount;
    }

    public Integer getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(Integer commentsCount) {
        this.commentsCount = commentsCount;
    }

    public Integer getIssuesCount() {
        return issuesCount;
    }

    public void setIssuesCount(Integer issuesCount) {
        this.issuesCount = issuesCount;
    }

    public Integer getPullsCount() {
        return pullsCount;
    }

    public void setPullsCount(Integer pullsCount) {
        this.pullsCount = pullsCount;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
