package m2i.fr.hotel_project.security;

import m2i.fr.hotel_project.entities.AdminEntity;
import m2i.fr.hotel_project.repositories.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Check si le user existe en bd
        AdminEntity admin = adminRepository.findByUsername(username);

        if(admin == null) {
            throw new UsernameNotFoundException("No admin " + username);
        } else {
            return new UserDetailsImpl(admin);
        }
    }

}