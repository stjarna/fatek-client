package cz.stjarna.fatek.connectivity;

import com.google.common.collect.Maps;
import cz.stjarna.fatek.enums.ProtocolEnum;
import cz.stjarna.fatek.exception.FatekException;
import cz.stjarna.fatek.util.CommonConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;


@Slf4j
public class ConnectionParams {

	private static final String PARAM_DELIMITER = "&";

	private static final String PARAM_SLAVE_STATION_ID = "slaveStationId";
    private static final int PARAM_SLAVE_STATION_ID_DEFAULT = 1;

    private static final String PARAM_CP_INITIAL_SIZE = "initialSize";
    private static final int PARAM_CP_INITIAL_SIZE_DEFAULT = 1;

    private static final String PARAM_CP_MAX_SIZE = "maxSize";
    private static final int PARAM_CP_MAX_SIZE_DEFAULT = 3;

    private static final String PARAM_CP_CHECK_INTERVAL = "checkInterval";
    private static final int PARAM_CP_CHECK_INTERVAL_DEFAULT = 20000;
	
	private final Map<String,String> params;
	private final URI uri;


    public ConnectionParams(final URI uri) throws FatekException {
        this.params = Maps.newHashMap();
        this.uri = uri;
        parseURLParams(uri);
    }
	
	private void parseURLParams(final URI url) throws FatekException {
        checkNotNull(url, "Url cannot be null");
		log.debug("URL params parsing started");
		final String unescapedUriString = decodeEscapedURL(uri.getRawQuery());
		if (!StringUtils.isBlank(unescapedUriString)) {
            ConnectionParamParser paramParser;
			for (final String paramString : StringUtils.split(unescapedUriString, PARAM_DELIMITER)) {
				log.debug("URL param: {}", paramString);
				paramParser = new ConnectionParamParser(paramString);
				params.put(paramParser.getKey(), paramParser.getValue());
			}
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
    	final String scheme = uri.getScheme();
        return ProtocolEnum.valueOf(scheme.toUpperCase());
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
    
    public int getStationId() {
        int result = PARAM_SLAVE_STATION_ID_DEFAULT;
        if (params.containsKey(PARAM_SLAVE_STATION_ID)) {
            result = Integer.parseInt(params.get(PARAM_SLAVE_STATION_ID));
        }
        return result;
    }

    public int getInitialSize() {
        int result = PARAM_CP_INITIAL_SIZE_DEFAULT;
        if (params.containsKey(PARAM_CP_INITIAL_SIZE)) {
            result = Integer.parseInt(params.get(PARAM_CP_INITIAL_SIZE));
        }
        return result;
    }

    public int getMaxSize() {
        int result = PARAM_CP_MAX_SIZE_DEFAULT;
        if (params.containsKey(PARAM_CP_MAX_SIZE)) {
            result = Integer.parseInt(params.get(PARAM_CP_MAX_SIZE));
        }
        return result;
    }

    public long getCheckInterval() {
        int result = PARAM_CP_CHECK_INTERVAL_DEFAULT;
        if (params.containsKey(PARAM_CP_CHECK_INTERVAL)) {
            result = Integer.parseInt(params.get(PARAM_CP_CHECK_INTERVAL));
        }
        return result;
    }
}
