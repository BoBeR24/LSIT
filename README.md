# How To Use
1. Launch server from Main class
2. Authorize via GitLab token by going to https://localhost:8080/authentication (if you are not authorized you would not
   receive any roles, thus, will not have any permissions in the network)
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
Authorization happens with GitLab token. You authorize via GitLab and get roles assigned corresponding to which group
you are direct member of. So, if you are a direct member of group Client in our gitlab StarShipMechanicShop group - you will get
ROLE_CLIENT assigned to you. Only one role should be assigned at the same time(even if you are member of several groups -
you will get only one role).

Each role has access only to certain controllers and certain REST API methods. All entities are only able to access
information related to their id. So, for example, client cannot change name of another client with put method.
However, diagnostician and all tech guys are able to edit corresponding reports made by any member of their team(
e.g diagnostician can access any diagnostic assessments, not only theirs).

The logic behind these rules is that client should have no information about anyone except for himself, as they are unknown/untrusted entities.
At the same time people working inside the system(diagnosticians, tech teams and assemblers) are considered trusted users,
so we give them a little bit more control(again: diagnosticians can access all the assessments, not only theirs).

Here's a list of all permissions for each role:
- Client: client-controller(GET(id), PUT(id), DELETE(id)), service-request-controller(GET(id), PUT(id), POST, DELETE(id))
- Diagnostician: diagnostic-team-controller(GET(id), PUT(id), DELETE(id)), diagnostic-assessment-controller(GET, GET(id), PUT(id), POST, DELETE(id)),
  request-service-controller(GET, GET(id))
- Mechanic, Electrician, Software Specialist: \<tech team name>-controller(GET(id), PUT(id), DELETE(id)),
  repair-team-controller(GET, GET(id), PUT(id), POST, DELETE(id)), diagnostic-assessment-controller(GET, GET(id))
- Assembler: assembler-controller(GET(id), PUT(id), DELETE(id)), final-report-controller(GET, GET(id), PUT(id), POST, DELETE(id)),
  final-report-controller(GET, GET(id))
- Admin: Has access to every method and every controller(basically a God)

Role distribution happens in CustomOAuth2UserService(), it gets activated when user token gets loaded.

There are no extensive security measures in our network, but all role restrictions are implemented either using config file,
in which by creating a custom security filter chain we specify which roles have which permissions or just by if statements
in controllers in which id of the current user is checked and then decided on whether to give access to the call or not.

# Scalability
We tried to keep structure of the network rather simple, so in that regard it should be not hard to scale the model.
However, in the current version of the code(it may get updated) we don't use any design patterns, interfaces or inheritance,
so introducing new interdependencies between models or creating new fields may be quite a tedious task.
Our network doesn't scale automatically at all, so I would rate scalability of the current version as LOW-MEDIUM

## Here's ChatGPT analysis of the scalability of our project, I advise to read through it, as it may contain rather useful information:

### **Scalability Analysis for Star Ship Mechanic Shop Project**

#### **1. Business Process Bottlenecks**
- **Diagnostics:** Diagnosticians could become a bottleneck as all requests funnel through them. Consider parallelizing tasks or load balancing.
- **Engineering & Reporting:** Specialized roles (Mechanic, Electrician, Software Specialist) may create silos. Automate routine tasks or dynamically allocate teams to improve throughput. The Assembler role should also be optimized with modular or automated reporting.

#### **2. Security & Access Control**
- **GitLab Authentication:** Reliance on GitLab tokens may limit scalability. A dedicated OAuth2 provider or centralized identity service is recommended.
- **Single Role Limitation:** Single-role assignment limits flexibility. Introduce composite or hierarchical roles for better task management.
- **Minimal Security Measures:** Implement stronger security, including API gateways, encryption, and logging to ensure data integrity and access control at scale.

#### **3. Data Consistency & Access**
- **Entity Restrictions:** Ensure efficient database indexing and caching (e.g., Redis) to maintain fast data access and integrity.
- **Distributed Transactions:** Adopt patterns like Saga for consistent data management across services.

#### **4. Recommendations for Scaling**
- **Microservices:** Break the system into microservices for each role, enabling independent scaling.
- **Load Balancing & Automation:** Use load balancers and automation (AI diagnostics, report generation) to reduce human bottlenecks.
- **Cloud Scaling:** Leverage cloud platforms with autoscaling to handle increased demand.

### **Conclusion**
To scale effectively, focus on reducing bottlenecks, enhancing security, and adopting microservices. This will improve flexibility, ensure data consistency, and support growth.