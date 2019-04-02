package com.example.alecyericandroid.sent_analysis

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity(),TextToSpeech.OnInitListener {

    private var tts: TextToSpeech? = null
    private var buttonSpeak: Button? = null
    private var editText: EditText? = null
    private var textView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonSpeak = this.button_speak
        editText = this.edittext_input
        textView = this.foodToEat
        buttonSpeak!!.isEnabled = false;
        tts = TextToSpeech(this, this)

        buttonSpeak!!.setOnClickListener { muestraComida() }
    }

    override fun onInit(status: Int) {

        if (status == TextToSpeech.SUCCESS) {
            // set US English as language for tts
            val result = tts!!.setLanguage(Locale.US)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS","The Language specified is not supported!")
            } else {
                buttonSpeak!!.isEnabled = true
            }

        } else {
            Log.e("TTS", "Initilization Failed!")
        }

    }

    private fun speakOut() {
        val text = editText!!.text.toString()
        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null,"")
    }

    private fun muestraComida(){
        val text = editText!!.text.toString()
        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null,"")

        val parts = text.split(" ")
        var finalText= ""
        var contPos=0
        var contNeg=0
        var contNeut=0


        for (word in parts){
            if(cosoDict.get(word) == "positive"){
                contPos+=1
            }else if(cosoDict.get(word) == "negative"){
                contNeg +=1
            }else if(cosoDict.get(word) == "neutral"){
                contNeut +=1
            }
        }

        if(contPos > contNeg){
            finalText = "Deberías comer " +sopasPos.random() +" y " + drinkPos.random() +" y " + comidaPos.random()
        } else if( contNeg > contPos){
            finalText = "Deberías comer " +sopasPos.random() +" y " + drinkPos.random() +" y " + comidaPos.random()
        } else{
            finalText = "Deberías comer " +sopasPos.random() +" y " + drinkPos.random() +" y " + comidaPos.random()
        }

        textView!!.setText(finalText)

    }

    public override fun onDestroy() {
        // Shutdown TTS
        if (tts != null) {
            tts!!.stop()
            tts!!.shutdown()
        }
        super.onDestroy()
    }


    val cosoDict: HashMap<String, String> = hashMapOf("contento" to "positive",
        //Positive
        "contenta " to "positive", "feliz" to "positive", "relajado" to "positive",
        "alegre" to "positive", "calmado" to "positive", "bien" to "positive",
        "animado" to "positive", "animada" to "positive", "sonriente" to "positive",

        //Negative
        "triste" to "negative", "solo" to "negative", "sola" to "negative",
        "deprimido" to "negative", "deprimida" to "negative", "frustrado" to "negative",
        "frustrada" to "negative", "enojado" to "negative", "enojada" to "negative",
        //Neutral
        "ansioso" to "neutral", "curioso" to "neutral", "curiosa" to "neutral",
        "flojera" to "neutral", "sueño" to "neutral", "activo" to "neutral"
        )


    val sopasPos = arrayOf("sopa de tortilla","sopita de hongos")

    val sopasNeg = arrayOf("consomé de pollo","consomé de res")

    val sopasNeut = arrayOf("caldo tlalpeño","sopa azteca")

    val drinkPos = arrayOf("jugo de naranja","coca cola")
    val drinkNeg = arrayOf("jugo verde","agua natural")

    val drinkNeut = arrayOf("té helado","Agua de Jamaica")

    val comidaPos = arrayOf("Taquitos","pizza","helado","sushi","hamburguesas")

    val comidaNeg = arrayOf("alitas","salpicón","tortas","cemitas","chile en nogada")

    val comidaNeut = arrayOf("pollo asado","salmónm","ensalada","sandwich light")





}