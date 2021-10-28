package hello.typeconverter.formatter;

import hello.typeconverter.converter.IpPortToStringConverter;
import hello.typeconverter.converter.StringToIpPortConverter;
import hello.typeconverter.type.IpPort;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.format.support.DefaultFormattingConversionService;

import static org.assertj.core.api.Assertions.assertThat;

public class FormattingConversionServiceTest {

    @Test
    void formattingConversionService() {
        DefaultFormattingConversionService conversionService = new DefaultFormattingConversionService();
        // add converter
        conversionService.addConverter(new StringToIpPortConverter());
        conversionService.addConverter(new IpPortToStringConverter());
        // add formatter
        conversionService.addFormatter(new MyNumberFormatter());

        // use converter
        IpPort ipPort = conversionService.convert("127.0.0.1:8080", IpPort.class);
        String numberStrResult = conversionService.convert(1000, String.class);
        Number numberResult = conversionService.convert("1,000", Long.class);

        assertThat(ipPort).isEqualTo(new IpPort("127.0.0.1", 8080));
        assertThat(numberStrResult).isEqualTo("1,000");
        assertThat(numberResult).isEqualTo(1000L);
    }

}
