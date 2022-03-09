package jpabook.jpashop.domain.item;

import lombok.Getter;
import org.springframework.stereotype.Service;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("A")
@Getter
@Service
public class Album extends Item {

    private String artist;
    private String etc;
}
