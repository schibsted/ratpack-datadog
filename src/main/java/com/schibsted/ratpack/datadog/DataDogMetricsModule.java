package com.schibsted.ratpack.datadog;

import com.google.inject.AbstractModule;

public class DataDogMetricsModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(DataDogMetricsService.class);
    }
}
