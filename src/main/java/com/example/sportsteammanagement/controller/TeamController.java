package com.example.sportsteammanagement.controller;

import com.example.sportsteammanagement.dto.APIResponse;
import com.example.sportsteammanagement.dto.request.TeamCreationRequest;
import com.example.sportsteammanagement.dto.request.TeamUpdateRequest;
import com.example.sportsteammanagement.dto.response.TeamResponse;
import com.example.sportsteammanagement.service.TeamService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teams")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TeamController {

    TeamService teamService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public APIResponse<TeamResponse> createTeam(@RequestBody TeamCreationRequest request) {
        return APIResponse.<TeamResponse>builder()
                .result(teamService.createTeam(request))
                .build();
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'COACH')")
    public APIResponse<List<TeamResponse>> getAllTeams() {
        return APIResponse.<List<TeamResponse>>builder()
                .result(teamService.getAllTeams())
                .build();
    }

    @GetMapping("/{teamId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'COACH')")
    public APIResponse<TeamResponse> getTeamById(@PathVariable Integer teamId) {
        return APIResponse.<TeamResponse>builder()
                .result(teamService.getTeamById(teamId))
                .build();
    }

    @PutMapping("/{teamId}")
    @PreAuthorize("hasRole('ADMIN')")
    public APIResponse<TeamResponse> updateTeam(@PathVariable Integer teamId,
                                                @RequestBody TeamUpdateRequest request) {
        return APIResponse.<TeamResponse>builder()
                .result(teamService.updateTeam(teamId, request))
                .build();
    }

    @DeleteMapping("/{teamId}")
    @PreAuthorize("hasRole('ADMIN')")
    public APIResponse<Void> deleteTeam(@PathVariable Integer teamId) {
        teamService.deleteTeam(teamId);
        return APIResponse.<Void>builder().build();
    }
}
