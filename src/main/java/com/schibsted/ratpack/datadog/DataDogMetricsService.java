package com.schibsted.ratpack.datadog;

import com.codahale.metrics.MetricRegistry;
import com.google.inject.Inject;
import org.coursera.metrics.datadog.DatadogReporter;
import org.coursera.metrics.datadog.transport.Transport;
import org.coursera.metrics.datadog.transport.UdpTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ratpack.server.Service;
import ratpack.server.StartEvent;
import ratpack.server.StopEvent;

import java.util.EnumSet;
import java.util.concurrent.TimeUnit;

import static org.coursera.metrics.datadog.DatadogReporter.Expansion.*;

public class DataDogMetricsService implements Service {

    private static final Logger log = LoggerFactory.getLogger(DataDogMetricsService.class);

    private final MetricRegistry registry;
    private DatadogReporter reporter;

    @Inject
    public DataDogMetricsService(MetricRegistry registry) {
        this.registry = registry;
    }

    @Override
    public void onStart(StartEvent event) throws Exception {
        log.info("Service starting...");
        EnumSet<DatadogReporter.Expansion> expansions = EnumSet.of(COUNT, RATE_1_MINUTE, RATE_15_MINUTE, MEDIAN, P95, P99);
        Transport transport = new UdpTransport.Builder().build();
        reporter = DatadogReporter.forRegistry(registry)
                .withEC2Host()
                .withTransport(transport)
                .withExpansions(expansions)
                .withPrefix("application")
                .build();

        reporter.start(1, TimeUnit.MINUTES);
        log.info("Service started.");
    }

    @Override
    public void onStop(StopEvent event) throws Exception {
        log.info("Service stopping...");
        if (reporter != null) {
            reporter.stop();
        }
        log.info("Service stopped.");
    }
}
