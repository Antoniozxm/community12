package life.antonio12.community12.community12.controller;

//import life.antonio12.community12.community12.controller.provider.GithubProvider;
import life.antonio12.community12.community12.Provider.GithubProvider;
import life.antonio12.community12.community12.dto.AccessTokenDTO;
import life.antonio12.community12.community12.dto.GithubUser;
import life.antonio12.community12.community12.mapper.UserMapper;
import life.antonio12.community12.community12.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * Created by Antonio on 2019/06/12
 *
 */
@Controller

public class AuthorizeController {


    //private life.antonio12.community12.community12.controller.provider.GithubProvider githubProvider;
    @Autowired
    private GithubProvider githubProvider;
    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/callback")
    public String callback (@RequestParam(name="code") String code,
                            @RequestParam(name="state")String state,
                            //HttpServletRequest request,
                            HttpServletResponse response){   // Spring把上下文中的request放到方法里

        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getUser(accessToken);

        if(githubUser != null)
        {
            User user = new User();
            user.setName(githubUser.getName());
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());


            userMapper.insert(user);
            //写入cookie
            response.addCookie(new Cookie(  "token", token));

            //request.getSession().setAttribute("user",githubUser); //拿到session，set attribute， 把当前user对象放到session里面
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
