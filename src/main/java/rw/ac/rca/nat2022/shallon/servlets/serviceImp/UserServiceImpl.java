package rw.ac.rca.nat2022.shallon.servlets.serviceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import rw.ac.rca.nat2022.shallon.servlets.dtos.CreateOrUpdateUserDTO;
import rw.ac.rca.nat2022.shallon.servlets.dtos.LoginDTO;
import rw.ac.rca.nat2022.shallon.servlets.models.User;
import rw.ac.rca.nat2022.shallon.servlets.repository.IUserRepository;
import rw.ac.rca.nat2022.shallon.servlets.services.IUserService;
import rw.ac.rca.nat2022.shallon.servlets.utils.ResourceNotFoundException;
import rw.ac.rca.nat2022.shallon.servlets.utils.Utility;
import rw.ac.rca.nat2022.shallon.servlets.utils.security.JwtTokenProvider;

import java.util.List;

@Service
public class UserServiceImpl implements IUserService {
    private final IUserRepository userRepository;

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public UserServiceImpl(IUserRepository userRepository, AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceAccessException("User with id " + id.toString() + " not found"));
    }

    @Override
    public User save(CreateOrUpdateUserDTO user) {
        return userRepository.save(new User(user));
    }

    @Override
    public User update(CreateOrUpdateUserDTO user, Long id) {
        return null;
    }


    @Override
    public User remove(Long id) {
        return null;
    }

    @Override
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public String login(LoginDTO dto) {
        String jwt = null;

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getLogin(), dto.getPassword()));

        try {
            SecurityContextHolder.getContext().setAuthentication(authentication);

            jwt = jwtTokenProvider.generateToken(authentication);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return jwt;
    }

    @Override
    public User register(CreateOrUpdateUserDTO dto) {
        User user = save(dto);
        user.setPassword(Utility.encodePassword(dto.getPassword()));

        return userRepository.save(user);
    }

    @Override
    public User profile() {
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() == "anonymousUser")
            throw new ResourceNotFoundException("You are not logged in, try to log in");

        String email;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            email = ((UserDetails) principal).getUsername();
        } else {
            email = principal.toString();
        }

        return userRepository.findByEmailOrUsername(email, email).orElseThrow(() -> new ResourceNotFoundException("User", "email", email));
    }
}
