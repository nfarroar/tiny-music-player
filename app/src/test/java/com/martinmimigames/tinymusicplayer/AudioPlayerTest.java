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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;

/**
 * Unit tests for AudioPlayer class
 */
@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.LOLLIPOP)
public class AudioPlayerTest {

    private AudioPlayer audioPlayer;
    private MediaPlayer mockMediaPlayer;
    private Service mockService;

    @Before
    public void setUp() throws Exception {
        // Create mock service
        mockService = mock(Service.class);
        
        // Create AudioPlayer with null Uri - will throw exception but object is created
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

    /**
     * Test that isPlaying() delegates to MediaPlayer
     */
    @Test
    public void testIsPlayingDelegatesToMediaPlayer() {
        when(mockMediaPlayer.isPlaying()).thenReturn(true);
        assertTrue("isPlaying should return true when MediaPlayer is playing", audioPlayer.isPlaying());

        when(mockMediaPlayer.isPlaying()).thenReturn(false);
        assertFalse("isPlaying should return false when MediaPlayer is not playing", audioPlayer.isPlaying());
    }

    /**
     * Test setState with playing=true starts MediaPlayer
     */
    @Test
    public void testSetStatePlayingStartsMediaPlayer() {
        audioPlayer.setState(true, false);
        verify(mockMediaPlayer, times(1)).start();
        verify(mockMediaPlayer, times(1)).setLooping(false);
    }

    /**
     * Test setState with playing=false pauses MediaPlayer
     */
    @Test
    public void testSetStatePausedPausesMediaPlayer() {
        audioPlayer.setState(false, false);
        verify(mockMediaPlayer, times(1)).pause();
        verify(mockMediaPlayer, times(1)).setLooping(false);
    }

    /**
     * Test setState with looping=true sets looping on MediaPlayer
     */
    @Test
    public void testSetStateLoopingSetsLooping() {
        audioPlayer.setState(true, true);
        verify(mockMediaPlayer, times(1)).start();
        verify(mockMediaPlayer, times(1)).setLooping(true);
    }

    /**
     * Test setState with playing=false and looping=true
     */
    @Test
    public void testSetStatePausedWithLooping() {
        audioPlayer.setState(false, true);
        verify(mockMediaPlayer, times(1)).pause();
        verify(mockMediaPlayer, times(1)).setLooping(true);
    }

    /**
     * Test onCompletion calls service.stopSelf()
     */
    @Test
    public void testOnCompletionStopsService() {
        audioPlayer.onCompletion(mockMediaPlayer);
        verify(mockService, times(1)).stopSelf();
    }

    /**
     * Test interrupt releases MediaPlayer and interrupts thread
     */
    @Test
    public void testInterruptReleasesMediaPlayer() {
        audioPlayer.interrupt();
        verify(mockMediaPlayer, times(1)).release();
        assertTrue("Thread should be interrupted", audioPlayer.isInterrupted());
    }
}
