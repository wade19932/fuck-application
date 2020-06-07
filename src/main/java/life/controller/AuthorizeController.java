
package life.controller;

import life.dto.AccesstTkenDTO;
import life.dto.GithubUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import life.provider.GithubProvider;

@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String clientUri;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state
    ){
        AccesstTkenDTO accesstTkenDTO = new AccesstTkenDTO();
        accesstTkenDTO.setClient_id(clientId);
        accesstTkenDTO.setClient_secret(clientSecret);
        accesstTkenDTO.setCode(code);
        accesstTkenDTO.setRedirect_uri(clientUri);
        accesstTkenDTO.setState(state);

        String accessToken = githubProvider.getAccessToken(accesstTkenDTO);
        System.out.println(accessToken);
        GithubUser user = githubProvider.getUser(accessToken);
        System.out.println(user.getName());

        return "index";
    }



}
