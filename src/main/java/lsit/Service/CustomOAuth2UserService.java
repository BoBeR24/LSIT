package lsit.Service;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2User user = super.loadUser(userRequest);

        // Extract groups from attributes
        List<String> groups = (List<String>) user.getAttribute("https://gitlab.org/claims/groups/owner");

        if (groups == null) {
            return user; // Return as is if no groups found
        }

        List<SimpleGrantedAuthority> authorities = groups.stream()
                .map(group -> new SimpleGrantedAuthority("ROLE_" + extractRole(group).toUpperCase()))
                .collect(Collectors.toList());

        return new DefaultOAuth2User(authorities, user.getAttributes(), "name");
    }

    private String extractRole(String group) {
        return group.substring(group.lastIndexOf("/") + 1); // Get last part as role
    }
}
