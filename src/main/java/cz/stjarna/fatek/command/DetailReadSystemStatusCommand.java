package cz.stjarna.fatek.command;

import cz.stjarna.fatek.command.response.DetailedSystemStatus;
import cz.stjarna.fatek.command.response.Response;
import cz.stjarna.fatek.enums.CommandEnum;

import static com.google.common.base.Preconditions.checkNotNull;

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
	public DetailedSystemStatus getResponseData(final Response response) {
        checkNotNull(response, "Response cannot be null");
        return DetailedSystemStatus.builder()
                .status1(readByteFromByteArray(response.getPayload(), 0))
                .status2(readByteFromByteArray(response.getPayload(), 2))
                .status3(readByteFromByteArray(response.getPayload(), 4))
                .status4(readByteFromByteArray(response.getPayload(), 6))
                .status5(readByteFromByteArray(response.getPayload(), 8))
                .status6(readByteFromByteArray(response.getPayload(), 10))
                .status7(readByteFromByteArray(response.getPayload(), 12))
                .status8(readByteFromByteArray(response.getPayload(), 14))
                .status9(readByteFromByteArray(response.getPayload(), 16))
                .status10(readByteFromByteArray(response.getPayload(), 18))
                .status11(readByteFromByteArray(response.getPayload(), 20))
                .status12(readByteFromByteArray(response.getPayload(), 22))
                .status13(readByteFromByteArray(response.getPayload(), 24))
                .status14(readByteFromByteArray(response.getPayload(), 26))
                .status15(readByteFromByteArray(response.getPayload(), 28))
                .status16(readByteFromByteArray(response.getPayload(), 30))
                .status17(readByteFromByteArray(response.getPayload(), 32))
                .status18(readByteFromByteArray(response.getPayload(), 34))
                .status19(readByteFromByteArray(response.getPayload(), 36))
                .status20(readByteFromByteArray(response.getPayload(), 38))
                .status21(readByteFromByteArray(response.getPayload(), 40))
                .status22(readByteFromByteArray(response.getPayload(), 42))
                .status23(readByteFromByteArray(response.getPayload(), 44))
                .status24(readByteFromByteArray(response.getPayload(), 46))
                .status25(readByteFromByteArray(response.getPayload(), 48))
                .status26(readByteFromByteArray(response.getPayload(), 50))
                .status27(readByteFromByteArray(response.getPayload(), 52))
                .status28(readByteFromByteArray(response.getPayload(), 54))
                .build();
	}
}
