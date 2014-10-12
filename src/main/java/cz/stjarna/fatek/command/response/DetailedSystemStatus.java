package cz.stjarna.fatek.command.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Builder;

@ToString
@AllArgsConstructor
@Builder
@Getter
public class DetailedSystemStatus {

    protected int status1;

    protected int status2;

    protected int status3;

    protected int status4;

    protected int status5;

    protected int status6;

    protected int status7;

    protected int status8;

    protected int status9;

    protected int status10;

    protected int status11;

    protected int status12;

    protected int status13;

    protected int status14;

    protected int status15;

    protected int status16;

    protected int status17;

    protected int status18;

    protected int status19;

    protected int status20;

    protected int status21;

    protected int status22;

    protected int status23;

    protected int status24;

    protected int status25;

    protected int status26;

    protected int status27;

    protected int status28;

    public boolean isRun() {
        // 0b00000001 = 1
        return (status1 & 1) > 0;
    }

    public boolean isBatteryLow() {
        // 0b00000010 = 2
        return (status1 & 2) > 0;
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
        return (status1 & 32) > 0;
    }

    public boolean isUrgentStop() {
        // 0b01000000 = 64
        return (status1 & 64) > 0;
    }

    public int getMainUnitType() {
        return status2;
    }

    public int getMainUnitIOPoints() {
        return status3;
    }

    public int getOSVersion() {
        return status4;
    }

    public int getLadderSize() {
        return status5 << 8 | status6;
    }

    public int getDiscreteInput() {
        return status7 << 8 | status8;
    }

    public int getDiscreteOutput() {
        return status9 << 8 | status10;
    }

    public int getAnalogInput() {
        return status11 << 8 | status12;
    }

    public int getAnalogOutput() {
        return status13 << 8 | status14;
    }

    public int getMRelay() {
        return status15 << 8 | status16;
    }

    public int getSRelay() {
        return status17 << 8 | status18;
    }

    public int getLRelay() {
        return status19 << 8 | status20;
    }

    public int getRRelay() {
        return status21 << 8 | status22;
    }

    public int getDRelay() {
        return status23 << 8 | status24;
    }

    public int getTimer() {
        return status25 << 8 | status26;
    }

    public int getCounter() {
        return status27 << 8 | status28;
    }
}
