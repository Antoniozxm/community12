package life.antonio12.community12.community12.controller;

import life.antonio12.community12.community12.controller.provider.GithubProvider;
import life.antonio12.community12.community12.dto.AccessTokenDTO;
import life.antonio12.community12.community12.dto.GithubUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Antonio on 2019/06/12
 *
 */
@Controller

public class AuthorizeController {

    @Autowired
    private life.antonio12.community12.community12.controller.provider.GithubProvider githubProvider;

    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;

    @GetMapping("/callback")
    public String callback (@RequestParam(name="code") String code,
                            @RequestParam(name="state")String state,
                            HttpServletRequest request){   // Spring把上下文中的request放到方法里
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser user = githubProvider.getUser(accessToken);

        if(user != null)
        {
            request.getSession().setAttribute("user",user); //拿到session，set attribute， 把当前user对象放到session里面
            return "redirect:/"; // 回到主页
            //登陆成功
        }
        else {
            return "redirect:/";
            // fail to login
        }

        //return "index";
    }
}
