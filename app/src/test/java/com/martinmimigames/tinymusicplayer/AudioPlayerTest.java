package com.martinmimigames.tinymusicplayer;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.lang.reflect.Field;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit tests for AudioPlayer class
 */
@RunWith(RobolectricTestRunner.class)
public class AudioPlayerTest {

    private AudioPlayer audioPlayer;
    private MediaPlayer mockMediaPlayer;

    @Before
    public void setUp() throws Exception {
        // Create AudioPlayer with null Uri - will throw exception but object is created
        Service mockService = mock(Service.class);
        try {
            audioPlayer = new AudioPlayer(mockService, null);
        } catch (Exception e) {
            // Expected - we'll inject a mock MediaPlayer
        }
        
        // Use reflection to replace the MediaPlayer with our mock
        mockMediaPlayer = mock(MediaPlayer.class);
        Field mediaPlayerField = AudioPlayer.class.getDeclaredField("mediaPlayer");
        mediaPlayerField.setAccessible(true);
        mediaPlayerField.set(audioPlayer, mockMediaPlayer);
    }

    /**
     * Test that isLooping() returns false on Android SDK < CUPCAKE (SDK 3)
     */
    @Test
    @Config(sdk = Build.VERSION_CODES.BASE)
    public void testIsLoopingReturnsFalseOnOldAndroid() {
        assertFalse("isLooping should return false on SDK < CUPCAKE", audioPlayer.isLooping());
    }

    /**
     * Test that isLooping() delegates to MediaPlayer on Android SDK >= CUPCAKE
     */
    @Test
    @Config(sdk = Build.VERSION_CODES.CUPCAKE)
    public void testIsLoopingDelegatesToMediaPlayerOnModernAndroid() {
        when(mockMediaPlayer.isLooping()).thenReturn(true);
        assertTrue("isLooping should return true when MediaPlayer is looping", audioPlayer.isLooping());

        when(mockMediaPlayer.isLooping()).thenReturn(false);
        assertFalse("isLooping should return false when MediaPlayer is not looping", audioPlayer.isLooping());
    }
}
