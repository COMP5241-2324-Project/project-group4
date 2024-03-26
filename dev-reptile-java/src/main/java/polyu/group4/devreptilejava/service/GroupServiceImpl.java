package polyu.group4.devreptilejava.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;
import polyu.group4.devreptilejava.service.impl.GroupService;
//加了一条
import com.fasterxml.jackson.databind.type.TypeFactory;
//加的第二条
//import org.json.JSONObject;

import java.io.IOException;
import java.util.*;


//    第二步，等类报错的时候右键实现
@Service
public class GroupServiceImpl implements GroupService {
    // 替换GitHub 的组织名，令牌，仓库
    private String orgName = "COMP5241-2324-Project";
    private String reposName = "project-group4";
    private String url1 = "https://api.github.com/repos/" + orgName + "/" + reposName + "/commits";
    //private String oauthToken = "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";
    //    2222 issues
    private String url2 = "https://api.github.com/repos/" + orgName + "/" + reposName + "/issues";
    //  66666 assigned issues
    private String url6 = "https://api.github.com/repos/" + orgName + "/" + reposName + "/assignees";
    //    77777 pull requests
    private String url7 = "https://api.github.com/repos/" + orgName + "/" + reposName + "/pulls";
    //    88888 milestones
    private String url8 = "https://api.github.com/repos/" + orgName + "/" + reposName + "/milestones";


//    示例代码
    @Override
    public String getGroupCommits()  {
        String commitsJson = null;
        try(CloseableHttpClient client = HttpClients.createDefault()){
            // 创建一个HttpGet请求，用于获取仓库的提交信息
            HttpGet request = new HttpGet(url1);
//            request.addHeader("Authorization","token " + oauthToken);
            try(CloseableHttpResponse response = client.execute(request)){
                // 将响应体转换为字符串
                String responseBody = EntityUtils.toString(response.getEntity());
                // 创建一个ObjectMapper来解析JSON数据
                ObjectMapper mapper = new ObjectMapper();
                // 将响应体解析为一个列表，每个元素包含一个提交信息的Map
                List<Map<String,Object>> commits = mapper.readValue(responseBody, List.class);
                // 创建一个Map来存储每个作者的提交频率
                Map<String, Integer> commitFrequency = new HashMap<>();
                for (Map<String, Object> commit: commits){
                    Map<String, Object> author = (Map<String, Object>) commit.get("author");
                    if (author != null){
                        String authorName = (String) author.get("login");
                        commitFrequency.put(authorName, commitFrequency.getOrDefault(authorName, 0) + 1);
                    }
                    commitFrequency.remove("github-classroom[bot]");
                }
                commitsJson = mapper.writeValueAsString(commitFrequency);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return commitsJson;
    }

//写的第一个222222222
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
