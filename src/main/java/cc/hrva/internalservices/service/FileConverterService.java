package cc.hrva.internalservices.service;

import cc.hrva.internalservices.configuration.FileConfiguration;
import cc.hrva.internalservices.util.ConvertFileTypesUtil;
import cc.hrva.internalservices.util.FilePathUtil;
import com.itextpdf.text.DocumentException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class FileConverterService {

    private final FileConfiguration fileConfiguration;

    public String convertFileToPdf(MultipartFile file) throws IOException, DocumentException {
        String copiedFileLocation = FilePathUtil.saveFileLocally(file, fileConfiguration.getCopyLocation());

        switch (file.getContentType()) {
            case MediaType.APPLICATION_PDF_VALUE:
                return copiedFileLocation;
            case "application/vnd.openxmlformats-officedocument.wordprocessingml.document":
                String pdfFileLocation = FilePathUtil.getFilePath(fileConfiguration.getCopyLocation(), file.getOriginalFilename() + ".pdf");
                ConvertFileTypesUtil.convertDocxToPdf(copiedFileLocation, pdfFileLocation);
                return pdfFileLocation;
            case null, default:
                throw new IllegalArgumentException("File type not supported");
        }

    }

}
