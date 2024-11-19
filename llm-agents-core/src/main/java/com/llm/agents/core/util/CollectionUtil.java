
package com.llm.agents.core.util;

import java.util.Collection;

public class CollectionUtil {

    private CollectionUtil() {
    }


    public static <T> boolean noItems(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }


    public static boolean hasItems(Collection<?> collection) {
        return !noItems(collection);
    }
}
