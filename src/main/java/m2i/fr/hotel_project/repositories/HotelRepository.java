package m2i.fr.hotel_project.repositories;

import m2i.fr.hotel_project.entities.HotelEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface HotelRepository extends PagingAndSortingRepository<HotelEntity, Integer> {

    public List<HotelEntity> findByNomContains(String search );

    public Page<HotelEntity> findByNomContains(String search , Pageable pageable);

    public Page<HotelEntity> findAll(Pageable pageable);

}
