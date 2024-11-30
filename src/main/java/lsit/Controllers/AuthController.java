package lsit.Controllers;

import lsit.Models.*;
import lsit.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class AuthController {
    private final ClientRepository clientRepository;
    private final DiagnosticianRepository diagnosticianRepository;
    private final ElectricianRepository electricianRepository;
    private final MechanicRepository mechanicRepository;
    private final SoftwareSpecialistRepository softwareSpecialistRepository;
    private final AssemblerRepository assemblerRepository;

    @Autowired
    public AuthController(ClientRepository clientRepository,
                          DiagnosticianRepository diagnosticianRepository,
                          ElectricianRepository electricianRepository,
                          MechanicRepository mechanicRepository,
                          SoftwareSpecialistRepository softwareSpecialistRepository,
                          AssemblerRepository assemblerRepository) {

        this.clientRepository = clientRepository;
        this.diagnosticianRepository = diagnosticianRepository;
        this.electricianRepository = electricianRepository;
        this.mechanicRepository = mechanicRepository;
        this.softwareSpecialistRepository = softwareSpecialistRepository;
        this.assemblerRepository = assemblerRepository;
    }

    @GetMapping("/authentication")
        public String getUser(OAuth2AuthenticationToken authentication) throws Exception {

        var userAttributes = authentication.getPrincipal().getAttributes();

        ArrayList<String> userAuthorities = new ArrayList<>();
        for (var elem : authentication.getAuthorities()) {
            userAuthorities.add(elem.toString());
        }

        if (userAuthorities.contains("ROLE_CLIENT")) {
            Client client = new Client();

            client.userName = (String) userAttributes.get("nickname");
            client.id = Integer.parseInt((String) userAttributes.get("sub"));
            client.password = null; // set password to null, as we use token based authentication

            this.clientRepository.add(client);
            return "Client registered with following attributes. <br> ID: " + client.id + "<br>userName: " + client.userName;
        }

        else if (userAuthorities.contains("ROLE_DIAGNOSTICIAN")) {
            Diagnostician diagnostician = new Diagnostician();

            diagnostician.userName = (String) userAttributes.get("nickname");
            diagnostician.id = Integer.parseInt((String) userAttributes.get("sub"));
            diagnostician.password = null;

            this.diagnosticianRepository.add(diagnostician);
            return "Diagnostician registered with following attributes. <br> ID: " + diagnostician.id + "<br>userName: " + diagnostician.userName;
        }

        else if (userAuthorities.contains("ROLE_ELECTRICIAN")) {
            Electrician electrician = new Electrician();

            electrician.userName = (String) userAttributes.get("nickname");
            electrician.id = Integer.parseInt((String) userAttributes.get("sub"));
            electrician.password = null;

            this.electricianRepository.add(electrician);
            return "Diagnostician registered with following attributes. <br> ID: " + electrician.id + "<br>userName: " + electrician.userName;
        }

        else if (userAuthorities.contains("ROLE_MECHANIC")) {
            Mechanic mechanic = new Mechanic();
            mechanic.userName = (String) userAttributes.get("nickname");
            mechanic.id = Integer.parseInt((String) userAttributes.get("sub"));
            mechanic.password = null;

            this.mechanicRepository.add(mechanic);
            return "Mechanic registered with following attributes. <br> ID: " + mechanic.id + "<br>userName: " + mechanic.userName;
        }

        else if (userAuthorities.contains("ROLE_SOFTWARE_SPECIALIST")) {
            SoftwareSpecialist softwareSpecialist = new SoftwareSpecialist();
            softwareSpecialist.userName = (String) userAttributes.get("nickname");
            softwareSpecialist.id = Integer.parseInt((String) userAttributes.get("sub"));
            softwareSpecialist.password = null;

            this.softwareSpecialistRepository.add(softwareSpecialist);
            return "Software Specialist registered with following attributes. <br> ID: " + softwareSpecialist.id + "<br>userName: " + softwareSpecialist.userName;
        }

        else if (userAuthorities.contains("ROLE_ASSEMBLER")) {
            Assembler assembler = new Assembler();
            assembler.userName = (String) userAttributes.get("nickname");
            assembler.id = Integer.parseInt((String) userAttributes.get("sub"));
            assembler.password = null;

            this.assemblerRepository.add(assembler);
            return "Assembler registered with following attributes. <br> ID: " + assembler.id + "<br>userName: " + assembler.userName;
        }

        return userAttributes + "<br>" + userAuthorities;
    }

}
