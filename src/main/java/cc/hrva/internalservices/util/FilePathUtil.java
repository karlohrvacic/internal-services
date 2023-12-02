package cc.hrva.internalservices.util;

import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RequiredArgsConstructor
public class FilePathUtil {

    public static String getFilePath(String copyLocation, String fileName) {
        return copyLocation + "/" + UUID.randomUUID() + "-" + fileName;
    }

    public static String saveFileLocally(MultipartFile file, String copyLocation) throws IOException {
        String destination = getFilePath(copyLocation, file.getOriginalFilename());
        File copiedFileLocation = new File(destination);
        file.transferTo(copiedFileLocation);

        return destination;
    }
}
