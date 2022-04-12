package jpagroup.jpashop.service;

import jpagroup.jpashop.domain.Delivery;
import jpagroup.jpashop.domain.Member;
import jpagroup.jpashop.domain.Order;
import jpagroup.jpashop.domain.OrderItem;
import jpagroup.jpashop.domain.item.Item;
import jpagroup.jpashop.repository.ItemRepository;
import jpagroup.jpashop.repository.MemberRepository;
import jpagroup.jpashop.repository.OrderRepository;
import jpagroup.jpashop.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;

    //주문
    @Transactional
    public Long order(Long memberId, Long itemId, int count){
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        Delivery delivery = new Delivery();

        delivery.setAddress(member.getAddress());

        OrderItem orderItem = OrderItem.creatOrderItem(item, item.getPrice(), count);

        Order order = Order.builder()
                .member(member)
                .orderItems(Collections.singletonList(orderItem))
                .build();

        orderRepository.save(order);
        return order.getId();
    }

    //취소
    @Transactional
    public void cancelOrder(Long orderId){
        Order order = orderRepository.findOne(orderId);
        order.cancel();
    }

    public List<Order> findOrders(OrderSearch orderSearch){
        return orderRepository.findAllByString(orderSearch);
    }
}
