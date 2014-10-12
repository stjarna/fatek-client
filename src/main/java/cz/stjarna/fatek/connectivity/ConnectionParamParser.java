package cz.stjarna.fatek.connectivity;

import com.google.common.base.Splitter;
import lombok.Getter;

import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

@Getter
public class ConnectionParamParser {
    public static final String PARAM_SEPARATOR = "=";
    private final String key;
    private final String value;

    ConnectionParamParser(final String param) {
        checkNotNull(param, "Expression cannot be null");
        checkArgument(param.contains(PARAM_SEPARATOR), "Expression must contain '=' as a separator");
        final List<String> paramAndValue = Splitter.on(PARAM_SEPARATOR).splitToList(param.trim());
        this.key = paramAndValue.get(0);
        this.value = paramAndValue.get(1);
    }
}