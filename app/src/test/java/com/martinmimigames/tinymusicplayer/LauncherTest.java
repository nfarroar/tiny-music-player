package com.martinmimigames.tinymusicplayer;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests for Launcher class
 */
@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.LOLLIPOP)
public class LauncherTest {

    private ActivityController<Launcher> controller;
    private Launcher launcher;

    @Before
    public void setUp() {
        controller = Robolectric.buildActivity(Launcher.class);
        launcher = controller.get();
    }

    /**
     * Test that Launcher constants are defined correctly
     */
    @Test
    public void testLauncherConstants() {
        assertEquals("TYPE constant should be 'type'", "type", Launcher.TYPE);
        assertEquals("NULL constant should be 0", 0, Launcher.NULL);
        assertEquals("PLAY_PAUSE constant should be 1", 1, Launcher.PLAY_PAUSE);
        assertEquals("KILL constant should be 2", 2, Launcher.KILL);
        assertEquals("PLAY constant should be 3", 3, Launcher.PLAY);
        assertEquals("PAUSE constant should be 4", 4, Launcher.PAUSE);
        assertEquals("LOOP constant should be 5", 5, Launcher.LOOP);
    }

    /**
     * Test onCreate with ACTION_VIEW intent
     */
    @Test
    public void testOnCreateWithActionView() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("file:///test.mp3"));
        
        controller = Robolectric.buildActivity(Launcher.class, intent);
        controller.create();
        
        // Activity should finish after starting service
        assertTrue("Activity should be finishing", controller.get().isFinishing());
    }

    /**
     * Test onCreate with ACTION_SEND intent
     */
    @Test
    public void testOnCreateWithActionSend() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file:///test.mp3"));
        
        controller = Robolectric.buildActivity(Launcher.class, intent);
        controller.create();
        
        // Activity should finish after starting service
        assertTrue("Activity should be finishing", controller.get().isFinishing());
    }

    /**
     * Test onNewIntent is called correctly
     */
    @Test
    public void testOnNewIntent() {
        controller.create();
        
        Intent newIntent = new Intent(Intent.ACTION_VIEW);
        newIntent.setData(Uri.parse("file:///test2.mp3"));
        
        controller.newIntent(newIntent);
        
        // Activity should finish after handling new intent
        assertTrue("Activity should be finishing", controller.get().isFinishing());
    }
}
