package jpagroup.jpashop.service;

import jpagroup.jpashop.domain.Address;
import jpagroup.jpashop.domain.Member;
import jpagroup.jpashop.domain.Order;
import jpagroup.jpashop.domain.OrderStatus;
import jpagroup.jpashop.domain.item.Book;
import jpagroup.jpashop.domain.item.Item;
import jpagroup.jpashop.exception.NotEnoughStockException;
import jpagroup.jpashop.repository.ItemRepository;
import jpagroup.jpashop.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    private EntityManager em;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ItemRepository itemRepository;

    @Test
    void 상품주문() throws Exception {
        //given
        Member member = createMember("member1");
        em.persist(member);

        Book book = createBook("시골 JPA", 10000, 10);
        em.persist(book);
        int orderCount = 2;

        //when
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        //then
        Order order = orderRepository.findOne(orderId);
        assertEquals(order.getStatus(), OrderStatus.ORDER);
        assertThat(order.getId()).isGreaterThanOrEqualTo(0);
        assertEquals(1, order.getOrderItems().size());
        assertEquals(10000 * orderCount, order.getTotalPrice());
        assertEquals(8, book.getStockQuantity());
    }


    @Test
    void 상품주문_재고수량_초과() throws Exception {
        //given
        Member member = createMember("회원1");
        em.persist(member);

        Book book = createBook("hello world", 10000, 10);
        em.persist(book);
        int orderCount = 11;

        //when
        //then
        assertThrows(NotEnoughStockException.class, () -> {
            orderService.order(member.getId(), book.getId(), orderCount);
        });
    }

    @Test
    void 주문취소() throws Exception {
        //given
        Member member = createMember("회원1");
        Book book = createBook("시골 JPA", 10000, 10);
        int orderCount = 2;

        em.persist(member);
        em.persist(book);

        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);
        //when
        orderService.cancelOrder(orderId);

        //then
        Order canceledOrder = orderRepository.findOne(orderId);
        Item item = itemRepository.findOne(book.getId());

        assertEquals(canceledOrder.getStatus(), OrderStatus.CANCEL);
        assertEquals(item.getStockQuantity(), 10);
    }

    private Member createMember(String name) {
        Address address = new Address("서울", "강가", "123-123");
        Member member = Member.builder()
                .name("kim")
                .address(address)
                .build();
        return member;
    }

    private Book createBook(String name, int price, int stockQuantity) {
        Book book = Book.builder()
                .name(name)
                .price(price)
                .stockQuantity(stockQuantity)
                .build();
        return book;
    }
}