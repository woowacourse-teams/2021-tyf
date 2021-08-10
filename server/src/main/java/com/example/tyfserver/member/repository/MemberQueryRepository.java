package com.example.tyfserver.member.repository;

import com.example.tyfserver.admin.dto.RequestingAccountResponse;
import com.example.tyfserver.member.dto.CurationsResponse;

import java.util.List;

public interface MemberQueryRepository {
    List<CurationsResponse> findCurations();
    List<RequestingAccountResponse> findRequestingAccounts();
}
