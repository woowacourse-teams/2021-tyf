package com.thankyou_for.member.repository;

import com.thankyou_for.member.domain.Member;
import com.thankyou_for.member.dto.CurationsResponse;

import java.util.List;

public interface MemberQueryRepository {

    List<CurationsResponse> findCurations();

    List<Member> findRequestingAccounts();
}
