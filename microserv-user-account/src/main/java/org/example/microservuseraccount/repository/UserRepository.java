package org.example.microservuseraccount.repository;

import org.example.microservuseraccount.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
