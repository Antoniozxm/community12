package life.antonio12.community12.community12.controller;

import life.antonio12.community12.community12.controller.provider.GithubProvider;
import life.antonio12.community12.community12.dto.AccessTokenDTO;
import life.antonio12.community12.community12.dto.GithubUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Antonio on 2019/06/12
 *
 */
@Controller

public class AuthorizeController {

    @Autowired
    private life.antonio12.community12.community12.controller.provider.GithubProvider githubProvider;



    @GetMapping("/callback")
    public String callback ( @RequestParam(name="code") String code,
                             @RequestParam(name="state")String state){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri("http://localhost:8887/callback");
        accessTokenDTO.setState(state);
        accessTokenDTO.setClient_id("788c3883809b260a699f");
        accessTokenDTO.setClient_secret("7a390691b74dd33071fe31f1058a424202333972");
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser user = githubProvider.getUser(accessToken);
        System.out.println(user.getName());


        return "index";
    }
}
