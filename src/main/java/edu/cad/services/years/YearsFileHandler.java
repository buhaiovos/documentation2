package edu.cad.services.years;

import java.io.*;
import java.nio.file.Files;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class YearsFileHandler {
    Set<Integer> readYearsFromFile(File yearsFile) {
        try (Stream<String> lines = Files.lines(yearsFile.toPath())) {
            return lines
                    .mapToInt(Integer::valueOf)
                    .boxed()
                    .collect(Collectors.toCollection(TreeSet::new));
        } catch (IOException e) {
            throw new RuntimeException("Failed to read db years from years file", e);
        }
    }

    void addYearToFile(final int year, final File targetFile) {
        try (var fileWriter = new FileWriter(targetFile, true);
             var bufferedWriter = new BufferedWriter(fileWriter);
             var printWriter = new PrintWriter(bufferedWriter)
        ) {
            printWriter.printf("\n%d", year);
            printWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(String.format("Failed to add new year to year tracking file: %d", year), e);
        }
    }
}
