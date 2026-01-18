package org.example.datarize.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.datarize.common.error.ApiErrorCodes;
import org.example.datarize.common.error.ErrorCode;
import org.example.datarize.member.dto.MemberCreateRequest;
import org.example.datarize.member.dto.MemberCreateResponse;
import org.springframework.http.ResponseEntity;

@Tag(name = "Member", description = "회원 API")
public interface MemberApiDocs {

    @Operation(summary = "맴버 가입 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원 가입 성공")
    })
    @ApiErrorCodes(value = {
            ErrorCode.INVALID_NAME_LENGTH,
            ErrorCode.INVALID_BIRTH_NOT_FUTURE,
            ErrorCode.REQUIRED_BIRTH,
            ErrorCode.INVALID_BIRTH
    })
    ResponseEntity<MemberCreateResponse> join(final MemberCreateRequest request);
}
