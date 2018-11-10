package edu.cad.services.years;

import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toCollection;

class YearsFileHandler {
    Set<Integer> readYearsFromFile(File yearsFile) {
        try (Stream<String> lines = Files.lines(yearsFile.toPath(), Charset.forName("UTF-8"))) {
            return lines
                    .filter(StringUtils::isNotBlank)
                    .map(Integer::valueOf)
                    .collect(toCollection(TreeSet::new));

        } catch (IOException e) {
            throw new RuntimeException("Failed to read db years from years file", e);
        }
    }

    void addYearToFile(final int year, final File targetFile) {
        try (var fileWriter = new FileWriter(targetFile, true);
             var bufferedWriter = new BufferedWriter(fileWriter);
             var printWriter = new PrintWriter(bufferedWriter)
        ) {
            printWriter.println(String.valueOf(year));
            printWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(String.format("Failed to add new year to year tracking file: %d", year), e);
        }
    }
}
