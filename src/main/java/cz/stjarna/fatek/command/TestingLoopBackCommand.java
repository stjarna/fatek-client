package cz.stjarna.fatek.command;

import cz.stjarna.fatek.command.response.Response;
import cz.stjarna.fatek.enums.CommandEnum;
import cz.stjarna.fatek.exception.FatekException;

import static com.google.common.base.Preconditions.checkNotNull;

public class TestingLoopBackCommand extends AbstractCommand<String> {

	private final String testingData;

	public TestingLoopBackCommand(final String testingData) throws FatekException {
        checkNotNull(testingData, "Response cannot be null");
		this.testingData = testingData;
	}

	@Override
	public CommandEnum getCommandCode() {
		return CommandEnum.TESTING_LOOP_BACK;
	}

	@Override
	public String getRequestData() {
        return testingData;
	}

    @Override
	public String getResponseData(final Response response) {
        checkNotNull(response, "Response cannot be null");
        final byte[] payload = response.getPayload();
        return new String(payload);
	}

}
