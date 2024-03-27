package polyu.group4.devreptilejava.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import polyu.group4.devreptilejava.service.GroupService;

@RestController
@RequestMapping("/api/groups")
public class GroupController {
    @Autowired
    private GroupService groupService;

    @GetMapping("/issues")
    public String getGroupIssues(){return groupService.getGroupIssues(); }
    @GetMapping("/pulls")
    public String getGroupPulls(){return groupService.getGroupPulls(); }
    @GetMapping("/milestones")
    public String getGroupMilestones(){return groupService.getGroupMilestones(); }



    @GetMapping("/commits")
    // 返回小组成员的commits
    public String getGroupCommits(){
        return groupService.getGroupCommits();
    }

    @GetMapping("/comments")
    // 返回小组成员的comments
    public String getGroupComments(){return groupService.getGroupComments();}


    // 获得所有小组的信息，对应首页
    @GetMapping("/all")
    public String getAllGroup(){
        return groupService.getALlGroups();
    }

    // 获得小组各个成员的得分情况
    @GetMapping("/score")
    public String getGroupScore(){
        return groupService.getGroupScore();
    }
}