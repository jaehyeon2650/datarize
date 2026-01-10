package org.example.datarize.member.service;

import lombok.RequiredArgsConstructor;
import org.example.datarize.member.domain.Member;
import org.example.datarize.member.dto.MemberCreateRequest;
import org.example.datarize.member.dto.MemberCreateResponse;
import org.example.datarize.member.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public MemberCreateResponse createMember(final MemberCreateRequest request) {
        final Member member = Member.of(request.name(), request.getBirth());
        final Member saveMember = memberRepository.save(member);
        return MemberCreateResponse.of(saveMember);
    }
}
