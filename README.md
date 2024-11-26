1. Launch server from Main class
2. For testing authorization go to https://localhost:8080/client in your web browser.*
3. For checking possible api calls go to https://localhost:8080/swagger-ui/index.html

# *IMPORTANT
For testing authorization you first need to set up application.yml file in [src/main/resources](src/main/resources) folder.
```yaml
spring:
  security:
    oauth2:
      client:
        registration:
          gitlab:
            client-id: <client's ID>
            client-secret: <secret client's code>
            scope: openid+profile+email
            authorization-grant-type: authorization_code
            redirect-uri: http://localhost:8080/login/oauth2/code/gitlab
        provider:
          gitlab:
            issuer-uri: https://gitlab.com
            user-name-attribute: sub
```

You can either use client-id and client-secret either from live-demo or generate them for yourself.

## Generating your own ID and code:
In GitLab:

1. On the left sidebar, select your avatar.
2. Select Edit profile.
3. On the left sidebar, select Applications.
4. Select Add new application.
5. Enter a Name and Redirect URI.
6. Select following Scopes: openid, profile, email
7. In the Redirect URI, enter: http://localhost:8080/login/oauth2/code/gitlab
8. Select Save application.