package jpabook.jpashop.domain.item;

import jpabook.jpashop.domain.Category;
import jpabook.jpashop.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


//상속 관계 매핑 , 싱글 테이블 전략
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "dtype")
@Getter @Setter
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
    
    //==비즈니스 로직==//

    /**
     * stock 증가
     */
    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    /**
     * stock 감소
     */
    public void removeStock(int quantity) {
        int restStock = this.stockQuantity - quantity;
        if (restStock < 0) {
            throw new NotEnoughStockException("need more stock");
        }

        this.stockQuantity = restStock;
    }
}

// @Inheritance(stratgy = InheritanceType. 1) SINGLE_TABLE 2) TABLE_PER_CLASS, 3) JOINED
//Single table 전략은 한 테이블에 모두 쓰는 것
// table per class는 우리가 만들어준 MOVIE, BOOK, ALBUM 테이블을 만듦
// joined 가장 정규화된 스타일

// DiscriminatorColumn, DiscriminatorValue -> 구분용
