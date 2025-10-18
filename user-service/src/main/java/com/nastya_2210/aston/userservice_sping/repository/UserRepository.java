package com.nastya_2210.aston.userservice_sping.repository;

import com.nastya_2210.aston.userservice_sping.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {

}
