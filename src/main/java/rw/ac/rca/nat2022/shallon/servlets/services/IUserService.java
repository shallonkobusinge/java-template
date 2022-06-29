package rw.ac.rca.nat2022.shallon.servlets.services;

import rw.ac.rca.nat2022.shallon.servlets.dtos.CreateOrUpdateUserDTO;
import rw.ac.rca.nat2022.shallon.servlets.dtos.LoginDTO;
import rw.ac.rca.nat2022.shallon.servlets.models.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    User findById(Long id);
    User save(CreateOrUpdateUserDTO user);
    User update(CreateOrUpdateUserDTO user, Long id);
    User remove(Long id);
    List<User> findAll();

    String login(LoginDTO loginDTO);

    User register(CreateOrUpdateUserDTO dto);

    User profile();
}
