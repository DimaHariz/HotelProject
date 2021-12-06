package m2i.fr.hotel_project.repositories;

import m2i.fr.hotel_project.entities.ReservationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.sql.Timestamp;
import java.util.List;

public interface ReservationRepository extends PagingAndSortingRepository<ReservationEntity, Integer> {

    public List<ReservationEntity> findByClientContains(String search );

    // public Page<ReservationEntity> findByClientContains(String search , Pageable pageable);

    public Page<ReservationEntity> findAll(Pageable pageable);

   // public Iterable<ReservationEntity> findByDatedebBetween(Timestamp dateheure, Timestamp dateheure2);

}
