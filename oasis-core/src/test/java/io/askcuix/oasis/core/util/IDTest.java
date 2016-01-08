package io.askcuix.oasis.core.util;

import org.junit.Test;

/**
 *
 * Created by Chris on 16/1/8.
 */
public class IDTest {

    @Test
    public void generate() {
        System.out.println("uuid: " + IDs.uuid());
        System.out.println("uuid2:" + IDs.uuid2());
        System.out.println("randomLong:  " + IDs.randomLong());
    }
}
