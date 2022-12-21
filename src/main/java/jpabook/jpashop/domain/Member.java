package jpabook.jpashop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore // 양방향인 경우 걸어줘야 한다. API 호출 시 무한으로 계속 바인딩하기때문.
    @OneToMany(mappedBy = "member")
    private List<Orders> orders = new ArrayList<>();
    //하이버네이트가 영속화할때 위 방식으로 해야 나중에 set..어쩌구..뭐 문제가있다고함 관리측면에서
//    public Member() {
//        orders = new ArrayList<>();
//    }


    /**
     * 프록시
     * 하이버네이트에서 작업
     *
     * em.find() vs em.getReference()
     * find : 데이터베이스를 통해서 실제 엔티티 객체 조회
     * Reference 데이터베이스 조회를 미루는 가짜(프록시) 엔티티 객체 조회
     *  셀렉트 쿼리없이 객체를 담고 받은 클래스 객체에서 해결하지 못하는 경우 select 쿼리 발생.
     *
     * 프록시 특징
     *     1. 실제 클래스를 상속 받아 만들어져 있어 실제 클래스와 프록시 객체는 겉모양이같다
     *     2. 사용하는 입장에서는 진짜 객체인지 프록시 객체인지 구분하지 않고 사용하면 됨 (이론상 .. 진짜 객체를 상속 받기 때문에 )
     *     3. 프록시 객체는 실제 객체의 참조(target)를 보관
     *  a. 프록시 객체는 처음 사용할 때 한 번만 초기화
     *  b. 프록시 객체를 초기화 할 때, 프록시 객체가 실제 엔티티로 바뀌는건 아니다!!!, 초기화되면 프록시 객체를 통해서 실제 엔티티에 접근 가능.
     *  c. 프록시 객체는 원본 엔티티를 상속받음, 따라서 타입 체크시 주의해야함 ( == 비교 실패, instance of 사용 Type에 대한 비교는 instance of 를 쓰자 )
     *  d. 영속성 컨텍스트에 찾는 엔티티가 이미 있으면 em.getReference()를 호출해도 실제 엔티티를 반환
     *    ==  프록시를 먼저 조회하고 이후에 find 한 경우 find 해도 프록시를 조회함.
     *  e. 영속성 컨텍스트의 도움을 받을 수 없는 준영속 상태일 때, 프록시를 초기화 문제 발생 ( 하이버네이트는 LazeInitailizationException 예외 터트림 )
     *    -- em.clear, detach ..등을 만난 상태에서 초기화요청... ( no Session 등의 에러 )
     * 프록시 확인
     *     1. 프록시 인스턴스의 초기화 여부 확인 (PersistenceUnitUtil.isLoaded(Object entity)
     *     2. 프록시 클래스 확인 방법 (entity.getClass().getName() 출력(javasist.... or HibernateProxy...)
     *     3. 프록시 강제 초기화 (org.hibernate.Hibernate.initialize(entity);
     *     참고 . JPA는 강제초기화가 없음.. 위에 내용은 하이버네이트가 제공해줌.
     *
     *
     *
     */
//
}
