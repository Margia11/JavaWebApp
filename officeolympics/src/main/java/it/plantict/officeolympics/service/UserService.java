package it.plantict.officeolympics.service;

import it.plantict.officeolympics.dto.UserDTO;
import it.plantict.officeolympics.exceptions.MyDuplicateKeyException;
import it.plantict.officeolympics.models.UserRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface UserService {
    long getCountAllUsers();
    List<UserDTO> getAllUsers(int page, int size);
    UserDTO getUserByUsername(String username);
    UserDTO createUser(UserRequest userRequest) throws MyDuplicateKeyException;
    UserDTO updateUserById(UUID id, UserRequest userRequest);
    void removeUserByUsername(String username);
}