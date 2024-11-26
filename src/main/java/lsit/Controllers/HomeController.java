package lsit.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
public class HomeController {
    @GetMapping("/client")
    public String getUser(OAuth2AuthenticationToken authentication) throws Exception{
        var groups = (List<String>)authentication.getPrincipal().getAttribute("https://gitlab.org/claims/groups/owner");
        if(!groups.contains("lsit-ken3239/roles/students")){

            return "Access denied";
//            throw new Exception("Access Denied...");
        };

        var userAttributes = authentication.getPrincipal().getAttributes();

        return "<pre> \n" +
                userAttributes.entrySet().parallelStream().collect(
                        StringBuilder::new,
                        (s, e) -> s.append(e.getKey()).append(": ").append(e.getValue()),
                        (a, b) -> a.append("\n").append(b)
                ) +
                "</pre>";
    }
}
