package com.ggasoftware.jdiuitests.core.utils.common;

import com.ggasoftware.jdiuitests.core.utils.usefulUtils.TryCatchUtil;

import java.lang.reflect.Field;
import java.util.List;

import static java.util.Arrays.asList;

/**
 * Created by Roman_Iovlev on 7/8/2015.
 */
public class EnumUtils {
    public static String getEnumValue(Enum enumWithValue) {
        Class<?> type = enumWithValue.getClass();
        Field[] fields = type.getDeclaredFields();
        Field field;
        switch (fields.length) {
            case 0:
                return enumWithValue.toString();
            case 1:
                field = fields[0];
                break;
            default:
                try {
                    field = type.getField("value");
                } catch (Exception ex) {
                    return enumWithValue.toString();
                }
                break;
        }
        return TryCatchUtil.tryGetResult(() -> field.get(enumWithValue).toString());
    }

    public static <T extends Enum> List<T> getAllEnumValues(T enumValue) {
        return asList((T[]) enumValue.getDeclaringClass().getEnumConstants());
    }
}
