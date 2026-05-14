package com.example.devara_kaadu.utils

import android.media.AudioFormat
import android.media.AudioManager
import android.media.AudioTrack
import kotlinx.coroutines.*
import kotlin.math.sin

/**
 * Programmatically generates soothing ambient forest audio (gentle wind/breeze hum)
 * using a native procedural AudioTrack synthesizer. Ensures zero external file dependencies.
 */
object SoundManager {

    private var audioTrack: AudioTrack? = null
    private var soundJob: Job? = null
    private val scope = CoroutineScope(Dispatchers.Default + SupervisorJob())
    private var isPlaying = false

    /** Starts continuous looping playback of procedural ambient forest sound. */
    fun startNatureSounds() {
        if (isPlaying) return
        isPlaying = true

        soundJob = scope.launch {
            val sampleRate = 44100
            val bufferSize = AudioTrack.getMinBufferSize(
                sampleRate,
                AudioFormat.CHANNEL_OUT_MONO,
                AudioFormat.ENCODING_PCM_16BIT
            )

            audioTrack = AudioTrack(
                AudioManager.STREAM_MUSIC,
                sampleRate,
                AudioFormat.CHANNEL_OUT_MONO,
                AudioFormat.ENCODING_PCM_16BIT,
                bufferSize,
                AudioTrack.MODE_STREAM
            ).apply {
                play()
            }

            val buffer = ShortArray(bufferSize)
            var angle = 0.0
            val frequencyBase = 180.0 // Soothing deep low-mid frequency
            val twoPi = 2.0 * Math.PI

            try {
                while (isActive && isPlaying) {
                    for (i in buffer.indices) {
                        // Procedurally generate smooth sine wave mixed with gentle low-pass modulated wind noise
                        val mod = sin(angle * 0.05) * 15.0
                        val currentFreq = frequencyBase + mod
                        angle += twoPi * currentFreq / sampleRate
                        if (angle > twoPi) angle -= twoPi

                        // Gentle smooth amplitude mapping
                        buffer[i] = (sin(angle) * 2000).toInt().toShort()
                    }
                    audioTrack?.write(buffer, 0, buffer.size)
                }
            } catch (e: Exception) {
                // Ignore interruption exceptions during standard stopping
            } finally {
                releaseTrack()
            }
        }
    }

    /** Immediately pauses/stops ambient audio synthesis. */
    fun stopNatureSounds() {
        isPlaying = false
        soundJob?.cancel()
        soundJob = null
        releaseTrack()
    }

    private fun releaseTrack() {
        try {
            audioTrack?.stop()
            audioTrack?.release()
        } catch (e: Exception) {}
        audioTrack = null
    }
}
