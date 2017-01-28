package cz.stjarna.fatek.command;

import cz.stjarna.fatek.command.response.DetailedSystemStatus;
import cz.stjarna.fatek.enums.CommandEnum;

import java.util.function.Function;

public class DetailReadSystemStatusCommand extends AbstractSystemStatusCommand<DetailedSystemStatus> {

    @Override
    public CommandEnum getCommandCode() {
        return CommandEnum.DETAIL_READ_SYSTEM_STATUS;
    }

    @Override
    public String getRequestData() {
        return null;
    }

    @Override
    public Function<byte[], DetailedSystemStatus> getResultFunction() {
        return (byte[] payload) ->
                DetailedSystemStatus.builder()
                        .status1(readByteFromByteArray(payload, 0))
                        .status2(readByteFromByteArray(payload, 2))
                        .status3(readByteFromByteArray(payload, 4))
                        .status4(readByteFromByteArray(payload, 6))
                        .status5(readByteFromByteArray(payload, 8))
                        .status6(readByteFromByteArray(payload, 10))
                        .status7(readByteFromByteArray(payload, 12))
                        .status8(readByteFromByteArray(payload, 14))
                        .status9(readByteFromByteArray(payload, 16))
                        .status10(readByteFromByteArray(payload, 18))
                        .status11(readByteFromByteArray(payload, 20))
                        .status12(readByteFromByteArray(payload, 22))
                        .status13(readByteFromByteArray(payload, 24))
                        .status14(readByteFromByteArray(payload, 26))
                        .status15(readByteFromByteArray(payload, 28))
                        .status16(readByteFromByteArray(payload, 30))
                        .status17(readByteFromByteArray(payload, 32))
                        .status18(readByteFromByteArray(payload, 34))
                        .status19(readByteFromByteArray(payload, 36))
                        .status20(readByteFromByteArray(payload, 38))
                        .status21(readByteFromByteArray(payload, 40))
                        .status22(readByteFromByteArray(payload, 42))
                        .status23(readByteFromByteArray(payload, 44))
                        .status24(readByteFromByteArray(payload, 46))
                        .status25(readByteFromByteArray(payload, 48))
                        .status26(readByteFromByteArray(payload, 50))
                        .status27(readByteFromByteArray(payload, 52))
                        .status28(readByteFromByteArray(payload, 54))
                        .build();
    }
}
