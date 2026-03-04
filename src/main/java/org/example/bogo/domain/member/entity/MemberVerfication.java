package org.example.bogo.domain.member.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "MEMBER_VERIFICATIONS")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class MemberVerfication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
