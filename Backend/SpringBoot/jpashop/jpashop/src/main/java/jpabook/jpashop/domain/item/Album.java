package jpabook.jpashop.domain.item;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("A")
@AllArgsConstructor @Getter
public class Album extends Item {

    private String artist;
    private String etc;

    protected Album() { }

}
