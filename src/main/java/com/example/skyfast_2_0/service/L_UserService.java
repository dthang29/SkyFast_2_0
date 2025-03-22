package com.example.skyfast_2_0.service;

import com.example.skyfast_2_0.dto.L_UserDTO;
import com.example.skyfast_2_0.entity.User;
import com.example.skyfast_2_0.repository.L_UserRepository;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class L_UserService {
    private final L_UserRepository userRepository;
    private final ModelMapper modelMapper;

    public L_UserService(L_UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public List<L_UserDTO> getAllActiveAndInactiveUsers() {
        List<User> users = userRepository.findByStatusIn(Arrays.asList("ACTIVE", "INACTIVE"));
        return users.stream().map(user -> modelMapper.map(user, L_UserDTO.class)).collect(Collectors.toList());
    }

    public L_UserDTO getUserById(Integer id) {
        Optional<User> user = userRepository.findById(id);
        L_UserDTO userDTO = user.map(value -> modelMapper.map(value, L_UserDTO.class)).orElse(null);

        if (userDTO != null) {
            System.out.println("UserDTO Date of Birth: " + userDTO.getDateOfBirth());
        } else {
            System.out.println("User not found!");
        }

        return userDTO;
    }


    // ... existing code ...
    public L_UserDTO createUser(L_UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);
        user.setCreatedAt(LocalDate.now()); // Đổi từ LocalDateTime.now() -> LocalDate.now()
        user.setUpdateAt(LocalDate.now());
        user.setStatus(userDTO.getStatus());
        userRepository.save(user);
        return modelMapper.map(user, L_UserDTO.class);
    }

// ... existing code ...

    public L_UserDTO updateUser(Integer id, L_UserDTO userDTO) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setUserName(userDTO.getUserName());
            user.setEmail(userDTO.getEmail());
            user.setFullName(userDTO.getFullName());
            user.setPhoneNumber(userDTO.getPhoneNumber());
            user.setAddress(userDTO.getAddress());
            user.setRole(userDTO.getRole());
            user.setDateOfBirth(userDTO.getDateOfBirth());
            user.setStatus(userDTO.getStatus());
            user.setUpdateAt(LocalDate.now());
            userRepository.save(user);
            return modelMapper.map(user, L_UserDTO.class);
        }
        return null;
    }


    public boolean deleteUser(Integer id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setStatus("INACTIVE");
            user.setUpdateAt(LocalDate.now()); // Cập nhật ngày sửa đổi
            userRepository.save(user);
            return true;
        }
        return false;
    }

}
