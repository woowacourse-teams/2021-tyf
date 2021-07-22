package com.example.tyfserver.member.repository;

import com.example.tyfserver.member.dto.CurationsResponse;

import java.util.List;
import java.util.Optional;

public interface MemberQueryRepository {
    List<CurationsResponse> findCurations();
    Optional<String> findProfileImageById(Long id);
}
