package sw_workbook.spring.domain.mapping;

import jakarta.persistence.*;
import lombok.*;
import sw_workbook.spring.domain.Member;
import sw_workbook.spring.domain.Mission;
import sw_workbook.spring.domain.common.BaseEntity;
import sw_workbook.spring.domain.enums.MissionStatus;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MemberMission extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private MissionStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mission_id")
    private Mission mission;
}
