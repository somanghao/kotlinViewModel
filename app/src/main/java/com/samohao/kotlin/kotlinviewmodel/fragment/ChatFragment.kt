package com.samohao.kotlin.kotlinviewmodel.fragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.samohao.kotlin.kotlinviewmodel.R

import com.samohao.kotlin.kotlinviewmodel.activity.HomeActivity
import com.samohao.kotlin.kotlinviewmodel.databinding.ChatFragmentBinding
import com.samohao.kotlin.kotlinviewmodel.viewModel.ChatViewModel
import com.samohao.kotlin.kotlinviewmodel.viewModel.VoiceDataViewModel
import com.samohao.kotlin.kotlinviewmodel.viewModel.VoiceDataViewModelFactory
import org.koin.android.ext.android.inject



class ChatFragment : Fragment() {

    companion object {
        fun newInstance() = ChatFragment()
    }

    private lateinit var activity : Activity
    private lateinit var chatViewModel: ChatViewModel
    private lateinit var chatFragmentBinding: ChatFragmentBinding
    private val voiceDataViewModelFactory : VoiceDataViewModelFactory by inject()
    private lateinit var voiceDataViewModel : VoiceDataViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        voiceDataViewModel = ViewModelProviders.of(this , voiceDataViewModelFactory).get(VoiceDataViewModel::class.java)
        chatViewModel = ViewModelProviders.of(this).get(ChatViewModel::class.java)
        chatFragmentBinding = DataBindingUtil.inflate(inflater , R.layout.chat_fragment , container , false)
        chatFragmentBinding.chatViewModel = chatViewModel
        chatFragmentBinding.voiceDataViewModel = voiceDataViewModel
        chatFragmentBinding.lifecycleOwner = this
        return chatFragmentBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity = getActivity() as HomeActivity
        //chatViewModel.getMyChatRoomInfo("test" , 1111)

        chatViewModel.chatRoomList.observe(this , Observer {
        })

        voiceDataViewModel.clickSpeech.observe(this , Observer {
            inputVoice()
        })


    }

    private fun inputVoice() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            .putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE , activity.packageName)
            .putExtra(RecognizerIntent.EXTRA_LANGUAGE , "ko-KR")

        val context : Context = context!!
        val speechRecognizer  = SpeechRecognizer.createSpeechRecognizer(context)
        speechRecognizer.setRecognitionListener(object : RecognitionListener{
            override fun onReadyForSpeech(p0: Bundle?) {
                voiceDataViewModel.setResult("onReadyForSpeech")
            }

            override fun onRmsChanged(rmsdB: Float) {

                val quiet_max = 25f
                val medium_max = 65f

                if (rmsdB < quiet_max) {
                    voiceDataViewModel.setResult("Quiet$rmsdB")
                    // quiet
                } else if (rmsdB >= quiet_max && rmsdB < medium_max) {
                    voiceDataViewModel.setResult("Medium$rmsdB")
                    // medium
                } else {
                    voiceDataViewModel.setResult("Loud$rmsdB")
                    // loud
                }
            }

            override fun onBufferReceived(p0: ByteArray?) {
                voiceDataViewModel.setResult("onBufferReceived")
            }

            override fun onPartialResults(p0: Bundle?) {
                voiceDataViewModel.setResult("onPartialResults")
            }

            override fun onEvent(p0: Int, p1: Bundle?) {
                voiceDataViewModel.setResult("onEvent")
            }

            override fun onBeginningOfSpeech() {
                voiceDataViewModel.setResult("onBeginningOfSpeech")
            }

            override fun onEndOfSpeech() {
                voiceDataViewModel.setResult("onEndOfSpeech")
            }

            override fun onError(p0: Int) {
                voiceDataViewModel.setResult("천천히 말해")
            }

            override fun onResults(results: Bundle?) {
                val key = SpeechRecognizer.RESULTS_RECOGNITION
                val result = results?.getStringArrayList(key)
                voiceDataViewModel.setResult(result?.get(0) ?: "인식못함")
            }
        })
        speechRecognizer.startListening(intent)
    }
}

