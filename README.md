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

# About the project
First things first we had to choose the topic of our project. Choice fell onto a mechanic shop with a twist - its for star ships.
Next step – define business process and entities. In our mechanic shop the procedure is simple:

1. The [Client](src/main/java/lsit/Models/Client.java) provides his/her star ship to the mechanic shop with a [description of the ship and the problem itself](src/main/java/lsit/Models/ServiceRequest.java).
2. The [Diagnostic Team](src/main/java/lsit/Models/Diagnostician.java) accesses the [situation](src/main/java/lsit/Models/DiagnosticAssesment.java). 
3. Following the assesment the fault will be taken care of by actual engineers, known as the [Electrician](src/main/java/lsit/Models/Electrician.java), [Mechanic](src/main/java/lsit/Models/Mechanic.java) and [Software Specialist](src/main/java/lsit/Models/SoftwareSpecialist.java).
3. After the problem is taken care of each engineering team that participated in the fix has to write a [Repair Team Report]((src/main/java/lsit/Models/RepairTeamReport.java)).
4. Finally, after the issues are fixed (or not) and the team reports are written comes in the [Assembler](src/main/java/lsit/Models/Assembler.java). His job is to create the [Final Report](src/main/java/lsit/Models/FinalReport.java), which would be sent off to the client with all the works done on the star ship, remaining issues and, of course, the costs to be paid. <sup>we are doing business here after all</sup>

There is one more role, which is not a part of the business process but is a crucial member of the system - [Admin](src/main/java/lsit/Models/Admin.java). This entity is a necessary part of the process as it ensures we have a role that has the rights to all commands in case there is an issue present.

## About some entities in a little detail
The entire process starts with a [service request](src/main/java/lsit/Models/ServiceRequest.java), which the [client](src/main/java/lsit/Models/Client.java) provides to the mechanic shop. This request covers all the important parameters and data for the mechanic shop employees to do their job, such as:
1. ship model, class, engine type and power source.
2. dimensions of the ship (weight, length, height, width).
3. client id (the owner of the ship) and repair id.
4. the issue description from the client.

After the diagnostic team receives the request and handles the diagnostics of the issue, the [diagnostic assesment](src/main/java/lsit/Models/DiagnosticAssesment.java) entity is formed. This document covers the most important things to further proceed to the engineers, such as:
1. the requestId (so that we don't forget which star ship we are working on. <sup>we are a big mechanic shop</sup>).
2. assignedTeam (a problem is assigned to a specific team, as each of them have their specialties).
3. damageLevel **(enum LOW, SEVERE, CRITICAL)**, which is also necessary for the engineers to allocate their power and knowledge to specific issues, ones more important than others).
4. additionalCommentary (which covers any unusual information to be given to the engineers).

Moving on. After the engineers perform their magic on the start ship they must report about their work in written form, that is why [repair team report](src/main/java/lsit/Models/RepairTeamReport.java) exists. It covers:
1. the requestId (repeated from the previous step)
2. The service team presenting the report
3. solved and remaining issues
4. any additional notes to be mentioned in the final report

Finally, comes the assembler creating the [final report](src/main/java/lsit/Models/FinalReport.java). Written in understandable to a non-engineer mortal language this document covers:
1. The cost breakdown of the job done.
2. the state of the job done **(enum COMPLETED_ALL_CLEAR, COMPLETED_WITH_WARNINGS, COMPLETED_NEEDS_FOLLOWUP, FAILED_INTEGRATION)**.
3. remaining issues (if any left).
4. recommendations and final notes of how to take care of the star ship.