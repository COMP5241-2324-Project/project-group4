package polyu.group4.devreptilejava.service;


import java.util.List;

public interface GroupService {
    String getGroupCommits();

    String getGroupComments();

    String getGroupIssues();
    String getGroupPulls();
    String getGroupMilestones();

    String getALlGroups();

    String getGroupScore();
}
