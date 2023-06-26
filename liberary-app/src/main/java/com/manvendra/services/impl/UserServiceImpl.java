package com.manvendra.services.impl;

import com.manvendra.dtos.UserRequest;
import com.manvendra.models.Role;
import com.manvendra.models.User;
import com.manvendra.repository.RoleRepository;
import com.manvendra.repository.UserRepository;
import com.manvendra.services.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final RoleRepository roleRepository;

    @Override
    public void createUser(UserRequest userRequest) {
        String encodedPassword = passwordEncoder.encode(userRequest.getPassword());
        userRequest.setPassword(encodedPassword);
        User user = modelMapper.map(userRequest, User.class);
        Role role = roleRepository.findById(1002).orElse(new Role(1002, "ROLE_NORMAL"));
        user.getRoles().add(role);
        userRepository.save(user);
    }
}
