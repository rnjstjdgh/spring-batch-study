package me.soungho.domain.member;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member> findAll();
    List<Member> findByStatusEquals(MemberStatus memberStatus);
}
