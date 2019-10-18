/*
 * Copyright 2018 National Bank of Belgium
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
package internal.sql.odbc.win;

import internal.sql.jdbc.SqlConnectionSuppliers;
import java.sql.Connection;
import java.sql.SQLException;
import nbbrd.service.ServiceProvider;
import nbbrd.sql.jdbc.SqlConnectionSupplier;
import nbbrd.sql.odbc.OdbcConnectionSupplierSpi;

/**
 *
 * @author Philippe Charles
 */
@ServiceProvider(OdbcConnectionSupplierSpi.class)
public final class SunOdbcConnectionSupplier implements OdbcConnectionSupplierSpi {

    private static final String JDBC_ODBC_DRIVER_NAME = "sun.jdbc.odbc.JdbcOdbcDriver";
    private static final String JDBC_ODBC_DRIVER_PREFIX = "jdbc:odbc:";

    private final SqlConnectionSupplier delegate = SqlConnectionSupplier.usingDriverManager(JDBC_ODBC_DRIVER_NAME, o -> JDBC_ODBC_DRIVER_PREFIX + o);

    @Override
    public String getName() {
        return JDBC_ODBC_DRIVER_NAME;
    }

    @Override
    public boolean isAvailable() {
        return !is64bit() && SqlConnectionSuppliers.isDriverAvailable(JDBC_ODBC_DRIVER_NAME);
    }

    @Override
    public Connection getConnection(String connectionString) throws SQLException {
        return delegate.getConnection(connectionString);
    }

    private static boolean is64bit() {
        return "amd64".equals(System.getProperty("os.arch"));
    }
}
