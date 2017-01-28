package cz.stjarna.fatek.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.EnumSet;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
public enum ErrorCodeEnum {
    ERROR_FREE(0x0, "Error free"),
    ILLEGAL_VALUE(0x2, "Illegal value."),
    ILLEGAL_FORMAT(0x4, "Illegal format, or communication command can not execute."),
    CANNOT_RUN_CHECKSUM_ERROR(0x5, "Can not run (Ladder Checksum error when run PLC)"),
    CANNOT_RUN_LADDER_ID_ERROR(0x6, "Can not run (PLC ID != Ladder ID when run PLC)"),
    CANNOT_RUN_SYNTAX_ERROR(0x7, "Can not run (Syntax check error when run PLC)"),
    CANNOT_RUN_FUNCTION_NOT_SUPPORTED_ERROR(0x9, "Can not run (Function not supported)"),
    ILLEGAL_ADDRESS(0xA, "Illegal address");

    private int errorCode;

    private String errorMessage;

    private static final Map<Integer, ErrorCodeEnum> LOOKUP;

    static {
        LOOKUP = EnumSet.allOf(ErrorCodeEnum.class).stream().collect(Collectors.toMap(ErrorCodeEnum::getErrorCode, Function.identity()));
    }

    public static ErrorCodeEnum get(final int code) {
        return LOOKUP.get(code);
    }

    @Override
    public String toString() {
        return String.format("error code %X (%s)", errorCode, errorMessage);
    }
}
