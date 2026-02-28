package org.example.bogo.domain.member.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "nickname", unique = true, length = 50)
    private String nickname;

    @Column(nullable = false, name = "password", length = 255)
    private String password;

    @Column(nullable = false, name = "phone", unique = true, length = 15)
    private String phone;

    @Column(nullable = false, name = "email", unique = true, length = 255)
    private String email;

    @Column(nullable = false)
    private Role role;

    @Builder
    public Member(String nickname, String password, String phone, String email) {
        this.nickname = nickname;
        this.password = password;
        this.phone = phone;
        this.email = email;
        this.role = Role.ROLE_USER;
    }

}
