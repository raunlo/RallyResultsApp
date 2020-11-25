package com.ralohmus.rallyresults.persistence.util;

import org.apache.commons.lang3.StringUtils;

import java.util.function.Function;

public final class SpecificationUtils {
    public static final Function<String, String> LIKE_OPERATOR_TRANSFORMER = value -> StringUtils.isBlank(value) ? "%" : "%" + value.toUpperCase() + "%";
}