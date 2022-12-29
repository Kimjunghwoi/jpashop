package jpabook.jpashop.api;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.Orders;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import jpabook.jpashop.repository.OrderSimpleQueryDto;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Order
 * Order -> Member
 * Order -> Delivery
 */
@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderRepository orderRepository;


    /**
     * 엔티티 직접 노출
     */
    @GetMapping("/api/v1/simple-orders")
    public List<Orders> ordersV1() {
        List<Orders> all = orderRepository.findAllByString(new OrderSearch());

        //Lazy loading 에 의해 나오지않는 정보 출력방법.
        for (Orders orders : all) {
            orders.getMember().getName();//Lazy 강제 초기화
            orders.getDelivery().getAddress();//Lazy 강제 초기화
        }

        return all;
    }

    @GetMapping("/api/v2/simple-orders")
    public List<SimpleOrdersDTO> ordersV2() {

        //N + 1 문제 1 + 회원 N + 배송 N
        List<Orders> orders = orderRepository.findAllByString(new OrderSearch());

        return orders.stream()
                .map(SimpleOrdersDTO::new)
                .collect(toList());
    }



    @GetMapping("/api/v3/simple-orders")
    public List<SimpleOrdersDTO> ordersV3() {

        List<Orders> orders = orderRepository.findAllWithMemberDelivery();
        List<SimpleOrdersDTO> collect = orders.stream()
                .map(SimpleOrdersDTO::new)
                .collect(toList());
        return collect;

    }

    @GetMapping("/api/v4/simple-orders")
    public List<OrderSimpleQueryDto> ordersV4() {


        return orderRepository.findOrderDtos();

    }


    @Data
    static class SimpleOrdersDTO {
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;

        public SimpleOrdersDTO(Orders orders) {
            orderId = orders.getId();
            name = orders.getMember().getName();
            orderDate = orders.getOrderDate();
            orderStatus = orders.getStatus();
            address = orders.getDelivery().getAddress();
        }
    }


}
