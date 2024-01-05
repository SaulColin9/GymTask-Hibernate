package org.example.configuration.metric;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.example.controller.TrainingTypeController;

import java.util.function.Supplier;

public class TrainingTypesMetric {
    protected TrainingTypeController trainingTypeController;

    public Supplier<Number> fetchTrainingTypesCount() {
        return () -> trainingTypeController.getTrainingTypes().size();
    }

    public TrainingTypesMetric(MeterRegistry registry) {
        Gauge.builder("trainingTypesController.trainingTypesCount", fetchTrainingTypesCount())
                .description("Training Types count")
                .register(registry);
    }

    public void setTrainingTypeController(TrainingTypeController trainingTypeController) {
        this.trainingTypeController = trainingTypeController;
    }
}
