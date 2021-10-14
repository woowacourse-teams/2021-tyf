package com.example.tyfserver.common.repository;

import com.example.tyfserver.member.domain.Account;
import com.example.tyfserver.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class DummyDataRepository {

    private final JdbcTemplate jdbcTemplate;

    @Value("${batch_size}")
    private int batchSize;

    public void saveAllMember(List<Member> members, int startIdx) {
        int batchCount = 0;
        List<Member> subItems = new ArrayList<>();
        for (int i = 0; i < members.size(); i++) {
            subItems.add(members.get(i));
            if ((i + 1) % batchSize == 0) {
                batchCount = batchInsertMember(batchSize, batchCount, subItems, startIdx);
            }
        }
        if (!subItems.isEmpty()) {
            batchCount = batchInsertMember(batchSize, batchCount, subItems, startIdx);
        }
        System.out.println("batchCount: " + batchCount);
    }

    public void saveAllAccount(List<Account> accounts) {
        int batchCount = 0;
        List<Account> subItems = new ArrayList<>();
        for (int i = 0; i < accounts.size(); i++) {
            subItems.add(accounts.get(i));
            if ((i + 1) % batchSize == 0) {
                batchCount = batchInsertAccount(batchSize, batchCount, subItems);
            }
        }
        if (!subItems.isEmpty()) {
            batchCount = batchInsertAccount(batchSize, batchCount, subItems);
        }
        System.out.println("batchCount: " + batchCount);
    }


    private int batchInsertAccount(int batchSize, int batchCount, List<Account> accounts) {
        jdbcTemplate.batchUpdate("INSERT INTO account (created_at, account_holder, account_number, bank, bankbook_url, status) VALUES (?,?,?,?,?,?)",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setString(1, LocalDateTime.now().toString());
                        ps.setString(2, accounts.get(i).getAccountHolder());
                        ps.setString(3, accounts.get(i).getAccountNumber());
                        ps.setString(4, accounts.get(i).getBank());
                        ps.setString(5, accounts.get(i).getBankbookUrl());
                        ps.setString(6, accounts.get(i).getStatus().name());
                    }

                    @Override
                    public int getBatchSize() {
                        return accounts.size();
                    }
                });
        accounts.clear();
        batchCount++;
        return batchCount;
    }

    private int batchInsertMember(int batchSize, int batchCount, List<Member> members, int startIdx) {
        jdbcTemplate.batchUpdate("INSERT INTO member (created_at, bio, email, nickname, oauth2type, page_name, point, profile_image, account_id) VALUES (?,?,?,?,?,?,?,?,?)",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setString(1, LocalDateTime.now().toString());
                        ps.setString(2, members.get(i).getBio());
                        ps.setString(3, members.get(i).getEmail());
                        ps.setString(4, members.get(i).getNickname());
                        ps.setString(5, members.get(i).getOauth2Type().name());
                        ps.setString(6, members.get(i).getPageName());
                        ps.setLong(7, members.get(i).getPoint());
                        ps.setString(8, members.get(i).getProfileImage());
                        ps.setLong(9, startIdx + i);
                    }

                    @Override
                    public int getBatchSize() {
                        return members.size();
                    }
                });
        members.clear();
        batchCount++;
        return batchCount;
    }

}
