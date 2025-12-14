package com.example.demo.mapper;

import com.example.demo.dto.UserDto;
import com.example.demo.dto.UserResponseDto;
import com.example.demo.entity.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Component
public class UserMapper {
    public User toEntity(UserDto dto) {
        User entity = new User();

        entity.setName(dto.getName());
        entity.setAge(dto.getAge());
        entity.setRole(dto.getRole());
        entity.setAddress(dto.getAddress());
        entity.setEmail(dto.getEmail());
        entity.setCreatedAt(LocalDateTime.now());
        return entity;
    }
    public UserResponseDto toDto(User Entity) {
        UserResponseDto dto = new UserResponseDto();

        dto.setId(Entity.getId());
        dto.setName(Entity.getName());
        dto.setEmail(Entity.getEmail());
        dto.setAge(Entity.getAge());
        dto.setAddress(Entity.getAddress());
        dto.setRole(Entity.getRole());

        return dto;
    }
    public List<UserResponseDto> toDtoList(List<User> entities) {
        if (entities == null || entities.isEmpty()) {
            return new ArrayList<>();
        }

        return entities.stream()
                .map(user -> this.toDto(user))
                .collect(Collectors.toList());
    }
}
