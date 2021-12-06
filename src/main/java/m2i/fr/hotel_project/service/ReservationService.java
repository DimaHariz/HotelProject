package m2i.fr.hotel_project.service;

import m2i.fr.hotel_project.entities.ReservationEntity;
import m2i.fr.hotel_project.repositories.ClientRepository;
import m2i.fr.hotel_project.repositories.HotelRepository;
import m2i.fr.hotel_project.repositories.ReservationRepository;
import org.springframework.stereotype.Service;

import java.io.InvalidObjectException;
import java.util.NoSuchElementException;

@Service
public class ReservationService {

    private ReservationRepository rr;
    private ClientRepository cr;
    private HotelRepository hr;


    public ReservationService(ReservationRepository rr, ClientRepository cr, HotelRepository hr){
        this.rr = rr;
        this.cr = cr;
        this.hr = hr;
    }

    public Iterable<ReservationEntity> findAll() {
        return rr.findAll();
    }

    public Iterable<ReservationEntity> findAll( String search ) {
        if( search != null && search.length() > 0 ){
            return rr.findByClientContains(search);
        }
        return rr.findAll();
    }

    public ReservationEntity findReservation(int id) {
        return rr.findById(id).get();
    }

    public void delete(int id) {
        rr.deleteById(id);
    }

   /* private void checkReservation( ReservationEntity r) throws Exception {

        *//*if( rdvRepo.findByDateheure( v.getDateheure() ).iterator().hasNext() ){
            throw new Exception("Rdv Existe déjà");
        }*//*
        Timestamp after30min = (Timestamp) r.getDatedeb().clone();
        after30min.setTime(after30min.getTime() + TimeUnit.MINUTES.toMillis(15));

        Timestamp before30min = (Timestamp) r.getDatedeb().clone();
        before30min.setTime(after30min.getTime() - TimeUnit.MINUTES.toMillis(15));

        System.out.println( "Je check les reservations entre " + before30min + " et " + after30min );

        Iterable<ReservationEntity> retourCheck = r.findByDatedebBetween( before30min , after30min );

        if( retourCheck.iterator().hasNext() ){
            throw new Exception("Rdv en conflit avec d'autres rdv");
        }

    }*/

    public void addReservation(ReservationEntity r) throws InvalidObjectException {
       // checkReservation(r);
        rr.save(r);
    }

    public void editReservation(int id, ReservationEntity r) throws InvalidObjectException {
        // checkReservation(r);

        try{
            ReservationEntity rExistante = rr.findById(id).get();

            rExistante.setDatedeb(r.getDatedeb());
            rExistante.setDatefin(r.getDatefin());
            rExistante.setNumChambre(r.getNumChambre());
            rExistante.setClient(r.getClient());
            rExistante.setHotel(r.getHotel());

            rr.save( rExistante );

        }catch ( NoSuchElementException e ){
            throw e;
        }
    }
}
