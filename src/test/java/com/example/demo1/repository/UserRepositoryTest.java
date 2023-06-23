package com.example.demo1.repository;

import com.example.demo1.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;
    @Test
    public void findOneUserByIdTest(){
        UserEntity userEntity = new UserEntity();
        userEntity.setUserName("findOneUserByIdTest");
        userEntity.setPassword("12345678");
        userEntity.setStatus(0);
        userEntity.setRoles(null);
        userRepository.save(userEntity);
        UserEntity findedUser = userRepository.findOneById(userEntity.getId());
        org.assertj.core.api.Assertions.assertThat(findedUser).isNotNull();
    }

}