package model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
public class DateOfOperatorRequest extends BaseEntity {
    private Integer id_telegram;
    private Date date;

    public DateOfOperatorRequest(int id_telegram, Date date) {
        this(null, id_telegram, date);
    }

    public DateOfOperatorRequest(Integer id, Integer id_telegram, Date date) {
        super(id);
        this.id_telegram = id_telegram;
        this.date = date;
    }
}