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
import java.util.HashMap;
import java.util.List;
import java.util.Map;




//    第二步，等类报错的时候右键实现
@Service
public class GroupServiceImpl implements GroupService {
    // 替换GitHub 的组织名，令牌，仓库
    private String orgName = "COMP5241-2324-Project";
    private String reposName = "project-group4";
    private String url1 = "https://api.github.com/repos/" + orgName + "/" + reposName + "/commits";
    //private String oauthToken = "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";
    //    66666
    private String url6 = "https://api.github.com/repos/" + orgName + "/" + reposName + "/issues";
    //    77777
    private String url7 = "https://api.github.com/repos/" + orgName + "/" + reposName + "/pulls";
    //    88888


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

//写的第一个
    @Override
    public String getGroupIssues() {
        String issuesJson = null;
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            // 创建一个HttpGet请求，用于获取仓库的问题信息
            HttpGet request = new HttpGet(url6);
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

//写的第二个
    @Override
    public String getGroupPulls() {
        String pullRequestJson = null;
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            // 创建一个HttpGet请求，用于获取仓库的拉取请求信息
            HttpGet request = new HttpGet(url7); // 确保url7已经被设置为正确的GitHub API端点
            // request.addHeader("Authorization", "token " + oauthToken); // 如果需要的话，取消注释这行来添加身份验证头
            try (CloseableHttpResponse response = client.execute(request)) {
                // 将响应体转换为字符串
                String responseBody = EntityUtils.toString(response.getEntity());
                // 解析响应体中的JSON数据
                ObjectMapper mapper = new ObjectMapper();
                // 将响应体解析成Map
                Map<String, Object> pullRequest = mapper.readValue(responseBody, Map.class);

                // 提取基本信息
                Map<String, Object> pullRequestInfo = new HashMap<>();
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
                // 注意: 这些数据可能在API返回的JSON中不直接可用，需要额外的API调用或在Pull Request列表中
                if (pullRequest.containsKey("additions")) {
                    pullRequestInfo.put("additions", pullRequest.get("additions"));
                }
                if (pullRequest.containsKey("deletions")) {
                    pullRequestInfo.put("deletions", pullRequest.get("deletions"));
                }
                if (pullRequest.containsKey("changed_files")) {
                    pullRequestInfo.put("changed_files", pullRequest.get("changed_files"));
                }
                // Convert the pullRequestInfo map to JSON string
                pullRequestJson = mapper.writeValueAsString(pullRequestInfo);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return pullRequestJson;    }

    @Override
    public String getGroupMilestones() {
        return null;
    }

    @Override
    public String getGroupNotifications() {
        return null;
    }

    @Override
    public String getGroupProjects() {
        return null;
    }





}
