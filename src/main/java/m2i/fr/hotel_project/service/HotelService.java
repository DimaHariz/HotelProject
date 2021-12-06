package m2i.fr.hotel_project.service;

import m2i.fr.hotel_project.entities.HotelEntity;
import m2i.fr.hotel_project.repositories.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.InvalidObjectException;
import java.util.NoSuchElementException;

@Service
public class HotelService {

    private HotelRepository hr;

    public HotelService( HotelRepository hr ){
        this.hr = hr;
    }

    public Iterable<HotelEntity> findAll(  ) {
        return hr.findAll();
    }

    public Iterable<HotelEntity> findAll(  String search  ) {
        if( search != null && search.length() > 0 ){
            return hr.findByNomContains(search);
        }
        return hr.findAll();
    }

    public Page<HotelEntity> findAllByPage(Integer pageNo, Integer pageSize , String search  ) {
        Pageable paging = PageRequest.of(pageNo, pageSize);

        if( search != null && search.length() > 0 ){
            return hr.findByNomContains(search, paging );
        }

        return hr.findAll( paging );
    }

    private void checkHotel( HotelEntity h ) throws InvalidObjectException {

        if( h.getNom().length() <= 2  ){
            throw new InvalidObjectException("Nom de l'hotel invalide");
        }

        if( h.getAdresse().length() <= 10  ){
            throw new InvalidObjectException("Adresse de l'hotel invalide");
        }

    }

    public HotelEntity findHotel(int id) {
        return hr.findById(id).get();
    }

    public void addHotel( HotelEntity h ) throws InvalidObjectException {
        checkHotel(h);
        hr.save(h);
    }

    public void delete(int id) {
        hr.deleteById(id);
    }

    public void editHotel( int id , HotelEntity h) throws InvalidObjectException , NoSuchElementException {
        checkHotel(h);
        try{
            HotelEntity hExistant = hr.findById(id).get();

           hExistant.setNom(h.getNom());
           hExistant.setEtoiles(h.getEtoiles());
           hExistant.setAdresse(h.getAdresse());
           hExistant.setTelephone(h.getTelephone());
           hExistant.setEmail(h.getEmail());
           hExistant.setVille(h.getVille());
           hr.save( hExistant );

        }catch ( NoSuchElementException e ){
            throw e;
        }

    }
}
