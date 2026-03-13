package org.example.bogo.domain.member.repository;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.example.bogo.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByNickname(String nickname);

    Optional<Member> findById(Long id);

    boolean existsByEmail(String email);

    Optional<Member> findByEmail(@NotBlank @Size(max = 50) String email);

    Optional<Member> findMemberByEmail(@NotBlank @Size(max = 50) String nickname);

}