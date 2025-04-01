package sw_workbook.spring.service.memberservice;

import org.springframework.security.crypto.password.PasswordEncoder;
import sw_workbook.spring.domain.Member;
import sw_workbook.spring.web.dto.MemberRequestDTO;

public interface MemberCommandService {

    Member joinMember(MemberRequestDTO.JoinDto request);

}