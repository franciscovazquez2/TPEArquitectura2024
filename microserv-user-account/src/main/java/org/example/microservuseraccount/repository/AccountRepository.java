package org.example.microservuseraccount.repository;

import org.example.microservuseraccount.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account,Long> {
}
