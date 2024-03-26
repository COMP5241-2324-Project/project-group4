package polyu.group4.devreptilejava.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    private String url = "https://api.github.com/repos/" + orgName + "/" + reposName + "/commits";


    @Override
    public String getGroupCommits() {
        String commitsJson = null;
        List<String> branchName = getBranchNames();
        // 创建一个Map来存储人名和对应的entity
        Map<String, PersonEntity> commitPersons = new HashMap<String, PersonEntity>();
        // 创建一个ObjectMapper来解析JSON数据
        ObjectMapper mapper = new ObjectMapper();
        for (String name : branchName) {
            String branchUrl = "https://api.github.com/repos/" + orgName + "/" +reposName + "/commits?sha="+ name;
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
                                    person.getMessages().add(newCommit);
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


    public void test() {
    }
}
