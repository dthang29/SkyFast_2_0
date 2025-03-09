package com.example.skyfast_2_0.service;

import com.example.skyfast_2_0.dto.L_UserDTO;
import com.example.skyfast_2_0.entity.User;
import com.example.skyfast_2_0.repository.L_UserRepository;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class L_UserService {
    private final L_UserRepository LUserRepository;
    private final ModelMapper modelMapper;

    public L_UserService(L_UserRepository LUserRepository, ModelMapper modelMapper) {
        this.LUserRepository = LUserRepository;
        this.modelMapper = modelMapper;
    }

    public List<L_UserDTO> getAllActiveAndInactiveUsers() {
        List<User> users = LUserRepository.findByStatusIn(Arrays.asList("ACTIVE", "INACTIVE"));
        return users.stream().map(user -> modelMapper.map(user, L_UserDTO.class)).collect(Collectors.toList());
    }

    public L_UserDTO getUserById(Integer id) {
        Optional<User> user = LUserRepository.findById(id);
        return user.map(value -> modelMapper.map(value, L_UserDTO.class)).orElse(null);
    }

    // ... existing code ...
    public L_UserDTO createUser(L_UserDTO LUserDTO) {
        User user = modelMapper.map(LUserDTO, User.class);
        user.setCreatedAt(LocalDate.now()); // Đổi từ LocalDateTime.now() -> LocalDate.now()
        user.setUpdateAt(LocalDate.now());
        user.setStatus(LUserDTO.getStatus());
        LUserRepository.save(user);
        return modelMapper.map(user, L_UserDTO.class);
    }

// ... existing code ...

    public L_UserDTO updateUser(Integer id, L_UserDTO LUserDTO) {
        Optional<User> optionalUser = LUserRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setUserName(LUserDTO.getUserName());
            user.setEmail(LUserDTO.getEmail());
            user.setFullName(LUserDTO.getFullName());
            user.setPhoneNumber(LUserDTO.getPhoneNumber());
            user.setAddress(LUserDTO.getAddress());
            user.setRole(LUserDTO.getRole());
            user.setDateOfBirth(LUserDTO.getDateOfBirth());
            user.setStatus(LUserDTO.getStatus());
            user.setUpdateAt(LocalDate.now()); // Cập nhật ngày sửa đổi
            LUserRepository.save(user);
            return modelMapper.map(user, L_UserDTO.class);
        }
        return null;
    }


    public boolean deleteUser(Integer id) {
        Optional<User> optionalUser = LUserRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setStatus("INACTIVE");
            user.setUpdateAt(LocalDate.now()); // Cập nhật ngày sửa đổi
            LUserRepository.save(user);
            return true;
        }
        return false;
    }

}
