package com.example.sportsteammanagement.controller;


import com.example.sportsteammanagement.dto.APIResponse;
import com.example.sportsteammanagement.dto.request.MemberCreationRequest;
import com.example.sportsteammanagement.dto.request.MemberUpdateRequest;
import com.example.sportsteammanagement.dto.response.MemberResponse;
import com.example.sportsteammanagement.service.MemberService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MemberController {
    MemberService memberService;


    @PostMapping("/teams/{teamId}/members")
    @PreAuthorize("hasAnyRole('ADMIN','COACH')")

    public APIResponse<MemberResponse> addMember (@PathVariable Integer teamId,
                                                  @RequestBody MemberCreationRequest request) {
        return APIResponse.<MemberResponse>builder()
                .result(memberService.addMember(teamId, request)).build();
    }



    @GetMapping("/members")
    public APIResponse<List<MemberResponse>> getAllMembers (@RequestParam(required = false) Integer teamId) {
        return APIResponse.<List<MemberResponse>>builder()
                .result(memberService.getAllMembers(teamId )).build();
    }

    @PutMapping("/teams/{teamId}/members/{userId}")
    @PreAuthorize("hasAnyRole('ADMIN','COACH')")
    public APIResponse<MemberResponse> updateMember (@PathVariable Integer teamId, @PathVariable String userId, @RequestBody MemberUpdateRequest request) {
        return APIResponse.<MemberResponse>builder()
                .result(memberService.updateMember(teamId, userId, request)).build();

    }
    @DeleteMapping("/teams/{teamId}/members/{userId}")
    @PreAuthorize("hasAnyRole('ADMIN','COACH')")
    public  APIResponse<Void> deleteMember (@PathVariable Integer teamId, @PathVariable String userId) {
        memberService.deleteMember(teamId, userId);
        return APIResponse.<Void>builder().build();
    }
}

