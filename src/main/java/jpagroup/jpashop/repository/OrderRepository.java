package jpagroup.jpashop.repository;

import jpagroup.jpashop.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.swing.text.html.parser.Entity;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager em;

    public void save(Order order){
        em.persist(order);
    }

    public Order findOne(Long id){
        return em.find(Order.class, id);
    }

    //dynamic query
    public List<Order> findAllByCriteria(OrderSearch orderSearch){
        //query dsl을 사용하자
        return null;
    }
}
