package org.example.bogo.domain.member.repository;

import org.example.bogo.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findEmailByMembername(String membername);
}