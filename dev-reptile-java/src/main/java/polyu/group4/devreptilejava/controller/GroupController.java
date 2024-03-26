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

    @GetMapping("/commits")
    public String getGroupCommits(){
        return groupService.getGroupCommits();
    }

    @GetMapping("/test")
    public void getAllBranchUrls(){
        groupService.test();
    }
}
