package cc.hrva.internalservices.controller;

import cc.hrva.internalservices.service.FilePrintService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FilePrintController {

    private final FilePrintService filePrintService;

    @PostMapping("/print")
    public String print() {
        return filePrintService.printFile();
    }

}
