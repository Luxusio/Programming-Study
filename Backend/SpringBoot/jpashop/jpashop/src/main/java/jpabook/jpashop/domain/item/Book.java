package jpabook.jpashop.domain.item;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("B")
@AllArgsConstructor @Getter
public class Book extends Item {

    private String author;
    private String isbn;

    protected Book() { }

}
