package com.stox.common.util;

import java.net.URL;

import com.stox.alert.AlertService;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public interface Audio {

	public static void playAlertAudio() {
		final URL url = AlertService.class.getClassLoader().getResource("dhol_tashe.mp3");
		final Media media = new Media(url.toExternalForm());
		final MediaPlayer mediaPlayer = new MediaPlayer(media);
		mediaPlayer.setAutoPlay(true);
		mediaPlayer.setVolume(1);
		mediaPlayer.play();
	}
	
}
