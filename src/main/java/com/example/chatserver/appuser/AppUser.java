package com.example.chatserver.appuser;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class AppUser {
    @Id
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @JsonIgnore
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String displayName;

    private LocalDate birthDate;

    @JsonIgnore
    @ManyToMany
    private List<AppUser> friends = new ArrayList<>();

    public AppUser(String displayName, String username, String password, LocalDate birthDate) {
        this.displayName = displayName;
        this.username = username;
        this.password = password;
        this.birthDate = birthDate;
    }
}
