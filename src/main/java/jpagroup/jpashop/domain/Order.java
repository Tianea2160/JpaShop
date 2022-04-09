package jpagroup.jpashop.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {
    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    // 연관관계 편의 메소드 들
    public void setMember(Member member){
        this.member = member;
        member.getOrders().add(this);
    }

    public void setOrderItem(OrderItem orderItem){
        this.orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery){
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    //생성 메서드
    @Builder
    public Order(Member member, Delivery delivery,List<OrderItem> orderItems){
        this.setMember(member); // 비지니스 로직
        this.delivery = delivery;

        for(OrderItem orderItem : orderItems){
            this.setOrderItem(orderItem);
        }
        this.status = OrderStatus.ORDER;
        this.orderDate = LocalDateTime.now();
    }

    // 비지니스 로직
    public void cancel(){
        if(delivery.getStatus() == DeliveryStatus.CAMP){
            throw new IllegalStateException("이미 배송된 상품은 주문 취소가 불가능합니다.");
        }

        this.status = OrderStatus.CANCEL;
        orderItems.forEach((OrderItem::cancel));
    }
    //조회 로직
    public int getTotalPrice(){
        return orderItems.stream()
                .mapToInt(OrderItem::getTotalPrice)
                .sum();
    }
}