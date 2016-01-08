package io.askcuix.oasis.core.util;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Chris on 16/1/8.
 */
public class CollectionUtilTest {

    @Test
    public void convertCollectionToString() {
        List<String> list = Lists.newArrayList("aa", "bb");
        String result = CollectionUtil.convertToString(list, ",");
        assertEquals(result, "aa,bb");

        result = CollectionUtil.convertToString(list, "<li>", "</li>");
        assertEquals(result, "<li>aa</li><li>bb</li>");
    }

}
