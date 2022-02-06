package com.example.tryamplify

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.amplifyframework.AmplifyException
import com.amplifyframework.core.Amplify
import com.amplifyframework.datastore.AWSDataStorePlugin
import com.amplifyframework.datastore.generated.model.PRIORITY
import com.amplifyframework.datastore.generated.model.Todo
import java.util.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        try {
            //Amplify.addPlugin(AWSApiPlugin()) // UNCOMMENT this line once backend is deployed
            Amplify.addPlugin(AWSDataStorePlugin())
            Amplify.configure(applicationContext)
            android.util.Log.i("Amplify", "Initialized Amplify")
        } catch (e: AmplifyException) {
            android.util.Log.e("Amplify", "Could not initialize Amplify", e)
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val but = findViewById<Button>(R.id.buttonID)
        but.setOnClickListener {
            Toast.makeText( this, "Button Clicked", Toast.LENGTH_SHORT).show()
            buttonFunc()
        }


    }

    private fun buttonFunc() {
        val date = Date()
        val offsetMillis = TimeZone.getDefault().getOffset(date.time).toLong()
        val offsetSeconds = TimeUnit.MILLISECONDS.toSeconds(offsetMillis).toInt()
        val temporalDateTime =
            com.amplifyframework.core.model.temporal.Temporal.DateTime(date, offsetSeconds)
        val item = Todo.builder()
            .name("Button attempt 2")
            .priority(PRIORITY.HIGH)
            .completedOn(temporalDateTime)
            .build()

        Amplify.DataStore.save(item,
            { android.util.Log.i("Tutorial", "Saved item: ${item.name}") },
            { android.util.Log.e("Tutorial", "Could not save item to DataStore", it) }
        )

    }
}