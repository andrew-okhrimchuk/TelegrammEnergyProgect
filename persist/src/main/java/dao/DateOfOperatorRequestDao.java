package dao;

import model.DateOfOperatorRequest;
import org.skife.jdbi.v2.sqlobject.*;

import java.time.LocalDate;
import java.util.List;

public abstract class DateOfOperatorRequestDao implements AbstractDao {

    public DateOfOperatorRequest insert(DateOfOperatorRequest date) {
        if (date.isNew()) {
            int id = insertGeneratedId(date);
            date.setId(id);
        } else {
            insertGeneratedWithId(date);
        }
        return date;
    }

    @SqlUpdate("INSERT INTO conversation_with_the_operator (id_telegram, date) VALUES (:id_telegram, :date) ON CONFLICT ON CONSTRAINT id_telegram_date_2 DO NOTHING ")
    @GetGeneratedKeys
    abstract int insertGeneratedId(@BindBean DateOfOperatorRequest dateOfOperatorRequest);

    @SqlUpdate("INSERT INTO conversation_with_the_operator (id, id_telegram, date) VALUES (:id, :id_telegram, :date) ON CONFLICT ON CONSTRAINT id_telegram_date_2 DO NOTHING ")
    abstract int insertGeneratedWithId(@BindBean DateOfOperatorRequest dateOfOperatorRequest);

    @SqlQuery("SELECT * FROM conversation_with_the_operator WHERE id_telegram = :id_telegram AND date = :date")
    public abstract DateOfOperatorRequest getWithIdTelegramDate(@Bind("id_telegram") int id_telegram, @Bind("date") LocalDate date);

    @SqlQuery("SELECT * FROM conversation_with_the_operator WHERE id_telegram = :id_telegram")
    public abstract List<DateOfOperatorRequest> getWithIdTelegram(@Bind("id_telegram") int id_telegram);

    @SqlQuery("SELECT COUNT (*) FROM conversation_with_the_operator WHERE  date >= :start AND date <= :end")
    public abstract Integer getCountBetween(@Bind("start") LocalDate start, @Bind("end") LocalDate end);

    @SqlQuery("SELECT COUNT (*) FROM conversation_with_the_operator ")
    public abstract Integer getAllCount();

    //   http://stackoverflow.com/questions/13223820/postgresql-delete-all-content
    @SqlUpdate("TRUNCATE conversation_with_the_operator")
    @Override
    public abstract void clean();


}
