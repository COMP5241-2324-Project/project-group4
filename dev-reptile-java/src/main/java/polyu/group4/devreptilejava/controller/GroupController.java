package polyu.group4.devreptilejava.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import polyu.group4.devreptilejava.service.impl.GroupService;

@RestController
@RequestMapping("/api/groups")
public class GroupController {
    @Autowired
    private GroupService groupService;

//    第三步，模仿下面的两行写两行
    @GetMapping("/commits")
    public String getGroupCommits(){
        return groupService.getGroupCommits();
    }
    @GetMapping("/issues")
    public String getGroupIssues(){return groupService.getGroupIssues(); }
    @GetMapping("/pulls")
    public String getGroupPulls(){return groupService.getGroupPulls(); }

}
