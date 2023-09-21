package com.omega.discovery.util;

import java.lang.reflect.Field;

public class ReflectionUtil {

    public static Field setFieldValue (Object object, String fieldName, Object fieldValue) throws Exception {
        final Field field = getField(object.getClass(), fieldName);
        field.setAccessible(true);
        field.set(object, fieldValue);
        return field;
    }

    public static Object getPrivateFieldValue(final Object object, final String fieldName) throws Exception {
        Field field = getField(object.getClass(), fieldName);
        field.setAccessible(true);
        return field.get(object);
    }

    private static Field getField (final Class<?> clazz, final String fieldName) throws Exception {
        return clazz.getDeclaredField(fieldName);
    }

}
