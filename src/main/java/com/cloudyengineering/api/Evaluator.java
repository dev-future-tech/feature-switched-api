package com.cloudyengineering.api;

import java.util.Optional;

public interface Evaluator<T> {
    Optional<T> evaluate(String customerId, String flag);
}
