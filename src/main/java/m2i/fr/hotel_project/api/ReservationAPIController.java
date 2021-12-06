package m2i.fr.hotel_project.api;

import m2i.fr.hotel_project.entities.ReservationEntity;
import m2i.fr.hotel_project.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.InvalidObjectException;
import java.net.URI;
import java.util.NoSuchElementException;

@RestController()
@RequestMapping("/api/reservation")

public class ReservationAPIController {

    @Autowired
    ReservationService rs;

    @GetMapping(value="" , produces = "application/json")
    public Iterable<ReservationEntity> getAll( HttpServletRequest request ){
        String search = request.getParameter("search");
        System.out.println( "Recherche de resa avec param = " + search );
        return rs.findAll( search );

    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<ReservationEntity> get(@PathVariable int id) {
        try{
            ReservationEntity r = rs.findReservation(id);
            return ResponseEntity.ok(r);
        }catch ( Exception e ){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(value="" , consumes = "application/json")
    public ResponseEntity<ReservationEntity> add(@RequestBody ReservationEntity r){
        try{
            rs.addReservation( r );

            // création de l'url d'accès au nouvel objet => http://localhost:8082/api/reservation/20
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand( r.getId() ).toUri();

            return ResponseEntity.created( uri ).body(r);

        }catch ( InvalidObjectException e ){
            //return ResponseEntity.badRequest().build();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST , e.getMessage() );
        }
    }

    @PutMapping(value="/{id}" , consumes = "application/json")
    public void update( @PathVariable int id , @RequestBody ReservationEntity r ){
        try{
            rs.editReservation( id , r );

        }catch ( NoSuchElementException e ){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND , "Reservation introuvable" );

        }catch ( InvalidObjectException e ){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST , e.getMessage() );
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> delete(@PathVariable int id) throws Exception {
        try{
            rs.delete(id);
            return ResponseEntity.ok(null);
        }catch ( Exception e ){
            return ResponseEntity.notFound().build();
        }

    }
}