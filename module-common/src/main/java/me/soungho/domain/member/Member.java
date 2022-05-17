package me.soungho.domain.member;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String name;

    @Column
    private String email;

    @Column
    private String nickName;

    @Column
    @Enumerated(EnumType.STRING)
    private MemberStatus status;

    @Column
    private int amountCharged;  // 청구된 금액

    @Column
    private int amountPaid; // 결재된 금액

    @Column
    private LocalDate dueDate;  // 결제 마감일

    @Column
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;

    public Member setStatusByUnPaid(){
        if(this.isUnpaid()){
            this.status = MemberStatus.INACTIVE;
        }
        return this;
    }

    public boolean isUnpaid(){
        return this.amountCharged <= this.amountPaid;
    }
}
