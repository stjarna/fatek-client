package cz.stjarna.fatek.command.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@AllArgsConstructor
@Getter
public class Response <RESULT_TYPE>{

    private final int slaveStationId;
    private final int commandCode;
    private final int checkSum;
    private final byte[] messageByteArray;
    private final byte[] payload;
    private RESULT_TYPE result;

}
