/*
 * Copyright 2013 National Bank of Belgium
 * 
 * Licensed under the EUPL, Version 1.1 or - as soon they will be approved 
 * by the European Commission - subsequent versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the Licence.
 * You may obtain a copy of the Licence at:
 * 
 * http://ec.europa.eu/idabc/eupl
 * 
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and 
 * limitations under the Licence.
 */
package internal.sql.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Philippe Charles
 */
public class SqlIdentifierQuoterImplTest {

    @Test
    public void testGetIdentifierQuoteString() throws SQLException {
        try (Connection conn = DriverManager.getConnection("jdbc:hsqldb:res:mydb", "sa", "")) {
            assertEquals("\"", SqlIdentifierQuoterImpl.getIdentifierQuoteString(conn.getMetaData()));
        }
    }

    public void testGetSqlKeywords() {
        String sqlServerKeywords = "BREAK,BROWSE,BULK,CHECKPOINT,CLUSTERED,COMMITTED,COMPUTE,CONFIRM,CONTROLROW,DATABASE,DBCC,DISK,DISTRIBUTED,DUMMY,DUMP,ERRLVL,ERROREXIT,EXIT,FILE,FILLFACTOR,FLOPPY,HOLDLOCK,IDENTITY_INSERT,IDENTITYCOL,IF,KILL,LINENO,LOAD,MIRROREXIT,NONCLUSTERED,OFF,OFFSETS,ONCE,OVER,PERCENT,PERM,PERMANENT,PLAN,PRINT,PROC,PROCESSEXIT,RAISERROR,READ,READTEXT,RECONFIGURE,REPEATABLE,RETURN,ROWCOUNT,RULE,SAVE,SERIALIZABLE,SETUSER,SHUTDOWN,STATISTICS,TAPE,TEMP,TEXTSIZE,TOP,TRAN,TRIGGER,TRUNCATE,TSEQUEL,UNCOMMITTED,UPDATETEXT,USE,WAITFOR,WHILE,WRITETEXT";
    }
}
