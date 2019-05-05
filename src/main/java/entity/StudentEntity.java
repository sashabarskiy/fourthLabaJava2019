package entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class StudentEntity {

    Long id;

    String name;

    String surname;

    String group;

    Integer mark;

}
