package com.epam.engx.cleandesign.Utils;

import java.util.Collection;
import java.util.function.ToDoubleFunction;

public class CalculationUtil {

    public static <T> double sumAllValues(Collection<T> coll, ToDoubleFunction<T> extractDouble) {
        return coll.stream().mapToDouble(extractDouble).sum();
    }
}
