package com.example.tyfserver.donation.repository;

import com.example.tyfserver.donation.domain.Donation;
import com.example.tyfserver.member.domain.Member;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DonationRepository extends JpaRepository<Donation, Long>, DonationQueryRepository {

    // todo OrderBy 개선필요
    //  workbench 에서 퍼포먼스db로 쿼리시 평균7초대 걸림 (오프셋없이). order by 지우면 0.007초대
    //  1. id로 정렬 -> 0.006초대
    //  2. created_at desc 인덱스 걸기

    // todo paging 개선필요 - limit offset 방식을 개선
    //  퍼포먼스 db 기준 offset 40,000부터 0.1sec, 280,000부터 1sec 넘어감.
    //  (정렬을 id로 걸었을 때 기준)

    //     select
    //        donation0_.id as id1_1_0_,
    //        member1_.id as id1_3_1_,
    //        donation0_.created_at as created_2_1_0_,
    //        donation0_.creator_id as creator_8_1_0_,
    //        donation0_.donator_id as donator_9_1_0_,
    //        donation0_.message as message3_1_0_,
    //        donation0_.name as name4_1_0_,
    //        donation0_.secret as secret5_1_0_,
    //        donation0_.point as point6_1_0_,
    //        donation0_.status as status7_1_0_,
    //        member1_.created_at as created_2_3_1_,
    //        member1_.account_id as account10_3_1_,
    //        member1_.bio as bio3_3_1_,
    //        member1_.email as email4_3_1_,
    //        member1_.nickname as nickname5_3_1_,
    //        member1_.oauth2type as oauth6_3_1_,
    //        member1_.page_name as page_nam7_3_1_,
    //        member1_.point as point8_3_1_,
    //        member1_.profile_image as profile_9_3_1_
    //    from
    //        donation donation0_
    //    left outer join
    //        member member1_
    //            on donation0_.creator_id=member1_.id
    //    where
    //        donation0_.creator_id=?
    //    order by
    //        donation0_.created_at desc limit ? offset ?
    @EntityGraph(attributePaths = "creator")
    List<Donation> findDonationByCreatorOrderByCreatedAtDesc(Member creator, Pageable pageable);
}
