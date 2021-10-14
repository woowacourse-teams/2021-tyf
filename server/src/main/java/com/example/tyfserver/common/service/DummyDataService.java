package com.example.tyfserver.common.service;

import com.example.tyfserver.auth.domain.Oauth2Type;
import com.example.tyfserver.common.repository.DummyDataRepository;
import com.example.tyfserver.member.domain.Account;
import com.example.tyfserver.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class DummyDataService {

    private final DummyDataRepository batchDataRepository;

    @Value("${batch_size}")
    private int batchSize;

    public void putMemberDummyData() {
        List<Member> members = new ArrayList<>();
        List<Account> accounts = new ArrayList<>();
        int idx = 1;
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < batchSize; j++) {
                System.out.println("account number :" + idx);
                accounts.add(new Account("test", String.valueOf(idx), "bank", null));
                members.add(new Member("e" + idx + "@test.com", "n" + idx,  "p" + idx, Oauth2Type.KAKAO));
                idx++;
            }

            batchDataRepository.saveAllAccount(accounts);
            batchDataRepository.saveAllMember(members, idx - batchSize);
            members.clear();
        }
    }

}
