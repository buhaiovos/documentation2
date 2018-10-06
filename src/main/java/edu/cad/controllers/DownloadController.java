package edu.cad.controllers;

import edu.cad.generators.CurriculumGenerator;
import edu.cad.generators.IDocumentGenerator;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@SuppressWarnings("ResultOfMethodCallIgnored")
@RestController
@RequestMapping("/templates")
public class DownloadController extends HttpServlet {
    private static final String CURRICULUM_PATH = "/WEB-INF/classes/templates/CurriculumTemplate.xls";
    private static final String WORKPLAN_PATH = "/WEB-INF/classes/templates/WorkplanTemplate.xls";
    private static final String K3FORM_PATH = "/WEB-INF/classes/templates/K3FormTemplate.xls";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            handle(request, response);
        } catch (IOException | ServletException | RuntimeException e) {
            e.printStackTrace(System.err);
            response.setStatus(500);
        }
    }

    private void handle(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (request.getParameter("downloadGenerated") != null) {
            downloadGenerated(request.getParameter("downloadGenerated"), response);
            return;
        }

        if (request.getParameter("downloadTemplate") != null) {
            String template = request.getParameter("template");
            downloadTemplate(getFilePath(template), getFileName(template), response);
        }

        if (request.getParameter("uploadTemplate") != null) {
            String template = request.getParameter("template");
            uploadTemplate(getFilePath(template), request);
            response.sendRedirect("Templates.html");
        }
    }

    private void downloadGenerated(String download, HttpServletResponse response) throws IOException {
        Workbook workbook = new HSSFWorkbook(new FileInputStream(getFilePath(download)));
        String filename = getFileName(download);

        switch (download) {
            case "curriculum":
            case "workplan":
                downloadGeneratedDocument(new CurriculumGenerator(workbook), filename, response);
                break;
            default:
                throw new UnsupportedOperationException(String.format("<%s> is not supported", download));
        }
    }

    private void addHeaders(HttpServletResponse response, String filename) {
        response.setContentType("application/ms-excel; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-disposition", "attachment; filename*=UTF-8''"
                + URLEncoder.encode(filename, StandardCharsets.UTF_8));
    }

    private void downloadGeneratedDocument(IDocumentGenerator generator, String filename, HttpServletResponse response)
            throws IOException {
        addHeaders(response, filename);
        generator.generate();
        generator.getTemplate().write(response.getOutputStream());
    }

    private void downloadTemplate(String path, String filename, HttpServletResponse response) throws IOException {
        addHeaders(response, filename);
        Files.copy(new File(path).toPath(), response.getOutputStream());
        response.getOutputStream().flush();
    }

    private void uploadTemplate(String path, HttpServletRequest request) throws ServletException, IOException {
        if (Files.exists(Paths.get(path))) {
            new File(path).delete();
        }
        final Part part = request.getPart("file");
        final Path filePath = createNewBlankFileAndReturnPath(path);
        Files.copy(part.getInputStream(), filePath);
    }

    private Path createNewBlankFileAndReturnPath(String path) {
        final var newFile = new File(path);
        newFile.getParentFile().mkdirs();
        return newFile.toPath();
    }

    private String getFileName(String value) {
        switch (value) {
            case "curriculum":
                return "Навчальний_план.xls";
            case "workplan":
                return "Робочий_навчальний_план.xls";
            case "k3form":
                return "Форма_К-3.xls";
        }
        return "";
    }

    private String getFilePath(String value) {
        switch (value) {
            case "curriculum":
                return getServletContext().getRealPath(CURRICULUM_PATH);
            case "workplan":
                return getServletContext().getRealPath(WORKPLAN_PATH);
            case "k3form":
                return getServletContext().getRealPath(K3FORM_PATH);
            default:
                throw new RuntimeException("Invalid document type: ".concat(value));
        }
    }
}
