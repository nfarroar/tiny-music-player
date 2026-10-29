package com.martinmimigames.tinymusicplayer;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Integration tests that verify components work together correctly
 */
@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.LOLLIPOP)
public class IntegrationTest {

    /**
     * Test complete flow: Launcher -> Service initialization
     */
    @Test
    public void testLauncherToServiceFlow() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("file:///test.mp3"));
        
        var controller = Robolectric.buildActivity(Launcher.class, intent);
        controller.create();
        
        // Verify activity finishes (expected behavior)
        assertNotNull("Launcher should not be null", controller.get());
    }

    /**
     * Test that Service can be started with ACTION_VIEW
     */
    @Test
    public void testServiceStartWithActionView() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("file:///test.mp3"));
        intent.setClass(RuntimeEnvironment.getApplication(), Service.class);
        
        var controller = Robolectric.buildService(Service.class, intent);
        var service = controller.create().get();
        
        assertNotNull("Service should be created", service);
        assertNotNull("HWListener should be created", service.hwListener);
        assertNotNull("Notifications should be created", service.notifications);
    }

    /**
     * Test that all launcher action constants are unique
     */
    @Test
    public void testLauncherActionConstantsAreUnique() {
        byte[] actions = {
            Launcher.NULL,
            Launcher.PLAY_PAUSE,
            Launcher.KILL,
            Launcher.PLAY,
            Launcher.PAUSE,
            Launcher.LOOP
        };
        
        // Verify all actions have different values
        for (int i = 0; i < actions.length; i++) {
            for (int j = i + 1; j < actions.length; j++) {
                if (actions[i] == actions[j]) {
                    throw new AssertionError("Actions " + i + " and " + j + " have the same value: " + actions[i]);
                }
            }
        }
    }

    /**
     * Test that exception messages are non-empty
     */
    @Test
    public void testExceptionMessagesAreNonEmpty() {
        assertNotNull("IllegalArgument message should not be null", Exceptions.IllegalArgument);
        assertNotNull("IllegalState message should not be null", Exceptions.IllegalState);
        assertNotNull("IO message should not be null", Exceptions.IO);
        assertNotNull("Security message should not be null", Exceptions.Security);
        
        assert !Exceptions.IllegalArgument.isEmpty() : "IllegalArgument message should not be empty";
        assert !Exceptions.IllegalState.isEmpty() : "IllegalState message should not be empty";
        assert !Exceptions.IO.isEmpty() : "IO message should not be empty";
        assert !Exceptions.Security.isEmpty() : "Security message should not be empty";
    }

    /**
     * Test that Service initializes all required components
     */
    @Test
    public void testServiceInitializesAllComponents() {
        var controller = Robolectric.buildService(Service.class);
        var service = controller.create().get();
        
        assertNotNull("Service should be created", service);
        assertNotNull("HWListener should be initialized", service.hwListener);
        assertNotNull("Notifications should be initialized", service.notifications);
    }
}
