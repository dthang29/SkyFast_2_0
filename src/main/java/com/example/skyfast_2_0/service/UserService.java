package com.example.skyfast_2_0.service;

import com.example.skyfast_2_0.dto.UserDTO;
import com.example.skyfast_2_0.entity.User;
import com.example.skyfast_2_0.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public List<UserDTO> getAllActiveAndInactiveUsers() {
        List<User> users = userRepository.findByStatusIn(Arrays.asList("ACTIVE", "INACTIVE"));
        return users.stream().map(user -> modelMapper.map(user, UserDTO.class)).collect(Collectors.toList());
    }

    public UserDTO getUserById(Integer id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(value -> modelMapper.map(value, UserDTO.class)).orElse(null);
    }

    // ... existing code ...
    public UserDTO createUser(UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);
        user.setCreatedAt(LocalDate.now());
        user.setUpdateAt(LocalDate.now());
        user.setStatus(userDTO.getStatus()); // Đảm bảo gán đúng giá trị status
        userRepository.save(user);
        return modelMapper.map(user, UserDTO.class);
    }
// ... existing code ...

    public UserDTO updateUser(Integer id, UserDTO userDTO) {
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
            return modelMapper.map(user, UserDTO.class);
        }
        return null;
    }

    public boolean deleteUser(Integer id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setStatus("INACTIVE"); // Thay đổi trạng thái thành INACTIVE
            user.setUpdateAt(LocalDate.now()); // Cập nhật thời gian sửa đổi
            userRepository.save(user); // Lưu lại thay đổi
            return true;
        }
        return false;
    }
}
