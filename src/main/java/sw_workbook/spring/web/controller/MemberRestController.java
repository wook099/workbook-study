package sw_workbook.spring.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import sw_workbook.spring.apiPayload.ApiResponse;
import sw_workbook.spring.converter.MemberConverter;
import sw_workbook.spring.domain.Member;
import sw_workbook.spring.service.memberservice.MemberCommandService;
import sw_workbook.spring.web.dto.MemberRequestDTO;
import sw_workbook.spring.web.dto.MemberResponseDTO;

@RestController
@RequiredArgsConstructor
public class MemberRestController {

    private final MemberCommandService memberCommandService;

//    @PostMapping("/")
//    public ApiResponse<MemberResponseDTO.JoinResultDTO> join(@RequestBody @Valid MemberRequestDTO.JoinDto request){
//        Member member = memberCommandService.joinMember(request);
//        return ApiResponse.onSuccess(MemberConverter.toJoinResultDTO(member));
//    }
    @GetMapping("api/hello")
    public String index(){
        return "HEllo from SPring BOOT";
    }


}
