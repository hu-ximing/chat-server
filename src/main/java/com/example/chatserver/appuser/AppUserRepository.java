package com.example.chatserver.appuser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    Optional<AppUser> findByUsername(String username);

    @Query(value = "SELECT * FROM app_user " +
            "WHERE display_name REGEXP :displayNameRegex",
            nativeQuery = true)
    List<AppUser> findByDisplayNameRegex(String displayNameRegex);
}
