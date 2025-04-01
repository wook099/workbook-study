package sw_workbook.spring.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sw_workbook.spring.domain.Member;
import sw_workbook.spring.domain.enums.MemberStatus;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query("SELECT m FROM Member m WHERE m.name = :name AND m.status = :status")
    List<Member> findByNameAndStatus(@Param("name") String name, @Param("status") MemberStatus status);

    Optional<Member> findByEmail(String email);
}