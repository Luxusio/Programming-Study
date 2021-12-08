package hello.proxy.proxyfactory;

import hello.proxy.common.service.ServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ProxyFactoryTest {

    @Test
    @DisplayName("인터페이스가 있으면 JDK 동적 게획서 사용")
    void interfaceProxy() {
        ServiceImpl service = new ServiceImpl();
    }

}
