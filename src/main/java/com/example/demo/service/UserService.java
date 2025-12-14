package com.example.demo.service;

import com.example.demo.dto.UserResponseDto;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.BaseResponseModel;
import com.example.demo.model.BaseResponseWithDataModel;
import com.example.demo.dto.UserDto;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper mapper;


    public ResponseEntity<BaseResponseWithDataModel> listUsers() {
        List<User> users = userRepository.findAll();
        List<UserResponseDto> dtos = mapper.toDtoList(users);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseWithDataModel("success","successfully retrieve users",dtos));
    }
    public ResponseEntity<BaseResponseWithDataModel> getUser(Long userId){
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new BaseResponseWithDataModel("fail","user not found with id :"
                            + userId,null));
        }

        UserResponseDto dto = mapper.toDto(user.get());

        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseWithDataModel("success","user found",dto));
    }
    public ResponseEntity<BaseResponseModel> createUser(UserDto payload) {
        User user = mapper.toEntity(payload);

        userRepository.save(user);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new BaseResponseModel("success","successfully created user"));
    }
    public ResponseEntity<BaseResponseModel> updateUser(UserDto payload, Long userId) {
        Optional<User> existing = userRepository.findById(userId);
        // if user not found then reponse 404
            if(existing.isEmpty()) {
               return ResponseEntity.status(HttpStatus.NOT_FOUND)
                       .body(new BaseResponseModel("fail","user not found with id: " + userId));
            }
        //modify values
        User updatedUser = existing.get();
            updatedUser.setEmail(payload.getEmail());
            updatedUser.setName(payload.getName());
            updatedUser.setAge(payload.getAge());
            updatedUser.setAddress(payload.getAddress());
            updatedUser.setRole(payload.getRole());
            userRepository.save(updatedUser);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseModel("success","successfully update user"));
    }
   
    //delete user method
    // Business logic for user management can be added here
    public ResponseEntity<BaseResponseModel> deleteUser(Long userId) {


        if (!userRepository.existsById(userId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new BaseResponseModel("fail","user not found with id :"+ userId));
        }
        // user not found , then delete
        userRepository.deleteById(userId);
        // 200 ok
        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponseModel("success", "User deleted successfully"));
    }
}
