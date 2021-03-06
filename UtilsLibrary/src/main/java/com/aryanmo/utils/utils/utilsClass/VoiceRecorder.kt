package com.aryanmo.utils.utils.utilsClass

import android.annotation.SuppressLint
import android.content.Context
import android.media.MediaPlayer
import android.media.MediaRecorder
import com.aryanmo.utils.utils.checkDir
import com.aryanmo.utils.utils.log.logError
import java.io.File


class VoiceRecorder(private val context: Context) {

    companion object {
        fun getDefaultVoiceDir(context: Context): String {
            return "${context.filesDir}/voice"
        }

        fun getVoiceList(context: Context): Array<out File> {
            return getVoiceList(getDefaultVoiceDir(context))
        }

        fun getVoiceList(path: String): Array<out File> {
            if (checkDir(path)) {
                return File(path).listFiles()
            }
            return arrayOf()
        }


        fun removeVoiceList(context: Context): Boolean {
            return removeVoiceList(getDefaultVoiceDir(context))
        }

        fun removeVoiceList(path: String): Boolean {
            return File(path).delete()
        }
    }

    private lateinit var recorder: MediaRecorder
    private var outPutPathDir = getDefaultVoiceDir(context)
    private lateinit var outPutFileName: String
    private var audioSource: Int = 0
    private var outputFormat: Int = 0
    private var audioEncoder: Int = 0
    private var initialize = false


    init {
        initialize()
    }

    @SuppressLint("SdCardPath")
    private fun initialize() {
        recorder = MediaRecorder()
        setAudioSource(MediaRecorder.AudioSource.DEFAULT)
        setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
        setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB)
        setOutputFileDir(getDefaultVoiceDir(context), "voice-${Date().toUTC().timestamp}")
        initialize = false
    }

    fun isInitialize(): Boolean {
        return initialize
    }

    fun setAudioSource(audioSource: Int) {
        this.audioSource = audioSource
        recorder.setAudioSource(audioSource)
    }

    fun setOutputFormat(outputFormat: Int) {
        this.outputFormat = outputFormat
        recorder.setOutputFormat(outputFormat)
    }

    fun setAudioEncoder(audioEncoder: Int) {
        this.audioEncoder = audioSource
        recorder.setAudioEncoder(audioEncoder)
    }

    fun setOutputFileDir(
        outPutPathDir: String = this.outPutPathDir,
        outPutFileName: String = "voice-${Date().toUTC().timestamp}"
    ): Boolean {
        if (checkDir(outPutPathDir)) {
            this.outPutPathDir = outPutPathDir
            this.outPutFileName = outPutFileName
            recorder.setOutputFile(getOutputFileDir())
            return true
        }
        return false
    }

    fun getOutputFileDir() = "${getOutputPathDir()}/${getOutputFileName()}.3gp"
    fun getOutputPathDir() = outPutPathDir
    fun getOutputFileName() = outPutFileName

    fun startRecorder(): Boolean {
        return try {
            initialize = true
            recorder.prepare()
            recorder.start()
            true
        } catch (e: Exception) {
            logError("startRecorder", e)
            false
        }
    }

    fun stopRecorder(): Boolean {
        return try {
            recorder.stop()
            recorder.release()
            true
        } catch (e: Exception) {
            false
        }
    }

    fun getRecord(outPutFileDir: String): MediaPlayer? {
        return return try {
            val mediaPlayer = MediaPlayer()
            mediaPlayer.setDataSource(outPutFileDir)
            mediaPlayer.prepare()
            mediaPlayer
        } catch (e: Exception) {
            null
        }
    }

    fun getRecord(): MediaPlayer? {
        return getRecord(getOutputFileDir())
    }

    fun playRecorder(): Boolean {
        try {
            getRecord()?.let {
                it.start()
                return true
            }
        } catch (e: Exception) {
        }
        return false
    }

    fun resetRecorder(): Boolean {
        try {
            initialize()
            return true
        } catch (e: Exception) {
            logError("resetRecorder", e)
        }
        return false
    }

    fun getVoiceList(): Array<out File> {
        return getVoiceList(outPutPathDir)
    }

    fun removeVoiceList(): Boolean {
        return removeVoiceList(outPutPathDir)
    }

}