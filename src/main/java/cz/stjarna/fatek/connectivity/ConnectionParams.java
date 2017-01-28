package cz.stjarna.fatek.connectivity;

import cz.stjarna.fatek.enums.ProtocolEnum;
import cz.stjarna.fatek.exception.FatekException;
import cz.stjarna.fatek.util.CommonConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkNotNull;


@Slf4j
public class ConnectionParams {

	private static final String PARAM_DELIMITER = "&";

	private static final String PARAM_SLAVE_STATION_ID = "slaveStationId";
    private static final int PARAM_SLAVE_STATION_ID_DEFAULT = 1;

	private final Map<String,String> params;
	private final URI uri;


    public ConnectionParams(final URI uri) throws FatekException {
        this.params = new HashMap();
        this.uri = uri;
        parseURLParams(uri);
    }
	
	private void parseURLParams(final URI url) throws FatekException {
        checkNotNull(url, "Url cannot be null");
		log.debug("URL params parsing started");
		final String unescapedUriString = decodeEscapedURL(uri.getRawQuery());
		if (StringUtils.isNotEmpty(unescapedUriString)) {
            params.putAll(Arrays.stream(unescapedUriString.split(PARAM_DELIMITER))
                    .map(paramString -> {
                        log.debug("URL param: {}", paramString);
                        return new ConnectionParamParser(paramString);
                    })
                    .collect(Collectors.toMap(ConnectionParamParser::getKey, ConnectionParamParser::getValue)));
		}
		log.debug("URL params parsing completed");
	}

	private String decodeEscapedURL(final String val) throws FatekException {
		try {
			return URLDecoder.decode(val, CommonConstants.ENCONDING_ASCII);
		} catch (UnsupportedEncodingException e) {
			throw new FatekException(String.format("Could not decode provided URL %s", val), e);
		}
	}

    public String getParam(final String key) {
        checkNotNull(key, "Key cannot be null");
        return params.get(key);
    }
    
    public ProtocolEnum getProtocol() {
        return ProtocolEnum.valueOf(uri.getScheme().toUpperCase());
    }
    
    public String getHost() {
        return uri.getHost();
    }    
    
    public int getPort() {
        int port = uri.getPort();
        if (port < 0) {
            port = getProtocol().getDefaultPort();
            log.debug("Explicit port not provided, default value {} used instead", port);
        }
        return port;
    }
    
    public byte getStationId() {
        byte result = PARAM_SLAVE_STATION_ID_DEFAULT;
        if (params.containsKey(PARAM_SLAVE_STATION_ID)) {
            result = Byte.parseByte(params.get(PARAM_SLAVE_STATION_ID));
        }
        return result;
    }
}
