package jpabook.jpashop.domain;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Orders;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.criterion.Order;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member")
    private List<Orders> orders = new ArrayList<>();
    //하이버네이트가 영속화할때 위 방식으로 해야 나중에 set..어쩌구..뭐 문제가있다고함 관리측면에서
//    public Member() {
//        orders = new ArrayList<>();
//    }
}
