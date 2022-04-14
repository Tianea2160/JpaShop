package jpagroup.jpashop.domain;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @NotNull
    private String name;

    @Embedded
    private Address address;

    @OneToMany(mappedBy="member")
    private List<Order> orders = new ArrayList<>();

    @Builder
    public Member(String name, Address address) {
        if(name == null) throw new IllegalArgumentException("Member name error");

        this.name = name;
        this.address = address;
    }

    public Long updateName(String name) {
        this.name = name;
        return this.id;
    }
}
