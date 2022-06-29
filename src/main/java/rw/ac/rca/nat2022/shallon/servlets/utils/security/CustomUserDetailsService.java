package rw.ac.rca.nat2022.shallon.servlets.utils.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import rw.ac.rca.nat2022.shallon.servlets.models.User;
import rw.ac.rca.nat2022.shallon.servlets.repository.IUserRepository;
import rw.ac.rca.nat2022.shallon.servlets.utils.ResourceNotFoundException;

import javax.transaction.Transactional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final IUserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmailOrUsername(username, username).orElseThrow(() -> new ResourceNotFoundException("user not found with email or mobile of " + username));
        return UserPrinciple.create(user);
    }

    @Transactional
    public UserDetails loadByUserId(Long id) {
        User user = this.userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + id));
        return UserPrinciple.create(user);
    }
}
