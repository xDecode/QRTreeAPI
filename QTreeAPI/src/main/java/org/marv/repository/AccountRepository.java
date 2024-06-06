package org.marv.repository;

import org.marv.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, String> {

    @Modifying
    @Query("UPDATE QRCode q SET q.isActive = false WHERE q.id = :id")
    void deactivateAccount(@Param("id") String id);

    void deleteById(String id);

    Account findByUsername(String username);

    Optional<Account> findById(String id);

    boolean existsByUsername(String username);

}