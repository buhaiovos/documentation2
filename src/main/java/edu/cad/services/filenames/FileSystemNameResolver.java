package edu.cad.services.filenames;

import edu.cad.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.ServletContext;

@Service
@Deprecated
public class FileSystemNameResolver implements FileNameResolvingService {
    @Value("${cad.generation.path.curriculum}")
    private String curriculumPath;
    @Value("${cad.generation.path.workplan}")
    private String workplanPath;
    @Value("${cad.generation.path.k3}")
    private String k3Path;

    private final ServletContext servletContext;

    public FileSystemNameResolver(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    @Override
    public String resolveForDocument(Document document) {
        switch (document) {
            case CURRICULUM:
                return this.servletContext.getRealPath(curriculumPath);
            case WORK_PLAN:
                return this.servletContext.getRealPath(workplanPath);
            case K3:
                return this.servletContext.getRealPath(k3Path);
            default:
                throw new RuntimeException("Invalid document type");
        }
    }

    @Override
    public String resolveForDatabaseYearsFile() {
        return "blah";
    }
}
