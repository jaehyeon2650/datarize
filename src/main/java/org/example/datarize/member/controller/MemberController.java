package org.example.datarize.member.controller;

import lombok.RequiredArgsConstructor;
import org.example.datarize.member.dto.MemberCreateRequest;
import org.example.datarize.member.dto.MemberCreateResponse;
import org.example.datarize.member.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController implements MemberApiDocs {

    private final MemberService memberService;

    @PostMapping("/api/users")
    public ResponseEntity<MemberCreateResponse> join(@RequestBody final MemberCreateRequest request) {
        final MemberCreateResponse response = memberService.createMember(request);
        return ResponseEntity.ok(response);
    }
}
