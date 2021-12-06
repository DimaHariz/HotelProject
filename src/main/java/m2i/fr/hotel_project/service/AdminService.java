package m2i.fr.hotel_project.service;

import m2i.fr.hotel_project.entities.AdminEntity;
import m2i.fr.hotel_project.repositories.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.InvalidObjectException;
import java.util.NoSuchElementException;

@Service
public class AdminService {

    private AdminRepository ar;

    @Autowired
    private PasswordEncoder encoder;

    public AdminService( AdminRepository ar ){
        this.ar = ar;
    }

    public Iterable<AdminEntity> findAll(  ) {
        return ar.findAll();
    }

    public Iterable<AdminEntity> findAll(  String search  ) {
        if( search != null && search.length() > 0 ){
            return ar.findByUsernameContains((search));
        }
        return ar.findAll();
    }

    public Page<AdminEntity> findAllByPage(Integer pageNo, Integer pageSize , String search  ) {
        Pageable paging = PageRequest.of(pageNo, pageSize);

        if( search != null && search.length() > 0 ){
            return ar.findByUsernameContains(search, paging );
        }

        return ar.findAll( paging );
    }


private void checkAdmin( AdminEntity a ) throws InvalidObjectException {

        if( a.getUsername().length() <= 3  ){
            throw new InvalidObjectException("Nom de l'hotel invalide");
        }

        if( a.getPassword().length() <= 8  ){
            throw new InvalidObjectException("Adresse de l'hotel invalide");
        }

    }



    public AdminEntity findAdmin(int id) {
        return ar.findById(id).get();
    }


    public void addAdmin( AdminEntity a ) throws InvalidObjectException {
        a.setPassword(encoder.encode(a.getPassword()));
        checkAdmin(a);
        ar.save(a);
    }


    public void delete(int id) {
        ar.deleteById(id);
    }

    public void editAdmin( int id , AdminEntity a) throws InvalidObjectException , NoSuchElementException {
        checkAdmin(a);
        try{
            AdminEntity aExistant = ar.findById(id).get();

            aExistant.setUsername(a.getUsername());
            aExistant.setPassword(a.getPassword());
            aExistant.setRole(a.getRole());

            if( a.getPassword().length() > 0 ){
                aExistant.setPassword( encoder.encode( a.getPassword() ) );
            }

            ar.save( aExistant );

        }catch ( NoSuchElementException e ){
            throw e;
        }

    }
}
