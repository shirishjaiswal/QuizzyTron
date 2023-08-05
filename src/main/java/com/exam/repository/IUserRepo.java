package com.exam.repository;

import com.exam.module.UserEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepo extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUserName(String userName);

    UserEntity findByToken(String token);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "UPDATE user SET token = :token WHERE user_Name = :user_Name")
    public int addToken(@Param("token") String token, @Param("user_Name") String user_Name);
}
