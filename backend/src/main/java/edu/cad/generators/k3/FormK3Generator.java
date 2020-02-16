package edu.cad.generators.k3;

import edu.cad.generators.DocumentGenerator;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FormK3Generator extends DocumentGenerator {

    private final List<AbstractK3Generator> generators;

    @Override
    public void fillInSheet(Sheet sheet) {
        generators.stream()
                .filter(generator -> generator.canGenerate(sheet))
                .findAny()
                .ifPresent(generator -> generator.fillInSheet(sheet));
    }

}
