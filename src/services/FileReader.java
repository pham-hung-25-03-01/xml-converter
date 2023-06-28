package services;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.univocity.parsers.common.processor.BatchedColumnProcessor;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;

import common.Config;

public class FileReader {
    public Map<String, Object> readFile(File sourceFile) throws IOException {
        Reader inputReader = new InputStreamReader(new FileInputStream(sourceFile), "UTF-8");
        CsvParserSettings settings = new CsvParserSettings();
        String delimiter = Config.getSystem("DELIMITER");
        if (delimiter != null && delimiter.length() > 0) {
            settings.getFormat().setDelimiter(delimiter.charAt(0));
        } else {
            settings.detectFormatAutomatically();
        }
        settings.setLineSeparatorDetectionEnabled(true);
        settings.setHeaderExtractionEnabled(true);
        settings.setProcessor(new BatchedColumnProcessor(1000) {
            @Override
            public void batchProcessed(int rowsInThisBatch) {
            }
        });

        CsvParser parser = new CsvParser(settings);

        List<String[]> rows = parser.parseAll(inputReader);

        String[] parsedHeaders = parser.getRecordMetadata().headers();
        HashMap<String, Integer> headers = new HashMap<>();
        int index = 0;
        for (String header : parsedHeaders) {
            headers.put(header, index++);
        }

        parser.stopParsing();

        Map<String, Object> result = new HashMap<>();
        result.put("headers", headers);
        result.put("rows", rows);

        return result;
    }

}