package org.example.datarize.member.service;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import org.example.datarize.member.domain.Member;
import org.example.datarize.member.dto.MemberCreateRequest;
import org.example.datarize.member.dto.MemberCreateResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import(MemberService.class)
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private EntityManager em;

    @Test
    @DisplayName("회원 가입 시 새로운 아이디를 부여한다.")
    void createMember() {
        // given
        final String name = "a";
        final String birth = "2000-11-02";
        final MemberCreateRequest request = new MemberCreateRequest(name, birth);
        // when
        final MemberCreateResponse response = memberService.createMember(request);
        // then
        assertThat(response.id()).isNotNull();
        final Member findMember = em.find(Member.class, response.id());
        assertThat(findMember).isNotNull();
        assertThat(findMember.getName()).isEqualTo(name);
        assertThat(findMember.getBirth()).isEqualTo(LocalDate.parse(birth));
    }

    @Test
    @DisplayName("동명이인도 회원 가입이 가능하다.")
    void createSameMember() {
        // given
        final String duplicateName = "a";
        final String duplicateDate = "2000-11-02";
        final Member savedMember = Member.of(duplicateName, LocalDate.parse(duplicateDate));
        em.persist(savedMember);
        em.flush();
        em.clear();
        final MemberCreateRequest request = new MemberCreateRequest(duplicateName, duplicateDate);
        // when
        final MemberCreateResponse response = memberService.createMember(request);
        // then
        assertThat(response.id()).isNotNull();
        final Member findMember = em.find(Member.class, response.id());
        assertThat(findMember).isNotNull();
        assertThat(findMember.getName()).isEqualTo(duplicateName);
        assertThat(findMember.getBirth()).isEqualTo(LocalDate.parse(duplicateDate));
    }
}
