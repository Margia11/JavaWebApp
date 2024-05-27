package it.plantict.officeolympics.service;

import it.plantict.officeolympics.dto.UserDTO;
import it.plantict.officeolympics.entities.UserEntity;
import it.plantict.officeolympics.exceptions.MyDuplicateKeyException;
import it.plantict.officeolympics.mapper.UserMapper;
import it.plantict.officeolympics.models.UserRequest;
import it.plantict.officeolympics.repository.RoleRepository;
import it.plantict.officeolympics.repository.UserRepository;
import it.plantict.officeolympics.utils.Utils;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    private final Logger logger = LogManager.getLogger(ChallengeServiceImpl.class);

    @Override
    public long getCountAllUsers() {
        return userRepository.count();
    }

    @Override
    public List<UserDTO> getAllUsers(int page, int size) {
        Pageable pageRequest = PageRequest.of(page, size);
        Page<UserEntity> users = userRepository.findAll(pageRequest);
        return UserMapper.MAPPER.mapToDTOs(users.stream().toList());
    }

    @Override
    public UserDTO getUserByUsername(String username) {
        UserEntity userEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User with name " + username + " not found"));
        return UserMapper.MAPPER.toDTO(userEntity);
    }

    @Transactional
    @Override
    public UserDTO createUser(UserRequest userRequest) throws MyDuplicateKeyException {
        if (userRequest.getUsername() == null || userRequest.getUsername().isEmpty()) {
            logger.error("username cannot be null or empty");
            throw new IllegalArgumentException("username cannot be null or empty");
        }
        if (userRequest.getFirstName() == null || userRequest.getFirstName().isEmpty()) {
            logger.error("firstName cannot be null or empty");
            throw new IllegalArgumentException("firstName cannot be null or empty");
        }
        if (userRequest.getLastName() == null || userRequest.getLastName().isEmpty()) {
            logger.error("lastName cannot be null or empty");
            throw new IllegalArgumentException("lastName cannot be null or empty");
        }
        if (userRequest.getEmail() == null || userRequest.getEmail().isEmpty()) {
            logger.error("email cannot be null or empty");
            throw new IllegalArgumentException("email cannot be null or empty");
        }
        if (userRequest.getOperator() == null || userRequest.getOperator().isEmpty()) {
            logger.error("operator cannot be null or empty");
            throw new IllegalArgumentException("operator cannot be null or empty");
        }
        if (userRequest.getIdRole() == null) {
            logger.error("idRole cannot be null or empty");
            throw new IllegalArgumentException("idRole cannot be null or empty");
        }
        UserEntity newUserEntity = new UserEntity();
        Optional<UserEntity> usernameToCheck = userRepository.findByUsername(userRequest.getUsername());
        if (usernameToCheck.isPresent()) {
            logger.error("Username {} already exist", usernameToCheck);
            throw new MyDuplicateKeyException("Username cannot be duplicate");
        }
        newUserEntity.setUsername(userRequest.getUsername());
        newUserEntity.setFirstName(userRequest.getFirstName());
        newUserEntity.setLastName(userRequest.getLastName());
        newUserEntity.setEmail(userRequest.getEmail());
        newUserEntity.setAvatar(userRequest.getAvatar());
        newUserEntity.setOperator(userRequest.getOperator());
        newUserEntity.setLastUpdate(Utils.getCurrentTimestamp());
        newUserEntity.setRole(roleRepository.findById(userRequest.getIdRole())
                .orElseThrow(() -> new EntityNotFoundException("Role not found")));
        newUserEntity = userRepository.save(newUserEntity);
        logger.info("User created with id {}", newUserEntity.getIdUser());
        return UserMapper.MAPPER.toDTO(newUserEntity);
    }

    @Transactional
    @Override
    public UserDTO updateUserById(UUID id, UserRequest userRequest) {
        UserEntity userToUpdate = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
        if (userRequest.getFirstName() != null && !userRequest.getFirstName().isEmpty())
            userToUpdate.setFirstName(userRequest.getFirstName());
        if (userRequest.getLastName() != null && !userRequest.getLastName().isEmpty())
            userToUpdate.setLastName(userRequest.getLastName());
        if (userRequest.getEmail() != null && !userRequest.getEmail().isEmpty())
            userToUpdate.setEmail(userRequest.getEmail());
        if (userRequest.getAvatar() != null && !userRequest.getAvatar().isEmpty())
            userToUpdate.setAvatar(userRequest.getAvatar());
        userToUpdate.setLastUpdate(Utils.getCurrentTimestamp());
        userToUpdate = userRepository.save(userToUpdate);
        return UserMapper.MAPPER.toDTO(userToUpdate);
    }

    @Transactional
    @Override
    public void removeUserByUsername(String username) {
        Optional<UserEntity> userNameToCheck = userRepository.findByUsername(username);
        if (userNameToCheck.isEmpty()) {
            logger.error("No User found with username: {}", username);
            throw new EntityNotFoundException("No User found with username: " + username);
        }
        userRepository.removeByUsername(username);
    }
}
