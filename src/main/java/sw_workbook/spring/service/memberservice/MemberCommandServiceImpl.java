package sw_workbook.spring.service.memberservice;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sw_workbook.spring.Repository.FoodCategoryRepository;
import sw_workbook.spring.Repository.MemberRepository;
import sw_workbook.spring.apiPayload.code.exception.handler.FoodCategoryHandler;
import sw_workbook.spring.apiPayload.code.status.ErrorStatus;
import sw_workbook.spring.converter.MemberConverter;
import sw_workbook.spring.converter.MemberPreferConverter;
import sw_workbook.spring.domain.FoodCategory;
import sw_workbook.spring.domain.Member;
import sw_workbook.spring.domain.mapping.MemberPrefer;
import sw_workbook.spring.web.dto.MemberRequestDTO;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberCommandServiceImpl implements MemberCommandService{

    private final MemberRepository memberRepository;
    private final FoodCategoryRepository foodCategoryRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    @Transactional
    public Member joinMember(MemberRequestDTO.JoinDto request) {

        Member newMember = MemberConverter.toMember(request);

        newMember.encodePassword(passwordEncoder.encode(request.getPassword()));

        List<FoodCategory> foodCategoryList = request.getPreferCategory().stream()
                .map(category -> {
                    return foodCategoryRepository.findById(category).orElseThrow(() -> new FoodCategoryHandler(ErrorStatus.FOOD_CATEGORY_NOT_FOUND));
                }).collect(Collectors.toList());
        //List<FoodCategory> foodCategoryList = [FoodCategory(1), FoodCategory(3), FoodCategory(5)]; 들어감

        List<MemberPrefer> memberPreferList = MemberPreferConverter.toMemberPreferList(foodCategoryList);
        //리스트형으로 변환

        memberPreferList.forEach(memberPrefer -> {memberPrefer.setMember(newMember);});

        return memberRepository.save(newMember);

    }
}