package polyu.group4.devreptilejava.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;
import polyu.group4.devreptilejava.service.impl.GroupService;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GroupServiceImpl implements GroupService {
    // 替换GitHub 的组织名，令牌，仓库
    private String orgName = "COMP5241-2324-Project";
    private String reposName = "project-group4";
    private String url = "https://api.github.com/repos/" + orgName + "/" + reposName + "/commits";
    //private String oauthToken = "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";

    @Override
    public String getGroupCommits()  {
        String commitsJson = null;
        try(CloseableHttpClient client = HttpClients.createDefault()){
            // 创建一个HttpGet请求，用于获取仓库的提交信息
            HttpGet request = new HttpGet(url);
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
}
