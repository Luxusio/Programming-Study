package jpabook.jpashop.domain;

import lombok.*;

import javax.persistence.Embeddable;

import static lombok.AccessLevel.PROTECTED;

@Embeddable
@AllArgsConstructor @NoArgsConstructor(access = PROTECTED)
@Getter @Setter
public class Address {

    private String city;
    private String street;
    private String zipCode;

}
