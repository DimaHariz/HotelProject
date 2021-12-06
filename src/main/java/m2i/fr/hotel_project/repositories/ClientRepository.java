

package m2i.fr.hotel_project.repositories;

import m2i.fr.hotel_project.entities.ClientEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ClientRepository extends PagingAndSortingRepository<ClientEntity, Integer> {

    public List<ClientEntity> findByNomCompletContains(String search );

    public Page<ClientEntity> findByNomCompletContains(String search , Pageable pageable);

    public Page<ClientEntity> findAll(Pageable pageable);

}


