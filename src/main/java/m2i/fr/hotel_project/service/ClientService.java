

package m2i.fr.hotel_project.service;

import m2i.fr.hotel_project.entities.ClientEntity;
import m2i.fr.hotel_project.repositories.ClientRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.InvalidObjectException;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ClientService {

    private ClientRepository cr;

    public ClientService(ClientRepository cr ){
        this.cr = cr;
    }

    public Iterable<ClientEntity> findAll(  ) {
        return cr.findAll();
    }

    public Iterable<ClientEntity> findAll(String search  ) {
        if( search != null && search.length() > 0 ){
            return cr.findByNomCompletContains(search);
        }
        return cr.findAll();
    }

    public Page<ClientEntity> findAllByPage(Integer pageNo, Integer pageSize , String search  ) {
        Pageable paging = PageRequest.of(pageNo, pageSize);

        if( search != null && search.length() > 0 ){
            return cr.findByNomCompletContains(search, paging );
        }

        return cr.findAll( paging );
    }

    public static boolean validate(String emailStr) {
        Pattern VALID_EMAIL_ADDRESS_REGEX =
                Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

    private void checkClient( ClientEntity c ) throws InvalidObjectException {

        if (c.getNomComplet().length() <= 5) {
            throw new InvalidObjectException("Nom invalide");
        }

        if (c.getTelephone().length() <= 8) {
            throw new InvalidObjectException("Téléphone invalide");
        }

        if (c.getEmail().length() <= 5 || !validate(c.getEmail())) {
            throw new InvalidObjectException("Email invalide");
        }
        if (c.getAdresse().length() <= 10) {
            throw new InvalidObjectException("Adresse invalide");
        }

    }

    public ClientEntity findClient(int id) {
        return cr.findById(id).get();
    }

    public void addClient( ClientEntity c ) throws InvalidObjectException {
        checkClient(c);
        cr.save(c);
    }

    public void delete(int id) {
        cr.deleteById(id);
    }

    public void editClient( int id , ClientEntity c) throws InvalidObjectException , NoSuchElementException {
        checkClient(c);
        try{
            ClientEntity cExistant = cr.findById(id).get();

            cExistant.setNomComplet(c.getNomComplet());
            cExistant.setTelephone(c.getTelephone());
            cExistant.setEmail(c.getEmail());
            cExistant.setAdresse(c.getAdresse());
            cr.save( cExistant );

        }catch ( NoSuchElementException e ){
            throw e;
        }

    }

}


