package cc.hrva.internalservices.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PrintType {

    DOUBLE_SIDED_ON_LONG_EDGE("two-sided-long-edge"),
    SINGLE_SIDED("one-sided"),
    DOUBLE_SIDED_ON_SHORT_EDGE("two-sided-short-edge");

    private final String printerCommand;

}
