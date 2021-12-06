package m2i.fr.hotel_project.api;

import m2i.fr.hotel_project.entities.AdminEntity;
import m2i.fr.hotel_project.repositories.AdminRepository;
import m2i.fr.hotel_project.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Base64;

@RestController
public class LoginAPIController {

    @Autowired
    private AdminRepository ar;

    @Autowired
    private PasswordEncoder encoder;


    @Autowired
    private AdminService as;

    @PostMapping( value = "/api/login" ,  consumes = "application/json" ,  produces = "application/json")
    public ResponseEntity<AdminEntity> get(@RequestBody  AdminEntity a) {

        AdminEntity admin = ar.findByUsername(a.getUsername());

        if(admin == null) {
            return ResponseEntity.notFound().build();
        } else {
            System.out.println( "encoded pass : " + a.getPassword() );
            System.out.println( "pass en bd : " + admin.getPassword() );

            // user exists
            if( encoder.matches( a.getPassword() , admin.getPassword() ) ){
                String encoding = Base64.getEncoder().encodeToString((a.getUsername()+":"+a.getPassword()).getBytes());
                admin.setPassword(encoding);

                return ResponseEntity.ok(admin);
            }
            return ResponseEntity.badRequest().build();
        }

    }

}