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

## live-demo values:
```
client-id: 076f9e789fac2f361c2e0145aa0cc7298cd6c5455492f3c90999480e40406d3f
client-secret: gloas-2b635dc4bf910399e28ed5ede32d88305bc7ebf6450150ee8a3cac4078fecf91

```

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

# About the project 
## Lab 1
Lab 1
First things first we had to choose the topic of our project. Choice fell onto a mechanic shop with a twist - its for star ships.
Next step – define business process and entities. In our mechanic shop the procedure is simple:
1.	The [Client](src/main/java/lsit/Models/Client.java) provides his/her star ship to the mechanic shop with a [description of the ship and the problem itself] (ServiceRequest class).
2.	The [Diagnostic Team](src/main/java/lsit/Models/Diagnostician.java) accesses the [situation](src/main/java/lsit/Models/DiagnosticAssesment.java) at hand and there are 2 ways of handling that problem:
     * The fault is a quick fix, so it is done right after the diagnostics and the ship is sent off to the final step
   * The fault needs to be taken care of by actual engineers, known as the [Electrician](src/main/java/lsit/Models/Electrician.java), [Mechanic](src/main/java/lsit/Models/Mechanic.java) and [Software Specialist]((src/main/java/lsit/Models/SoftwareSpecialist.java)) classes.
3.	After the problem is taken care of each engineering team that participated in the fix has to write a [Repair Team Report]((src/main/java/lsit/Models/RepairTeamReport.java))
4.	Finally, after the issues are fixed (or not) and the team reports are written comes in the [Assembler]((src/main/java/lsit/Models/Assembler.java)). His job is to create the [(src/main/java/lsit/Models/FinalReport.java)](class), which would be send off the the client with all the works done on the star ship, remaining issues and, of course, the costs to be paid, *we are doing business here after all*


# Authorization and Security
* Authorization happens with GitLab token. You authorize via GitLab and get roles assigned corresponding to which group
  you are direct member of. So, if you are a direct member of group Client in our gitlab StarShipMechanicShop group - you will get
  ROLE_CLIENT assigned to you. Only one 