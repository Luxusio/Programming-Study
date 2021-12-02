package hello.advanced.trace.hellotrace;

import hello.advanced.trace.TraceStatus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HelloTraceV1Test {

    @Test
    void begin_end() {

        HelloTraceV1 trace = new HelloTraceV1();
        TraceStatus hello = trace.begin("hello");

        trace.end(hello);
    }

    @Test
    void begin_exception() {
        HelloTraceV1 trace = new HelloTraceV1();
        TraceStatus status = trace.begin("hello");

        trace.exception(status, new IllegalStateException());
    }

}