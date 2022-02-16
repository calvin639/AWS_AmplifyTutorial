package com.example.tryamplify

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.amplifyframework.AmplifyException
import com.amplifyframework.core.Amplify
import com.amplifyframework.datastore.AWSDataStorePlugin
import com.amplifyframework.datastore.generated.model.Family
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

        val post = findViewById<Button>(R.id.button1)
        post.setOnClickListener {
            Toast.makeText( this, "Posting Data", Toast.LENGTH_SHORT).show()
            postFunc()
        }

        val get = findViewById<Button>(R.id.button2)
        get.setOnClickListener {
            Toast.makeText( this, "Getting Data", Toast.LENGTH_SHORT).show()
            getFunc()
        }
    }

    private fun postFunc() {
        //For previous TodoList data model
        val date = Date()
        val offsetMillis = TimeZone.getDefault().getOffset(date.time).toLong()
        val offsetSeconds = TimeUnit.MILLISECONDS.toSeconds(offsetMillis).toInt()
        val temporalDateTime = com.amplifyframework.core.model.temporal.Temporal.DateTime(date, offsetSeconds)
        //Get the variables of the tbl
        android.util.Log.i("Calvin", "Post request")
        var name = findViewById(R.id.tblName1) as EditText
        var age = findViewById(R.id.tblAge1) as EditText
        var att  = findViewById(R.id.tblAtt1) as EditText
        val postName = name.text.toString()
        val postAge = age.text.toString()
        val postAtt = att.text.toString()
        android.util.Log.i("Calvin", "Converting age ${postAge}.")
        val postAge2 = postAge?.toInt()
        android.util.Log.i("Calvin", "Build start ${postAge2}.")
        val item = Family.builder()
            .name(postName)
            .age(postAge2)
            .attribute(postAtt)
            .build()
        android.util.Log.i("Calvin", "Build success")
        //Post data to AWS
        Amplify.DataStore.save(item,
            { android.util.Log.i("Tutorial", "Item Saved. name:${item.name} , age:${item.age}, attribute:${item.attribute}") },
            { android.util.Log.e("Tutorial", "Could not save item to DataStore", it) }
        )

    }

    private fun getFunc() {
        //Get the variables of the tbl
        var name = findViewById(R.id.tblName1) as EditText
        var age = findViewById(R.id.tblAge1) as EditText
        var att  = findViewById(R.id.tblAtt1) as EditText
        val getName = StringBuilder()
        val getAge = StringBuilder()
        val getAtt = StringBuilder()
        //Pull data from AWS
        Amplify.DataStore.query(Family::class.java,
            { fams ->
                while (fams.hasNext()) {
                    val fam: Family = fams.next()
                    android.util.Log.i("Tutorial", "==== Todo ====")
                    android.util.Log.i("Tutorial", "Name: ${fam.name}")
                    getName.append(fam.name)
                    getName.appendLine()
                    android.util.Log.i("Tutorial", "Age: ${fam.age}")
                    getAge.append(fam.age?.toString())
                    getAge.appendLine()
                    android.util.Log.i("Tutorial", "Best Attribute: ${fam.attribute}")
                    getAtt.append(fam.attribute)
                    getAtt.appendLine()
                }
                android.util.Log.i("Calvin", "getName: ${getName}")
                name.setText(getName)
                android.util.Log.i("Calvin", "getAge: ${getAge}")
                age.setText(getAge)
                android.util.Log.i("Calvin", "getAtt: ${getAtt}")
                att.setText(getAtt)
            },
            { android.util.Log.e("Tutorial", "Could not query DataStore", it) }
        )
        //Set the variables of the tbl to your new values
        //android.util.Log.i("Calvin2", "getName is: ${getName}")
    }

}