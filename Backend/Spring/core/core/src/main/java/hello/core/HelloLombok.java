package hello.core;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class HelloLombok {

    private String name;
    private int age;

    public static void main(String[] args) {
        HelloLombok helloLombok = new HelloLombok();
    }

}
