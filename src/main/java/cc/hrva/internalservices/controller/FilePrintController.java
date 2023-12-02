package cc.hrva.internalservices.controller;

import cc.hrva.internalservices.dto.PrintFileDto;
import cc.hrva.internalservices.service.FilePrintService;
import com.itextpdf.text.DocumentException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@CrossOrigin
@RestController
@RequiredArgsConstructor
public class FilePrintController {

    private final FilePrintService filePrintService;

    @PostMapping(value = "/print")
    public ResponseEntity<String> print(PrintFileDto printFileDto, @RequestBody MultipartFile file) throws IOException, DocumentException {
        return ResponseEntity.ok(filePrintService.printFile(printFileDto, file));
    }

}
