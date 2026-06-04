package com.example.sportsteammanagement.service;

import com.example.sportsteammanagement.Exception.AppException;
import com.example.sportsteammanagement.Exception.ErrorCode;
import com.example.sportsteammanagement.dto.request.TeamCreationRequest;
import com.example.sportsteammanagement.dto.request.TeamUpdateRequest;
import com.example.sportsteammanagement.dto.response.TeamResponse;
import com.example.sportsteammanagement.entity.Team;
import com.example.sportsteammanagement.entity.User;
import com.example.sportsteammanagement.mapper.TeamMapper;
import com.example.sportsteammanagement.repository.MemberRepository;
import com.example.sportsteammanagement.repository.TeamRepository;
import com.example.sportsteammanagement.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TeamService {
    TeamRepository teamRepository;
    MemberRepository memberRepository;
    UserRepository userRepository;
    TeamMapper teamMapper;

    public TeamResponse createTeam(TeamCreationRequest request) {
        if (teamRepository.existsByTeamName(request.getTeamName()))
            throw new AppException(ErrorCode.TEAM_EXISTED);

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User manager = userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.User_NOT_Exist));

        Team team = teamMapper.toTeam(request);
        team.setManager(manager);
        return toResponse(teamRepository.save(team));
    }

    public List<TeamResponse> getAllTeams() {
        return teamRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public TeamResponse getTeamById(Integer id) {
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.TEAM_NOT_FOUND));
        return toResponse(team);
    }

    public TeamResponse updateTeam(Integer id, TeamUpdateRequest request) {
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.TEAM_NOT_FOUND));

        if (request.getTeamName() != null
                && !team.getTeamName().equals(request.getTeamName())
                && teamRepository.existsByTeamName(request.getTeamName()))
            throw new AppException(ErrorCode.TEAM_EXISTED);

        teamMapper.updateTeam(team, request);

        if (request.getManagerId() != null) {
            User manager = userRepository.findById(request.getManagerId())
                    .orElseThrow(() -> new AppException(ErrorCode.User_NOT_Exist));
            team.setManager(manager);
        }

        return toResponse(teamRepository.save(team));
    }

    public void deleteTeam(Integer id) {
        if (!teamRepository.existsById(id))
            throw new AppException(ErrorCode.TEAM_NOT_FOUND);
        teamRepository.deleteById(id);
    }

    private TeamResponse toResponse(Team team) {
        TeamResponse response = teamMapper.toTeamResponse(team);
        response.setMemberCount((int) memberRepository.countById_TeamId(team.getId()));
        response.setManagerName(team.getManager() != null ? team.getManager().getName() : null);
        return response;
    }
}
