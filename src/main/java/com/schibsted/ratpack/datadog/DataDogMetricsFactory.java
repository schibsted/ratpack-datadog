package com.schibsted.ratpack.datadog;

import com.google.inject.Module;
import com.google.inject.util.Modules;

public class DataDogMetricsFactory {

    private Boolean enabled;

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Module buildModule() {
        if (enabled != null && enabled) {
            return new DataDogMetricsModule();
        }
        return Modules.EMPTY_MODULE;
    }
}
