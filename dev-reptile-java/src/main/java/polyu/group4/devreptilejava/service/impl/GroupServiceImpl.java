package polyu.group4.devreptilejava.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import polyu.group4.devreptilejava.entity.*;
import polyu.group4.devreptilejava.service.GroupService;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class GroupServiceImpl implements GroupService {
    // 替换GitHub 的组织名，令牌，仓库
    private String orgName = "COMP5241-2324-Project";
    private String reposName = "project-group4";
    private String url = "https://api.github.com/repos/" + orgName + "/" + reposName;
    //    88888 milestones
    private String url8 = "https://api.github.com/repos/" + orgName + "/" + reposName + "/milestones";

    @Autowired
    private GroupEntity myGroup;

    @Autowired
    private Map<String, PersonEntity> groupNumbers;

    @Override
    public String getGroupCommits() {
        String commitsJson = null;
        List<String> branchName = getBranchNames();
        // 创建一个Map来存储人名和对应的entity
        Map<String, PersonEntity> commitPersons = new HashMap<String, PersonEntity>();
        // 创建一个ObjectMapper来解析JSON数据

        Integer allCommits = 0;
        ObjectMapper mapper = new ObjectMapper();
        for (String name : branchName) {
            String branchUrl = "https://api.github.com/repos/" + orgName + "/" + reposName + "/commits?sha=" + name;
            try (CloseableHttpClient client = HttpClients.createDefault()) {
                // 创建一个HttpGet请求，用于获取仓库的提交信息
                HttpGet request = new HttpGet(branchUrl);
                try (CloseableHttpResponse response = client.execute(request)) {
                    // 将响应体转换为字符串
                    String responseBody = EntityUtils.toString(response.getEntity());
                    // 将响应体解析为一个列表，每个元素包含一个提交信息的Map
                    List<Map<String, Object>> commits = mapper.readValue(responseBody, List.class);
                    // 创建一个List来存储多个PersonEntity
                    for (Map<String, Object> commit : commits) {
                        Map<String, Object> commitAuthor = (Map<String, Object>) commit.get("commit");
                        if (commitAuthor != null) {
                            Map<String, Object> author = (Map<String, Object>) commitAuthor.get("author");
                            String authorName = (String) author.get("name");
                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                            Date commitDate = formatter.parse((String) author.get("date"));
                            String message = (String) commitAuthor.get("message");

                            // 将活动信息和活动时间放入Entity中
                            MessageEntity newCommit = new MessageEntity(message, commitDate);

                            // 判断当前Map中是否已经有了该author
                            PersonEntity person = commitPersons.get(authorName);
                            if (!"github-classroom[bot]".equals(authorName)) {
                                allCommits++;
                                // 如果已经有了该author，那么则仅需要更新message列表，和增加commit次数
                                if (null != person) {
                                    person.getCommits().add(newCommit);
                                    person.setCommitsCount(person.getCommitsCount() + 1);
                                } else {
                                    // 如果没有该author，那么则需要在Map中放入
                                    List<MessageEntity> messageList = new ArrayList<MessageEntity>();
                                    messageList.add(newCommit);
                                    PersonEntity newPerson = new PersonEntity(authorName, 1, messageList);
                                    commitPersons.put(authorName, newPerson);
                                }
                            }
                        }
                    }
                    commitsJson = mapper.writeValueAsString(commitPersons);

                    for (Map.Entry<String, PersonEntity> entry : commitPersons.entrySet()) {
                        String id = entry.getKey();
                        PersonEntity value = entry.getValue();
                        PersonEntity person = groupNumbers.get(id);
                        if (person == null) {
                            PersonEntity newPerson = new PersonEntity();
                            newPerson.setCommitsCount(value.getCommitsCount());
                            groupNumbers.put(id, newPerson);
                        } else {
                            person.setCommitsCount(value.getCommitsCount());
                            groupNumbers.put(id, person);
                        }
                    }
                }
            } catch (IOException | ParseException e) {
                throw new RuntimeException(e);
            }
        }
        myGroup.setCommitsCount(allCommits);
        return commitsJson;
    }

    @Override
    public String getGroupComments() {
        String commitsJson = null;
        // 创建一个Map来存储人名和对应的entity
        Map<String, PersonEntity> commentPerson = new HashMap<String, PersonEntity>();
        // 创建一个ObjectMapper来解析JSON数据
        ObjectMapper mapper = new ObjectMapper();
        Integer allComments = 0;
        String commentUrl = url + "/issues/comments";
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            // 创建一个HttpGet请求，用于获取仓库的提交信息
            HttpGet request = new HttpGet(commentUrl);
            try (CloseableHttpResponse response = client.execute(request)) {
                String responseBody = EntityUtils.toString(response.getEntity());
                List<Map<String, Object>> comments = mapper.readValue(responseBody, List.class);
                for (Map<String, Object> comment : comments) {
                    Map<String, Object> commentUser = (Map<String, Object>) comment.get("user");
                    if (commentUser != null) {
                        String name = (String) commentUser.get("login");
                        String message = (String) comment.get("body");
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                        Date commitDate = formatter.parse((String) comment.get("updated_at"));
                        // 将活动信息和活动时间放入Entity中
                        MessageEntity newComment = new MessageEntity(message, commitDate);
                        // 判断当前Map中是否已经有了该author
                        PersonEntity person = commentPerson.get(name);
                        allComments++;
                        if (null != person) {
                            person.getComments().add(newComment);
                            person.setCommentsCount(person.getCommentsCount() + 1);
                        } else {
                            // 如果没有该author，那么则需要在Map中放入
                            List<MessageEntity> messageList = new ArrayList<MessageEntity>();
                            messageList.add(newComment);
                            PersonEntity newPerson = new PersonEntity();
                            newPerson.setName(name);
                            newPerson.setComments(messageList);
                            newPerson.setCommentsCount(1);
                            commentPerson.put(name, newPerson);
                        }
                    }
                }
                for (Map.Entry<String, PersonEntity> entry : commentPerson.entrySet()) {
                    String id = entry.getKey();
                    PersonEntity value = entry.getValue();
                    PersonEntity person = groupNumbers.get(id);
                    if (person == null) {
                        PersonEntity newPerson = new PersonEntity();
                        newPerson.setCommitsCount(value.getCommentsCount());
                        groupNumbers.put(id, newPerson);
                    } else {
                        person.setCommentsCount(value.getCommentsCount());
                        groupNumbers.put(id, person);
                    }
                }
                commitsJson = mapper.writeValueAsString(commentPerson);
            }
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
        myGroup.setCommentsCount(allComments);
        return commitsJson;
    }

    public List<String> getBranchNames() {
        // 获取所有分支
        String branchUrl = "https://api.github.com/repos/" + orgName + "/" + reposName + "/branches";
        List<String> branchName = new ArrayList<String>();
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(branchUrl);
            try (CloseableHttpResponse response = client.execute(request)) {
                String responseBody = EntityUtils.toString(response.getEntity());
                ObjectMapper mapper = new ObjectMapper();
                List<Map<String, Object>> branches = mapper.readValue(responseBody, List.class);
                for (Map<String, Object> branch : branches) {
                    branchName.add((String) branch.get("name"));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return branchName;
    }


    @Override
    public String getGroupIssues() {
        String commitsJson = null;
        // 创建一个Map来存储人名和对应的entity
        Map<String, PersonEntity> commentPerson = new HashMap<String, PersonEntity>();
        // 创建一个ObjectMapper来解析JSON数据
        ObjectMapper mapper = new ObjectMapper();
        String issueUrl = url + "/issues";
        Integer allIssues = 0;
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            // 创建一个HttpGet请求，用于获取仓库的提交信息
            HttpGet request = new HttpGet(issueUrl);
            try (CloseableHttpResponse response = client.execute(request)) {
                String responseBody = EntityUtils.toString(response.getEntity());
                List<Map<String, Object>> issues = mapper.readValue(responseBody, List.class);
                for (Map<String, Object> issue : issues) {
                    Map<String, Object> commentUser = (Map<String, Object>) issue.get("user");
                    if (commentUser != null) {
                        String name = (String) commentUser.get("login");
                        String message = (String) issue.get("body");
                        Integer commentCount = (Integer) issue.get("comments");
                        String title = (String) issue.get("title");
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                        Date issueDate = formatter.parse((String) issue.get("updated_at"));
                        // 将活动信息和活动时间放入Entity中
                        IssueMessage issueMessage = new IssueMessage();
                        issueMessage.setTitle(title);
                        issueMessage.setComments(commentCount);
                        issueMessage.setMessage(message);
                        issueMessage.setDate(issueDate);
                        // 判断当前Map中是否已经有了该author
                        PersonEntity person = commentPerson.get(name);
                        allIssues++;
                        if (null != person) {
                            person.getIssues().add(issueMessage);
                            person.setIssuesCount(person.getIssuesCount() + 1);
                        } else {
                            // 如果没有该author，那么则需要在Map中放入
                            List<IssueMessage> messageList = new ArrayList<>();
                            messageList.add(issueMessage);
                            PersonEntity newPerson = new PersonEntity();
                            newPerson.setName(name);
                            newPerson.setIssues(messageList);
                            newPerson.setIssuesCount(1);
                            commentPerson.put(name, newPerson);
                        }
                    }
                }

                for (Map.Entry<String, PersonEntity> entry : commentPerson.entrySet()) {
                    String id = entry.getKey();
                    PersonEntity value = entry.getValue();
                    PersonEntity person = groupNumbers.get(id);
                    if (person == null) {
                        PersonEntity newPerson = new PersonEntity();
                        newPerson.setIssuesCount(value.getIssuesCount());
                        groupNumbers.put(id, newPerson);
                    } else {
                        person.setIssuesCount(value.getIssuesCount());
                        groupNumbers.put(id, person);
                    }
                }

                commitsJson = mapper.writeValueAsString(commentPerson);
            }
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
        myGroup.setIssuesCount(allIssues);
        return commitsJson;
    }

    @Override
    public String getGroupPulls() {
        String commitsJson = null;
        // 创建一个Map来存储人名和对应的entity
        Map<String, PersonEntity> pullsPerson = new HashMap<String, PersonEntity>();
        // 创建一个ObjectMapper来解析JSON数据
        ObjectMapper mapper = new ObjectMapper();
        Integer allPulls = 0;
        String pullsUrl = url + "/pulls";
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            // 创建一个HttpGet请求，用于获取仓库的提交信息
            HttpGet request = new HttpGet(pullsUrl);
            try (CloseableHttpResponse response = client.execute(request)) {
                String responseBody = EntityUtils.toString(response.getEntity());
                List<Map<String, Object>> pulls = mapper.readValue(responseBody, List.class);
                for (Map<String, Object> pull : pulls) {
                    Map<String, Object> pullUser = (Map<String, Object>) pull.get("user");
                    if (pullUser != null) {
                        String name = (String) pullUser.get("login");
                        String message = (String) pull.get("body");
                        String title = (String) pull.get("title");
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                        Date pullDate = formatter.parse((String) pull.get("updated_at"));
                        // 将活动信息和活动时间放入Entity中
                        PullMessage newPulls = new PullMessage();
                        newPulls.setDate(pullDate);
                        newPulls.setTitle(title);
                        newPulls.setMessage(message);
                        // 判断当前Map中是否已经有了该author
                        PersonEntity person = pullsPerson.get(name);
                        allPulls++;
                        if (null != person) {
                            person.getPulls().add(newPulls);
                            person.setPullsCount(person.getPullsCount() + 1);
                        } else {
                            // 如果没有该author，那么则需要在Map中放入
                            List<PullMessage> messageList = new ArrayList<PullMessage>();
                            messageList.add(newPulls);
                            PersonEntity newPerson = new PersonEntity();
                            newPerson.setName(name);
                            newPerson.setPulls(messageList);
                            newPerson.setPullsCount(1);
                            pullsPerson.put(name, newPerson);
                        }
                    }
                }

                for (Map.Entry<String, PersonEntity> entry : pullsPerson.entrySet()) {
                    String id = entry.getKey();
                    PersonEntity value = entry.getValue();
                    PersonEntity person = groupNumbers.get(id);
                    if (person == null) {
                        PersonEntity newPerson = new PersonEntity();
                        newPerson.setPullsCount(value.getPullsCount());
                        groupNumbers.put(id, newPerson);
                    } else {
                        person.setIssuesCount(value.getPullsCount());
                        groupNumbers.put(id, person);
                    }
                }

                commitsJson = mapper.writeValueAsString(pullsPerson);
            }
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
        myGroup.setPullsCount(allPulls);
        return commitsJson;
    }

    @Override
    public String getGroupMilestones() {
        String milestoneJson = null;
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url8);
            try (CloseableHttpResponse response = client.execute(request)) {
                // 将响应体转换成字符串
                String responseBody = EntityUtils.toString(response.getEntity());
                // 解析响应体中的 JSON 数据
                ObjectMapper mapper = new ObjectMapper();
                // 将响应体解析为 Map
                List<Map<String, Object>> milestones = mapper.readValue(responseBody, List.class);

                List<Map<String, Object>> milestoneInfos = new ArrayList<>();
                for (Map<String, Object> milestone : milestones) {
                    // 提取基本信息
                    Map<String, Object> milestoneInfo = new HashMap<>();
                    milestoneInfo.put("number", milestone.get("number"));
                    milestoneInfo.put("title", milestone.get("title"));
                    milestoneInfo.put("description", milestone.get("description"));
                    milestoneInfo.put("state", milestone.get("state"));
                    milestoneInfo.put("created_at", milestone.get("created_at"));
                    milestoneInfo.put("updated_at", milestone.get("updated_at"));
                    milestoneInfo.put("due_on", milestone.get("due_on"));

                    // 提取创建者信息
                    Map<String, Object> creator = (Map<String, Object>) milestone.get("creator");
                    if (creator != null) {
                        milestoneInfo.put("creator.login", creator.get("login"));
                        milestoneInfo.put("creator.id", creator.get("id"));
                    }

                    // 提取问题数量
                    milestoneInfo.put("open_issues", milestone.get("open_issues"));
                    milestoneInfo.put("closed_issues", milestone.get("closed_issues"));

                    milestoneInfos.add(milestoneInfo);
                }

                // 将 milestoneInfo Map 转换为 JSON 字符串
                milestoneJson = mapper.writeValueAsString(milestoneInfos);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return milestoneJson;
    }

    @Override
    public String getALlGroups() {
        String commitsJson = null;
        ObjectMapper mapper = new ObjectMapper();

        getGroupCommits();
        getGroupIssues();
        getGroupPulls();
        getGroupComments();

        List<GroupEntity> groups = new ArrayList<>();
        for (int i = 1; i <= 8; i++) {
            if (i == 4) {
                myGroup.setName("Group4");
                myGroup.setCount(5);
                Integer score = myGroup.getCommentsCount() + myGroup.getCommitsCount() + myGroup.getIssuesCount() + myGroup.getPullsCount();
                myGroup.setScore(score);
                groups.add(myGroup);
            } else {
                GroupEntity group = new GroupEntity();
                String name = "Group" + i;
                group.setName(name);
                Integer randomCount = 3 + (int) (Math.random() * ((10 - 3) + 1));
                group.setCount(randomCount);
                groups.add(group);
            }
        }
        String myGroupUrl = url + "/commits/main";

        GroupEntity myGroup = groups.get(3);
        myGroup.setCount(5);
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(myGroupUrl);
            try (CloseableHttpResponse response = client.execute(request)) {
                // 将响应体转换为字符串
                String responseBody = EntityUtils.toString(response.getEntity());

                Map<String, Object> latest = mapper.readValue(responseBody, Map.class);

                Map<String, Object> commitAuthor = (Map<String, Object>) latest.get("commit");
                Map<String, Object> author = (Map<String, Object>) commitAuthor.get("author");
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                Date commitDate = formatter.parse((String) author.get("date"));
                String message = (String) commitAuthor.get("message");
                MessageEntity messageEntity = new MessageEntity();

                messageEntity.setDate(commitDate);
                messageEntity.setMessage(message);
                myGroup.setLatestMessage(messageEntity);
            }
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
        // 最新的活动在最前面
        try {
            commitsJson = mapper.writeValueAsString(groups);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return commitsJson;
    }

    @Override
    public String getGroupScore() {
        String commitsJson = null;
        ObjectMapper mapper = new ObjectMapper();
        getGroupCommits();
        getGroupIssues();
        getGroupPulls();
        getGroupComments();
        groupNumbers.remove("personEntity");
        for (Map.Entry<String, PersonEntity> entry : groupNumbers.entrySet()) {
            String id = entry.getKey();
            PersonEntity value = entry.getValue();
            Integer score = Optional.ofNullable(value.getIssuesCount()).orElse(0)
                    + Optional.ofNullable(value.getPullsCount()).orElse(0)
                    + Optional.ofNullable(value.getCommitsCount()).orElse(0)
                    + Optional.ofNullable(value.getCommentsCount()).orElse(0);
            value.setScore(score);
            groupNumbers.put(id, value);
        }

        try {
            commitsJson = mapper.writeValueAsString(groupNumbers);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return commitsJson;
    }


}