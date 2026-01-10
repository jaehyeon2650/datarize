package org.example.datarize.member.dto;

import java.time.LocalDate;
import org.example.datarize.member.domain.Member;

public record MemberCreateResponse(
        Long id,
        String name,
        LocalDate birth
) {
    public static MemberCreateResponse of(final Member member) {
        return new MemberCreateResponse(member.getId(), member.getName(), member.getBirth());
    }
}
