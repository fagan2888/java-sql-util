/*
 * Copyright 2016 National Bank of Belgium
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
package internal.sql.lhod;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 *
 * @author Philippe Charles
 */
public class Resources {

    static TabularDataExecutor good() {
        Map<String, String> map = new HashMap<>();
        map.put("DbProperties.vbs", "MyDbConnProperties.tsv");
        map.put("OpenSchema.vbs", "MyDbTables.tsv");
        return query -> {
            try {
                Path path = Paths.get(Resources.class.getResource(map.get(query.getProcedure())).toURI());
                return TabularDataReader.of(Files.newBufferedReader(path, StandardCharsets.UTF_8));
            } catch (URISyntaxException ex) {
                throw new RuntimeException(ex);
            }
        };
    }

    static TabularDataExecutor bad() {
        return ofException(FileNotFoundException::new);
    }

    static TabularDataExecutor ugly() {
        return ofContent("helloworld");
    }

    static TabularDataExecutor err() {
        return ofResource("MyDbErr.tsv");
    }

    static TabularDataExecutor ofResource(String name) {
        try {
            Path path = Paths.get(LhodConnectionTest.class.getResource(name).toURI());
            return query -> TabularDataReader.of(Files.newBufferedReader(path, StandardCharsets.UTF_8));
        } catch (URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }

    static TabularDataExecutor ofException(Supplier<? extends IOException> supplier) {
        return query -> {
            throw supplier.get();
        };
    }

    static TabularDataExecutor ofContent(CharSequence content) {
        return query -> TabularDataReader.of(new BufferedReader(new StringReader(content.toString())));
    }
}