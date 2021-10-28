package hello.typeconverter.converter;

import hello.typeconverter.type.IpPort;
import org.junit.jupiter.api.Test;
import org.springframework.core.convert.support.DefaultConversionService;

import static org.assertj.core.api.Assertions.assertThat;

public class ConversionServiceTest {

    @Test
    void conversionService() {
        // given
        DefaultConversionService conversionService = new DefaultConversionService();
        conversionService.addConverter(new StringToIntegerConverter());
        conversionService.addConverter(new IntegerToStringConverter());
        conversionService.addConverter(new StringToIpPortConverter());
        conversionService.addConverter(new IpPortToStringConverter());

        // when
        Integer result = conversionService.convert("10", Integer.class);
        String strResult = conversionService.convert(10, String.class);
        IpPort ipPortResult = conversionService.convert("127.0.0.1:8080", IpPort.class);
        String ipPortStrResult = conversionService.convert(new IpPort("127.0.0.1", 8080), String.class);


        // then
        assertThat(result).isEqualTo(10);
        assertThat(strResult).isEqualTo("10");
        assertThat(ipPortResult).isEqualTo(new IpPort("127.0.0.1", 8080));
        assertThat(ipPortStrResult).isEqualTo("127.0.0.1:8080");
    }

}
