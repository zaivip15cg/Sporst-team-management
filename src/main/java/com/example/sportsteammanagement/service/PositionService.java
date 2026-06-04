package com.example.sportsteammanagement.service;

import com.example.sportsteammanagement.dto.APIResponse;
import com.example.sportsteammanagement.dto.response.PositionResponse;
import com.example.sportsteammanagement.entity.Position;
import com.example.sportsteammanagement.repository.PositionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)

public class PositionService {
    PositionRepository positionRepository;

    public List<PositionResponse> getAllPositions() {
        return positionRepository.findAll()
                .stream()
                .map(position -> PositionResponse.builder()
                        .id(position.getId())
                        .name(position.getName())
                        .build())

        .toList();


        }
    }


