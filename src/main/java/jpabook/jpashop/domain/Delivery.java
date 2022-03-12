package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@Setter
public class Delivery { //배송

    @Id
    @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery", fetch = LAZY)
    private Order order;

    @Embedded
    private Address address; //내장 타입

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status; // READY(배송준비), COMP(배송)
    // enum타입을 사용할 때 @Enumerated 사용 이때 타입은 ORDINAL(default), STRING
    // 주의할 점. ordinal은 칼럼에 1, 2, ... 로 저장 나중에 문제 발생할 수 있음. 그래서 꼭 String 사용할 것

}
