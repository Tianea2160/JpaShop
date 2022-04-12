package jpagroup.jpashop.domain.item;

import lombok.*;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import static org.junit.jupiter.api.Assertions.*;

@Entity
@Getter
@DiscriminatorValue("B")
public class Book extends Item{
    private String author;
    private String isbn;

    protected Book(){
        super();
    }

    @Builder
    protected Book(String name, Integer price, Integer stockQuantity, String author, String isbn){
        super(name, price, stockQuantity);
        //name, price, stockQuantity 는 필수
        if(name == null) //  이름은 필수
            throw new IllegalArgumentException("book name error");

        if(price == null || price < 0)// 가격 필수 및 음수 불가
            throw new IllegalArgumentException("book price error");

        if(stockQuantity == null || stockQuantity < 0)// 재고 필수 및 음수 불가
            throw new IllegalArgumentException("book stockQuantity error");

        this.author = author;
        this.isbn = isbn;
    }
}
