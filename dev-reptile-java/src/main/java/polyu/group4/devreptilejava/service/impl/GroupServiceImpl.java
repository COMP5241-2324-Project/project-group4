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
import polyu.group4.devreptilejava.entity.MessageEntity;
import polyu.group4.devreptilejava.entity.PersonEntity;
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

    private String url2 = "https://api.github.com/repos/" + orgName + "/" + reposName + "/issues";
    //  66666 assigned issues
    private String url6 = "https://api.github.com/repos/" + orgName + "/" + reposName + "/assignees";
    //    77777 pull requests
    private String url7 = "https://api.github.com/repos/" + orgName + "/" + reposName + "/pulls";
    //    88888 milestones
    private String url8 = "https://api.github.com/repos/" + orgName + "/" + reposName + "/milestones";


    @Override
    public String getGroupCommits() {
        String commitsJson = null;
        List<String> branchName = getBranchNames();
        // 创建一个Map来存储人名和对应的entity
        Map<String, PersonEntity> commitPersons = new HashMap<String, PersonEntity>();
        // 创建一个ObjectMapper来解析JSON数据
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
                }
            } catch (IOException | ParseException e) {
                throw new RuntimeException(e);
            }
        }
        return commitsJson;
    }

    @Override
    public String getGroupComments() {
        String commitsJson = null;
        // 创建一个Map来存储人名和对应的entity
        Map<String, PersonEntity> commentPerson = new HashMap<String, PersonEntity>();
        // 创建一个ObjectMapper来解析JSON数据
        ObjectMapper mapper = new ObjectMapper();
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
                commitsJson = mapper.writeValueAsString(commentPerson);
            }
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
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
        String issuesJson = null;
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            // 创建一个HttpGet请求，用于获取仓库的问题信息
            HttpGet request = new HttpGet(url2);
            // request.addHeader("Authorization", "token " + oauthToken);
            try (CloseableHttpResponse response = client.execute(request)) {
                // 将响应体转换为字符串
                String responseBody = EntityUtils.toString(response.getEntity());
                // 创建一个ObjectMapper来解析JSON数据
                ObjectMapper mapper = new ObjectMapper();
                // 将响应体解析为一个列表，每个元素包含一个问题信息的Map
                List<Map<String, Object>> issues = mapper.readValue(
                        responseBody,
                        TypeFactory.defaultInstance().constructCollectionType(List.class, Map.class)
                );
                // 创建一个Map来存储每个用户的问题计数和问题详情
                Map<String, Object> stats = new HashMap<>();
                Map<String, Integer> userIssueCount = new HashMap<>();
                for (Map<String, Object> issue : issues) {
                    // 统计每个用户的issue数量
                    Map<String, Object> user = (Map<String, Object>) issue.get("user");
                    String userName = (String) user.get("login");
                    userIssueCount.put(userName, userIssueCount.getOrDefault(userName, 0) + 1);

                    // 提取其他相关数据
                    String issueNumber = issue.get("number").toString();
                    String issueTitle = (String) issue.get("title");
                    String issueState = (String) issue.get("state");
                    String createdAt = (String) issue.get("created_at");
                    String updatedAt = (String) issue.get("updated_at");
                    int comments = (int) issue.get("comments");
                    Map<String, Integer> reactions = (Map<String, Integer>) issue.get("reactions");
                    List<Map<String, Object>> labels = (List<Map<String, Object>>) issue.get("labels");
                    Map<String, Object> assignee = (Map<String, Object>) issue.get("assignee");

                    Map<String, Object> issueDetails = new HashMap<>();
                    issueDetails.put("number", issueNumber);
                    issueDetails.put("title", issueTitle);
                    issueDetails.put("state", issueState);
                    issueDetails.put("created_at", createdAt);
                    issueDetails.put("updated_at", updatedAt);
                    issueDetails.put("comments", comments);
                    issueDetails.put("reactions", reactions);
                    issueDetails.put("labels", labels);
                    issueDetails.put("assignee", assignee);

                    // 将每个issue的详细信息添加到统计信息中
                    stats.put(issueNumber, issueDetails);
                }
                // 将用户问题计数和统计信息合并到一个JSON字符串
                Map<String, Object> combinedDetails = new HashMap<>();
                combinedDetails.put("issuesStats", stats);
                combinedDetails.put("userIssueCount", userIssueCount);
                issuesJson = mapper.writeValueAsString(combinedDetails);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return issuesJson;
    }

    //写的第二个777777777777777777
    @Override
    public String getGroupPulls() {
        String pullRequestJson = null;
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            // 创建一个HttpGet请求，用于获取仓库的拉取请求信息
            HttpGet request = new HttpGet(url7); // 确保url7已经被设置为正确的GitHub API端点
            try (CloseableHttpResponse response = client.execute(request)) {
                // 将响应体转换成字符串
                String responseBody = EntityUtils.toString(response.getEntity());
                // 解析响应体中的 JSON 数据
                ObjectMapper mapper = new ObjectMapper();
                // 将响应体解析为 List<Map<String, Object>>
                List<Map<String, Object>> pullRequests = mapper.readValue(responseBody, List.class);

                List<Map<String, Object>> pullRequestInfos = new ArrayList<>();
                for (Map<String, Object> pullRequest : pullRequests) {
                    // 提取基本信息
                    Map<String, Object> pullRequestInfo = new LinkedHashMap<>();//使用hashmap时候两个时间没有连在一起输出，改用linkedhashmap
                    pullRequestInfo.put("title", pullRequest.get("title"));
                    pullRequestInfo.put("state", pullRequest.get("state"));
                    pullRequestInfo.put("created_at", pullRequest.get("created_at"));
                    pullRequestInfo.put("updated_at", pullRequest.get("updated_at"));

                    // 提取用户信息
                    Map<String, Object> user = (Map<String, Object>) pullRequest.get("user");
                    if (user != null) {
                        pullRequestInfo.put("user.login", user.get("login"));
                        pullRequestInfo.put("user.html_url", user.get("html_url"));
                    }

                    // 提取代码变更概览
                    if (pullRequest.containsKey("additions")) {
                        pullRequestInfo.put("additions", pullRequest.get("additions"));
                    }
                    if (pullRequest.containsKey("deletions")) {
                        pullRequestInfo.put("deletions", pullRequest.get("deletions"));
                    }
                    if (pullRequest.containsKey("changed_files")) {
                        pullRequestInfo.put("changed_files", pullRequest.get("changed_files"));
                    }

                    pullRequestInfos.add(pullRequestInfo);
                }

                // 将 pullRequestInfo Map 转换为 JSON 字符串
                pullRequestJson = mapper.writeValueAsString(pullRequestInfos);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return pullRequestJson;    }

    //    写的第三个888888888888888888
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

    //    写的第四个66666
    @Override
    public String getGroupAssignees() {
        String assigneesJson = null;
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url6); // 确保url5已经被设置为正确的GitHub API端点
            try (CloseableHttpResponse response = client.execute(request)) {
                // 将响应体转换成字符串
                String responseBody = EntityUtils.toString(response.getEntity());
                // 解析响应体中的 JSON 数据
                ObjectMapper mapper = new ObjectMapper();
                // 将响应体解析为 List<Map<String, Object>>
                List<Map<String, Object>> assignees = mapper.readValue(responseBody, List.class);

                List<Map<String, Object>> assigneeInfos = new ArrayList<>();
                for (Map<String, Object> assignee : assignees) {
                    // 提取基本信息
                    Map<String, Object> assigneeInfo = new HashMap<>();
                    assigneeInfo.put("login", assignee.get("login"));
                    assigneeInfo.put("id", assignee.get("id"));
                    assigneeInfo.put("received_events_url", assignee.get("received_events_url"));
                    assigneeInfo.put("type", assignee.get("type"));
                    assigneeInfo.put("site_admin", assignee.get("site_admin"));

                    assigneeInfos.add(assigneeInfo);
                }

                // 将 assigneeInfo Map 转换为 JSON 字符串
                assigneesJson = mapper.writeValueAsString(assigneeInfos);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return assigneesJson;
    }

    @Override
    public String getGroupProjects() {
        return null;
    }
}
