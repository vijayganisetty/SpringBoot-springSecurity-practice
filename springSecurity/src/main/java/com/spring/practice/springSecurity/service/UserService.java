package com.spring.practice.springSecurity.service;

import com.spring.practice.springSecurity.DTO.SignUpDTO;
import com.spring.practice.springSecurity.DTO.UserDTO;
import com.spring.practice.springSecurity.entity.UserEntity;
import com.spring.practice.springSecurity.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
        return userRepository.findByEmail(mail).orElseThrow(
                () -> new RuntimeException("User with mail "+ mail + " not found")
        );
    }

    public UserDTO getUserByUserId(Long id){
        UserEntity userEntity = userRepository.findById(id).orElseThrow(
                () -> new RuntimeException("User with id "+ id + " not found")
        );
        return modelMapper.map(userEntity, UserDTO.class);
    }

    public UserDTO signUp(SignUpDTO signUpDTO) {
         Optional<UserEntity> user = userRepository.findByEmail(signUpDTO.getEmail());
         if(user.isPresent()){
             throw new BadCredentialsException("User with email already exists "+ signUpDTO.getEmail());
         }
         UserEntity toBeCreateUser = modelMapper.map(signUpDTO, UserEntity.class);
         toBeCreateUser.setPassword(passwordEncoder.encode(toBeCreateUser.getPassword()));
         UserEntity createdUser = userRepository.save(toBeCreateUser);
         return modelMapper.map(createdUser, UserDTO.class);
    }
}
