package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    /*
    // 변경 감지에 의해서 데이터를 변경하는 방법
    @Transactional
    public void updateItem(Long itemId, Book param) {
        Item findItem = itemRepository.findOne(itemId);
        findItem.setPrice(param.getPrice());
        findItem.setName(param.getName());
        findItem.setStockQuantity(param.getStockQuantity());

        // itemRepository.save(findItem), merge, .. 아무것도 호출할 필요가 없음.

        //spring의 transactional에 의해서 transaction 커밋 -> jpa flush를 날려서 영속성 컨텍트스에 있는 엔티티 중에 변경된 애가 뭔지 전부 검색

    }

     */

    @Transactional
    public void updateItem(Long itemId, String name, int price, int stockQuantity) {
        Item findItem = itemRepository.findOne(itemId);
        // setter 없이 entity안에서 바로 추적할 수 있는 메서드를 만드는게 제일 베스트
        // findItem.change(name, price, stockQuantity)
        findItem.setName(name);
        findItem.setPrice(price);
        findItem.setStockQuantity(stockQuantity);
    } // 파라미터에 데이터를 Dto를 생성해서 활용 가능

    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }
}
