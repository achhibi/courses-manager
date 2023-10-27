package kata.coursesmanager.prometheus;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import lombok.experimental.UtilityClass;
import io.micrometer.core.instrument.Tag;


import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class TimerTasks{
    private  static final String APP_NAME = "AJUST";

    public static Timer buildTimer(MeterRegistry meterRegistry) {
        List<Tag> tagsArray = new ArrayList<>();

        tagsArray.add(Tag.of("service", "TEST-Service"));
        tagsArray.add(Tag.of("operation", "Test-operation"));
        tagsArray.add(Tag.of("demandeur", APP_NAME));
        tagsArray.add(Tag.of("test-name", "TEST-name"));
        Iterable<Tag> iterable = tagsArray;
        meterRegistry.gauge("test-name", iterable, 1);
        Gauge.builder("test-duration", "serviceStatus", a -> 12l)
                .tags(iterable)
                .strongReference(true)
                .register(meterRegistry);
        return meterRegistry.timer("test-name3", tagsArray);

    }
}