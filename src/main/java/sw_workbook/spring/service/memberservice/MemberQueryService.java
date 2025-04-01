package sw_workbook.spring.service.memberservice;

import sw_workbook.spring.domain.Member;

import java.util.Optional;

public interface MemberQueryService {

    Optional<Member> findMember(Long id);

}