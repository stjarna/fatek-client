package cz.stjarna.fatek.command.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@AllArgsConstructor
public class GistSystemStatus {

    @Getter
    protected int status1;
    @Getter
    protected int status2;
    @Getter
    protected int status3;

    public boolean isRun() {
        // 0b00000001 = 1
        return (status1 & 1) > 0;
    }

    public boolean isLadderChecksumError() {
        // 0b00000100 = 4
        return (status1 & 4) > 0;
    }

    public boolean isUseRomPack() {
        // 0b00001000 = 8
        return (status1 & 8) > 0;
    }

    public boolean isWDTTimeout() {
        // 0b00010000 = 16
        return (status1 & 16) > 0;
    }

    public boolean isSetId() {
        // 0b00100000 = 32
        return (status1 & 32 ) > 0;
    }

    public boolean isEmergencyStop() {
        // 0b01000000 = 64
        return (status1 & 64 ) > 0;
    }
}
