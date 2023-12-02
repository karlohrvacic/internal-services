package cc.hrva.internalservices.dto;

import cc.hrva.internalservices.enums.PrintType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class PrintFileDto {

    @Positive
    private Integer copies;

    @NotNull
    private PrintType printType;

    @NotNull
    private boolean printWholeDocument;
    private Integer startPage;
    private Integer endPage;

    private String printFileLocation;

}
