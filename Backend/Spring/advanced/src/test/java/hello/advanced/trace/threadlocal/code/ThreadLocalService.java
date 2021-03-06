package hello.advanced.trace.threadlocal.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ThreadLocalService {

    private final ThreadLocal<String> nameStore = new ThreadLocal<>();

    public String logic(String name) {
        log.info("์ ์ฅ name={} -> nameStore={}", name, nameStore);
        nameStore.set(name);
        sleep(1000);
        log.info("์กฐํ namestore={}", nameStore);

        return nameStore.get();
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            log.error("interruped", e);
        }
    }

}
