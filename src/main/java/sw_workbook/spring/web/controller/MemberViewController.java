package sw_workbook.spring.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import sw_workbook.spring.apiPayload.ApiResponse;
import sw_workbook.spring.service.memberservice.MemberCommandService;
import sw_workbook.spring.web.dto.MemberRequestDTO;

@Controller
@RequiredArgsConstructor
public class MemberViewController {

    private final MemberCommandService memberCommandService;



    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
    @GetMapping("/signup")
    public String signupPage(Model model) {
        model.addAttribute("memberJoinDto", new MemberRequestDTO.JoinDto());  // 모델에 DTO 객체 추가
        return "signup";
    }
    @PostMapping("members/signup")
    public String joinMember(@ModelAttribute("memberJoinDto") MemberRequestDTO.JoinDto request,
                             BindingResult bindingResult,
                             Model model) {

        if (bindingResult.hasErrors()) {
            return "signup";
        }
        try {
            memberCommandService.joinMember(request);
            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "signup";
        }
    }

//    @PostMapping("/{storeid}/review")
//    public ApiResponse<StoreReponseDTO.CreateReviewResultDTO> createReview(@RequestBody @Valid storeRequestDTO.ReviewDTO request,
//                                                                           @Exist)


}
