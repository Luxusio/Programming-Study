package jpabook.jpashop.domain.item;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("M")
@AllArgsConstructor @Getter
public class Movie extends Item {

    private String director;
    private String actor;

    protected Movie() { }

}
