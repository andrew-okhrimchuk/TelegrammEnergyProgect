package model;

import lombok.*;

import java.util.Objects;
@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class User extends BaseEntity {

    private Integer id_telegram;
    private Integer [] acc;

    public User(Integer id_telegram, Integer [] acc) {
        this(null, id_telegram, acc);
    }

    public User(Integer id, Integer id_telegram, Integer [] acc) {
        super(id);
        this.id_telegram = id_telegram;
        this.acc = acc;
    }
}