package model;

//import com.bertoncelj.jdbi.entitymapper.Column;
import lombok.*;

import javax.persistence.Column;
import java.time.LocalDate;

@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
public class DateOfGivingOfIndicators extends BaseEntity {
    private Integer id_telegram;
    private LocalDate date;

    public DateOfGivingOfIndicators(int id_telegram, LocalDate date) {
        this(null, id_telegram, date);
    }

    public DateOfGivingOfIndicators(Integer id, Integer id_telegram, LocalDate  date) {
        super(id);
        this.id_telegram = id_telegram;
        this.date = date;
    }
}