package com.example.sportsteammanagement.service;

import com.example.sportsteammanagement.Exception.AppException;
import com.example.sportsteammanagement.Exception.ErrorCode;
import com.example.sportsteammanagement.dto.request.MemberCreationRequest;
import com.example.sportsteammanagement.dto.request.MemberUpdateRequest;
import com.example.sportsteammanagement.dto.response.MemberResponse;
import com.example.sportsteammanagement.entity.*;
import com.example.sportsteammanagement.repository.MemberRepository;
import com.example.sportsteammanagement.repository.PositionRepository;
import com.example.sportsteammanagement.repository.TeamRepository;
import com.example.sportsteammanagement.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class MemberService {
    MemberRepository memberRepository;
    TeamRepository teamRepository;
    UserRepository userRepository;
    PositionRepository positionRepository;

    public MemberResponse addMember(Integer teamId, MemberCreationRequest request) {
        MemberId memberId = new MemberId(teamId, request.getUserId());
        if (memberRepository.existsById(memberId))
            throw new AppException(ErrorCode.MEMBER_EXISTED);

        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new AppException(ErrorCode.TEAM_NOT_FOUND));
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new AppException(ErrorCode.User_NOT_Exist));
        Position position = positionRepository.findById(request.getPositionId())
                .orElseThrow(() -> new AppException(ErrorCode.POSITION_NOT_FOUND));

        Member member = Member.builder()
                .id(memberId)
                .team(team)
                .user(user)
                .jerseyNumber(request.getJerseyNumber())
                .position(position)
                .build();

        return toResponse(memberRepository.save(member));
    }

    public List<MemberResponse> getAllMembers(Integer teamId) {
        List<Member> members = (teamId != null)
                ? memberRepository.findById_TeamId(teamId)
                : memberRepository.findAll();
        return members.stream().map(this::toResponse).toList();
    }

    public MemberResponse updateMember(Integer teamId, String userId, MemberUpdateRequest request) {
        MemberId memberId = new MemberId(teamId, userId);
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new AppException(ErrorCode.MEMBER_NOT_FOUND));

        if (request.getJerseyNumber() != null)
            member.setJerseyNumber(request.getJerseyNumber());

        if (request.getPositionId() != null) {
            Position position = positionRepository.findById(request.getPositionId())
                    .orElseThrow(() -> new AppException(ErrorCode.POSITION_NOT_FOUND));
            member.setPosition(position);
        }

        return toResponse(memberRepository.save(member));
    }

    public void deleteMember(Integer teamId, String userId) {
        MemberId memberId = new MemberId(teamId, userId);
        if (!memberRepository.existsById(memberId))
            throw new AppException(ErrorCode.MEMBER_NOT_FOUND);
        memberRepository.deleteById(memberId);
    }

    private MemberResponse toResponse(Member member) {
        return MemberResponse.builder()
                .userId(member.getId().getUserId())
                .teamId(member.getId().getTeamId())
                .jerseyNumber(member.getJerseyNumber())
                .positionName(member.getPosition() != null ? member.getPosition().getName() : null)
                .build();
    }
}
