package cc.hrva.internalservices.service;

import cc.hrva.internalservices.dto.PrintFileDto;
import com.itextpdf.text.DocumentException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Service
@RequiredArgsConstructor
public class FilePrintService {

    private final FileConverterService fileConverterService;

    public String printFile(PrintFileDto printFileDto, MultipartFile file) throws IOException, DocumentException {
        String pdfFileLocation = fileConverterService.convertFileToPdf(file);

        printFileDto.setPrintFileLocation(pdfFileLocation);
        return executeFilePrint(printFileDto);
    }

    private String executeFilePrint(PrintFileDto printFileDto) throws IOException {
        ProcessBuilder builder = new ProcessBuilder();
        builder.command("lp", printFileDto.getPrintFileLocation(),
                "-n", printFileDto.getCopies().toString(),
                "-o", "sides=" + printFileDto.getPrintType().getPrinterCommand(),
                getPrintRange(printFileDto));

        Process process = builder.start();
        BufferedReader output = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String commandOutput = output.readLine();
        output.close();

        return commandOutput;
    }

    private String getPrintRange(PrintFileDto printFileDto) {
        if (printFileDto.isPrintWholeDocument()) {
            return "";
        }
        return "-P " + printFileDto.getStartPage() + "-" + printFileDto.getEndPage();
    }

}


