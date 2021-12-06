package m2i.fr.hotel_project.repositories;


import m2i.fr.hotel_project.entities.AdminEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;

import java.util.List;

//@Component
public interface AdminRepository extends PagingAndSortingRepository<AdminEntity, Integer> {

    public AdminEntity findByUsername(String Username); //username

    public List<AdminEntity> findByUsernameContains(String search );

    public Page<AdminEntity> findByUsernameContains(String search , Pageable pageable);

    public Page<AdminEntity> findAll(Pageable pageable);

}

