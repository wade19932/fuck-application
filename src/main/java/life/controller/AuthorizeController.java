
package life.controller;

import life.dto.AccesstTkenDTO;
import life.dto.GithubUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import life.provider.GithubProvider;
import sun.net.httpserver.HttpServerImpl;

import javax.servlet.http.HttpServletRequest;

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
                           @RequestParam(name = "state") String state,
                           HttpServletRequest request) {



            AccesstTkenDTO accesstTkenDTO = new AccesstTkenDTO();
            accesstTkenDTO.setClient_id(clientId);
            accesstTkenDTO.setClient_secret(clientSecret);
            accesstTkenDTO.setCode(code);
            accesstTkenDTO.setRedirect_uri(clientUri);
            accesstTkenDTO.setState(state);

            String accessToken = githubProvider.getAccessToken(accesstTkenDTO);
            System.out.println(accessToken);
            GithubUser user = githubProvider.getUser(accessToken);
            if (user != null) {
                request.getSession().setAttribute("user",user);
                return "redirect:/";
                //登陆成功
            } else {
                return "redirect:/";
                //登陆失败，重新登陆

            }
        }


    }
