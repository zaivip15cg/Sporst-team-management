package com.example.sportsteammanagement.controller;

import com.example.sportsteammanagement.dto.APIResponse;
import com.example.sportsteammanagement.dto.response.PositionResponse;
import com.example.sportsteammanagement.service.PositionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PositionController {
    PositionService positionService;

    @GetMapping("/positions")
    public APIResponse<List<PositionResponse>> getAllPositions() {
        return APIResponse.<List<PositionResponse>>builder()
                .result(positionService.getAllPositions())
                .build();
    }
}

