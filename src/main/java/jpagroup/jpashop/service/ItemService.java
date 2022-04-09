package jpagroup.jpashop.service;

import jpagroup.jpashop.domain.item.Item;
import jpagroup.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item){
        itemRepository.save(item);
    }

    public List<Item> findItems(){
        return itemRepository.findAll();
    }

    public Item findOne(Long id){
        return itemRepository.findOne(id);
    }

    public Long updateItem(Long itemId, String name, int price, int stockQuantity) {
        //변경 감지 사용법
        Item item = itemRepository.findOne(itemId);

        item.setName(name);
        item.setPrice(price);
        item.setStockQuantity(stockQuantity);

        return itemId;
    }
}
