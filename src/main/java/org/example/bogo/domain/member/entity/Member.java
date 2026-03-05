package org.example.bogo.domain.member.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "member")
@Getter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "nickname", length = 50)
    private String nickname;

    @Column(nullable = false, name = "password", length = 255)
    private String password;

    @Column(nullable = false, name = "email", unique = true, length = 255)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(name = "email_verifyed")
    private boolean emailVerified = false;

    @Builder
    public Member(String nickname, String password, String email) {
        this.nickname = nickname;
        this.password = password;
        this.email = email;
        this.role = Role.ROLE_USER;
        this.emailVerified = false;
    }

    public void makeEmailVerified() {
        this.emailVerified = true;
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }

}
