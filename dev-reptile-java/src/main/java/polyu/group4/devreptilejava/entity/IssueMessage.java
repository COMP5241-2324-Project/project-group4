package polyu.group4.devreptilejava.entity;


public class IssueMessage extends MessageEntity {
    private String title;
    private Integer comments;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getComments() {
        return comments;
    }

    public void setComments(Integer comments) {
        this.comments = comments;
    }
}
