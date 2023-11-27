package cc.hrva.internalservices.service;

import cc.hrva.internalservices.configuration.FileConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FilePrintService {

    private final FileConfiguration fileConfiguration;

    public String printFile() {
        return "Printed, " + fileConfiguration.getCopyLocation();
    }
}
