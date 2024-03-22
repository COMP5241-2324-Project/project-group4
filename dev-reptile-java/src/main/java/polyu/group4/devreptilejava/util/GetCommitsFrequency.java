package polyu.group4.devreptilejava.util;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class GetCommitsFrequency {
    public static void main(String[] args) throws Exception {
        String orgName = "COMP5241-2324-Project";
        String oauthToken = "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";
        String reposName = "project-group4";
        try(CloseableHttpClient client = HttpClients.createDefault()){
            HttpGet request = new HttpGet("https://api.github.com/orgs/" + orgName + "/repos");
            request.addHeader("Authorization","token" + oauthToken);

            try(CloseableHttpResponse response = client.execute(request)){
                String responseBody = EntityUtils.toString(response.getEntity());
                System.out.println(responseBody);
            }
        }
    }
}

