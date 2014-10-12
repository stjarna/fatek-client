package cz.stjarna.fatek.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ProtocolEnum {
	TCP(500);

	private int defaultPort;
}
