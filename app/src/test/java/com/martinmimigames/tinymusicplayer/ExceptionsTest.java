package com.martinmimigames.tinymusicplayer;

import android.content.Context;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;

/**
 * Unit tests for Exceptions class
 */
@RunWith(RobolectricTestRunner.class)
@Config(sdk = 21)
public class ExceptionsTest {

    /**
     * Test that IllegalArgument message is defined correctly
     */
    @Test
    public void testIllegalArgumentMessage() {
        assertEquals("Requires cookies, which the app does not support.", Exceptions.IllegalArgument);
    }

    /**
     * Test that IllegalState message is defined correctly
     */
    @Test
    public void testIllegalStateMessage() {
        assertEquals("Unusable player state, close app and try again.", Exceptions.IllegalState);
    }

    /**
     * Test that IO message is defined correctly
     */
    @Test
    public void testIOMessage() {
        assertEquals("Read error, try again later.", Exceptions.IO);
    }

    /**
     * Test that Security message is defined correctly
     */
    @Test
    public void testSecurityMessage() {
        assertEquals("File location protected, cannot be accessed.", Exceptions.Security);
    }

    /**
     * Test that throwError doesn't throw exception
     */
    @Test
    public void testThrowErrorDoesNotThrowException() {
        Context context = RuntimeEnvironment.getApplication();
        
        // Should not throw exception, just display toast
        Exceptions.throwError(context, Exceptions.IllegalArgument);
        Exceptions.throwError(context, Exceptions.IllegalState);
        Exceptions.throwError(context, Exceptions.IO);
        Exceptions.throwError(context, Exceptions.Security);
        Exceptions.throwError(context, "Custom error message");
    }
}
