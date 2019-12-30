package dao;

import com.github.rkmk.mapper.CustomMapperFactory;
import model.DateOfGivingOfIndicators;
import org.skife.jdbi.v2.sqlobject.*;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapperFactory;

import java.time.LocalDate;

public abstract class DateOfGivingOfIndicatorsDao implements AbstractDao {

    public DateOfGivingOfIndicators insert(DateOfGivingOfIndicators date) {
        if (date.isNew()) {
            int id = insertGeneratedId(date);
            date.setId(id);
        } else {
            insertGeneratedWithId(date);
        }
        return date;
    }

    @SqlUpdate("INSERT INTO giving_of_indicator (id_telegram, date) VALUES (:id_telegram, :date) ON CONFLICT ON CONSTRAINT id_telegram_date DO NOTHING ")
    @GetGeneratedKeys
    abstract int insertGeneratedId(@BindBean DateOfGivingOfIndicators dateOfGivingOfIndicators);

    @SqlUpdate("INSERT INTO giving_of_indicator (id, id_telegram, date) VALUES (:id, :id_telegram, :date) ON CONFLICT ON CONSTRAINT id_telegram_date DO NOTHING ")
    abstract int insertGeneratedWithId(@BindBean DateOfGivingOfIndicators dateOfGivingOfIndicators);

    @SqlQuery("SELECT * FROM giving_of_indicator WHERE id_telegram = :id_telegram")
    public abstract DateOfGivingOfIndicators getWithIdTelegram(@Bind("id_telegram") int id_telegram);

    @SqlQuery("SELECT COUNT (*) FROM giving_of_indicator WHERE  date >= :start AND date <= :end")
    public abstract Integer getCountBetween(@Bind("start") LocalDate start, @Bind("end") LocalDate end);

    @SqlQuery("SELECT COUNT (*) FROM giving_of_indicator ")
    public abstract Integer getAllCount();

    //   http://stackoverflow.com/questions/13223820/postgresql-delete-all-content
    @SqlUpdate("TRUNCATE giving_of_indicator")
    @Override
    public abstract void clean();


}
