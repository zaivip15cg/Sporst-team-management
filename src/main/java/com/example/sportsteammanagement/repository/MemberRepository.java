package com.example.sportsteammanagement.repository;

import com.example.sportsteammanagement.entity.Member;
import com.example.sportsteammanagement.entity.MemberId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, MemberId> {
    List<Member> findById_TeamId(Integer teamId);
    long countById_TeamId(Integer teamId);

}
