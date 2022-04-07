package jpagroup.jpashop.domain.item;

import jpagroup.jpashop.exception.NotEnoughStockException;
import jpagroup.jpashop.domain.Category;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
public abstract class Item {
    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    // 비지니스 로지기
    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    public void removeStock(int quantity) {
        int remain = this.stockQuantity - quantity;
        //음수에 대한 검사
        if(remain < 0){
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = remain;
    }
}
