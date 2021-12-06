package m2i.fr.hotel_project.api;

import m2i.fr.hotel_project.entities.AdminEntity;
import m2i.fr.hotel_project.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.InvalidObjectException;
import java.net.URI;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/admin")
public class AdminAPIController {

    @Autowired
    AdminService as;

    public AdminAPIController(AdminService as ){
        this.as = as;
    }

    @GetMapping(value="" , produces = "application/json")
    public Iterable<AdminEntity> getAll(HttpServletRequest request ){
        String search = request.getParameter("search");
        System.out.println( "Recherche de client avec param = " + search );
        return as.findAll( search );

    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<AdminEntity> get(@PathVariable int id) {
        try{
            AdminEntity a = as.findAdmin(id);
            return ResponseEntity.ok(a);
        }catch ( Exception e ){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(value="" , consumes = "application/json")
    public ResponseEntity<AdminEntity> add( @RequestBody AdminEntity a ){
        System.out.println( a );
        try{
            as.addAdmin( a );

            // création de l'url d'accès au nouvel objet => http://localhost:8080/api/client/20
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand( a.getId() ).toUri();

            return ResponseEntity.created( uri ).body(a);

        }catch ( InvalidObjectException e ){
            //return ResponseEntity.badRequest().build();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST , e.getMessage() );
        }

    }

    @PutMapping(value="/{id}" , consumes = "application/json")
    public void update( @PathVariable int id , @RequestBody AdminEntity a ){
        try{
            as.editAdmin( id , a);

        }catch ( NoSuchElementException e ){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND , "Admin introuvable" );

        }catch ( InvalidObjectException e ){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST , e.getMessage() );
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> delete(@PathVariable int id) {
        // Check sur l'existance du client, si ko => 404 not found
        try{
            AdminEntity a = as.findAdmin(id);
        }catch( Exception e ){
            return ResponseEntity.notFound().build();
        }

        // si on a un problème à ce niveau => sql exception
        try{
            as.delete(id);
            return ResponseEntity.ok(null);
        }catch( Exception e ) {
            return ResponseEntity.badRequest().build();
        }
    }

}
