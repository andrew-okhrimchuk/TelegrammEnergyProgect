package provider;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.Argument;
import org.skife.jdbi.v2.tweak.ArgumentFactory;

import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class IntArrayArgument implements ArgumentFactory<Integer[]> {
    @Override
    public boolean accepts(Class<?> expectedType, Object value, StatementContext ctx) {
        return value != null && Integer[].class.isAssignableFrom(value.getClass());
    }

    @Override
    public Argument build(Class<?> expectedType, final Integer[] value, StatementContext ctx) {
        return new Argument() {
            @Override
            public void apply(int position, PreparedStatement statement, StatementContext ctx) throws SQLException {
                Array values = statement.getConnection().createArrayOf("Integer", value);
                statement.setArray(position, values);
            }
        };
    }
}