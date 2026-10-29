package com.martinmimigames.tinymusicplayer;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.KeyEvent;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static android.content.Intent.EXTRA_KEY_EVENT;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

/**
 * Unit tests for HWListener class
 */
@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.LOLLIPOP)
public class HWListenerTest {

    private HWListener hwListener;
    private Service mockService;
    private Context mockContext;

    @Before
    public void setUp() {
        mockService = mock(Service.class);
        mockContext = mock(Context.class);
        hwListener = new HWListener(mockService);
    }

    /**
     * Test that HWListener can be created without service (for older Android)
     */
    @Test
    public void testDefaultConstructor() {
        HWListener listener = new HWListener();
        // Should not throw exception
    }

    /**
     * Test that HWListener can be created with service
     */
    @Test
    public void testConstructorWithService() {
        HWListener listener = new HWListener(mockService);
        // Should not throw exception
    }

    /**
     * Test onReceive with MEDIA_PLAY key event
     */
    @Test
    public void testOnReceiveMediaPlayKey() {
        when(mockContext.startService(any(Intent.class))).thenReturn(null);

        KeyEvent keyEvent = new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_MEDIA_PLAY);
        Intent intent = new Intent();
        intent.putExtra(EXTRA_KEY_EVENT, keyEvent);

        hwListener.onReceive(mockContext, intent);

        verify(mockContext, times(1)).startService(any(Intent.class));
    }

    /**
     * Test onReceive with MEDIA_PAUSE key event
     */
    @Test
    public void testOnReceiveMediaPauseKey() {
        when(mockContext.startService(any(Intent.class))).thenReturn(null);

        KeyEvent keyEvent = new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_MEDIA_PAUSE);
        Intent intent = new Intent();
        intent.putExtra(EXTRA_KEY_EVENT, keyEvent);

        hwListener.onReceive(mockContext, intent);

        verify(mockContext, times(1)).startService(any(Intent.class));
    }

    /**
     * Test onReceive with MEDIA_PLAY_PAUSE key event
     */
    @Test
    public void testOnReceiveMediaPlayPauseKey() {
        when(mockContext.startService(any(Intent.class))).thenReturn(null);

        KeyEvent keyEvent = new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE);
        Intent intent = new Intent();
        intent.putExtra(EXTRA_KEY_EVENT, keyEvent);

        hwListener.onReceive(mockContext, intent);

        verify(mockContext, times(1)).startService(any(Intent.class));
    }

    /**
     * Test onReceive with MEDIA_STOP key event
     */
    @Test
    public void testOnReceiveMediaStopKey() {
        when(mockContext.startService(any(Intent.class))).thenReturn(null);

        KeyEvent keyEvent = new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_MEDIA_STOP);
        Intent intent = new Intent();
        intent.putExtra(EXTRA_KEY_EVENT, keyEvent);

        hwListener.onReceive(mockContext, intent);

        verify(mockContext, times(1)).startService(any(Intent.class));
    }

    /**
     * Test onReceive with ACTION_UP is ignored
     */
    @Test
    public void testOnReceiveIgnoresActionUp() {
        KeyEvent keyEvent = new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_MEDIA_PLAY);
        Intent intent = new Intent();
        intent.putExtra(EXTRA_KEY_EVENT, keyEvent);

        hwListener.onReceive(mockContext, intent);

        // Should not start service on ACTION_UP
        verify(mockContext, times(0)).startService(any(Intent.class));
    }

    /**
     * Test onReceive with unsupported key code is ignored
     */
    @Test
    public void testOnReceiveIgnoresUnsupportedKeyCode() {
        KeyEvent keyEvent = new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_VOLUME_UP);
        Intent intent = new Intent();
        intent.putExtra(EXTRA_KEY_EVENT, keyEvent);

        hwListener.onReceive(mockContext, intent);

        // Should not start service for unsupported keys
        verify(mockContext, times(0)).startService(any(Intent.class));
    }

    /**
     * Test setState doesn't throw exception when called
     */
    @Test
    public void testSetStateDoesNotThrow() {
        // On pre-Lollipop, setState does nothing
        hwListener.setState(true, false);
        hwListener.setState(false, false);
        hwListener.setState(true, true);
        hwListener.setState(false, true);
        // Should not throw any exceptions
    }
}
