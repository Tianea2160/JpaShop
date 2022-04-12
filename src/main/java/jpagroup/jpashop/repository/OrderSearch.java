package jpagroup.jpashop.repository;


import jpagroup.jpashop.domain.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderSearch {
    private String memberName; //회원의 이름
    private OrderStatus orderStatus; // ORDER, CANCEL
}
