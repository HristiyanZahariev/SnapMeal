package com.snapmeal.security;

import jersey.repackaged.com.google.common.base.Preconditions;

/**
 * Created by hristiyan on 20.12.16.
 */
public final class StringConditions {
    private StringConditions() { }

    public static String checkNotBlank(String string) {
        Preconditions.checkArgument(string != null && string.trim().length() > 0);
        return string;
    }
}
