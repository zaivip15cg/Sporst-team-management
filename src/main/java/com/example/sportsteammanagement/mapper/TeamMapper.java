package com.example.sportsteammanagement.mapper;

import com.example.sportsteammanagement.dto.request.TeamCreationRequest;
import com.example.sportsteammanagement.dto.request.TeamUpdateRequest;
import com.example.sportsteammanagement.dto.response.TeamResponse;
import com.example.sportsteammanagement.entity.Team;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TeamMapper {
    Team toTeam(TeamCreationRequest request);
    TeamResponse toTeamResponse(Team team);
    void updateTeam(@MappingTarget Team team, TeamUpdateRequest request);
}
