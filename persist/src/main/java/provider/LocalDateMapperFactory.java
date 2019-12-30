package provider;

import com.github.rkmk.mapper.FieldMapperFactory;


import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class LocalDateMapperFactory implements FieldMapperFactory<LocalDate> {

    @Override
    public LocalDate getValue(ResultSet rs, int index, Class<?> type) throws SQLException {
        return LocalDate.parse(rs.getString("date"));
    }

    @Override
    public Boolean accepts(Class<?> type) {
        return type.isAssignableFrom(LocalDate.class);
    }
}