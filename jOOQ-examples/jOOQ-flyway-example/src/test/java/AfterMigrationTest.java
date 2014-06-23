import org.jooq.Result;
import org.jooq.impl.DSL;
import org.junit.Test;

import static java.util.Arrays.asList;
import static org.jooq.example.flyway.db.h2.Tables.*;
import static org.junit.Assert.assertEquals;

/**
 * Created by Lukas on 23.06.2014.
 */
public class AfterMigrationTest {

    @Test
    public void testQueryingAfterMigration() {
        Result<?> result =
        DSL.using("jdbc:h2:~/flyway-test", "sa", "")
           .select(
               AUTHOR.FIRST_NAME,
               AUTHOR.LAST_NAME,
               BOOK.ID,
               BOOK.TITLE
           )
           .from(AUTHOR)
           .join(BOOK)
           .on(AUTHOR.ID.eq(BOOK.AUTHOR_ID))
           .orderBy(BOOK.ID.asc())
           .fetch();

        assertEquals(4, result.size());
        assertEquals(asList(1, 2, 3, 4), result.getValues(BOOK.ID));
    }
}
