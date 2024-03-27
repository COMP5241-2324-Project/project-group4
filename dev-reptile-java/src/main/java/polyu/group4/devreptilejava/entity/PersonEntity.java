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

    private Integer issuesCount;
    private List<IssueMessage> issues;

    private Integer pullsCount;

    private List<PullMessage> pulls;

    private Integer score;

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

    public List<IssueMessage> getIssues() {
        return issues;
    }

    public void setIssues(List<IssueMessage> issues) {
        this.issues = issues;
    }

    public List<PullMessage> getPulls() {
        return pulls;
    }

    public void setPulls(List<PullMessage> pulls) {
        this.pulls = pulls;
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
