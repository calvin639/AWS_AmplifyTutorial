package com.example.tryamplify

import android.os.Bundle
import android.provider.Contacts
import android.text.Editable
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.amplifyframework.AmplifyException
import com.amplifyframework.core.Amplify
import com.amplifyframework.core.model.query.Where
import com.amplifyframework.datastore.AWSDataStorePlugin
import com.amplifyframework.datastore.generated.model.Family
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.IO_PARALLELISM_PROPERTY_NAME
import kotlinx.coroutines.launch
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

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

        val reset = findViewById<Button>(R.id.button0)
        reset.setOnClickListener {
            Toast.makeText( this, "Resetting Data", Toast.LENGTH_SHORT).show()
            reset()
        }

        val post = findViewById<Button>(R.id.button1)
        post.setOnClickListener {
            Toast.makeText( this, "Posting Data", Toast.LENGTH_SHORT).show()
            postFunc()
        }

        val get = findViewById<Button>(R.id.button2)
        get.setOnClickListener {
            CoroutineScope(OS).launch {
                Toast.makeText(this, "Getting Data", Toast.LENGTH_SHORT).show()
                getFunc()
            }
        }

        val del = findViewById<Button>(R.id.button3)
        del.setOnClickListener {
            Toast.makeText( this, "Deleting Data", Toast.LENGTH_SHORT).show()
            delete()
        }
    }

    fun String.toEditable(): Editable =  Editable.Factory.getInstance().newEditable(this)
    fun setName(n: String) {
        val name = findViewById(R.id.tblName1) as EditText
        android.util.Log.i("FINDME", "Do something")
        name.text = "FINDME".toEditable()
    }
    fun setAge(n: String) {
        val age = findViewById(R.id.tblAge1) as EditText
        age.setText("200002")
    }
    fun setAtt(at: String) {
        val att = findViewById(R.id.tblAtt1) as EditText
        att.setText(at)
    }


    private fun postFunc() {
        //Get the variables of the tbl
        android.util.Log.i("Calvin", "Post request")
        var name = findViewById(R.id.tblName1) as EditText
        var age = findViewById(R.id.tblAge1) as EditText
        var att  = findViewById(R.id.tblAtt1) as EditText
        val postName = name.text.toString()
        val postAge = age.text.toString()
        val postAtt = att.text.toString()
        val postAge2 = postAge?.toInt()
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

    private suspend fun getFunc(): Family = suspendCoroutine { cont ->
        //Get the variables of the tbl
        val getName = StringBuilder()
        val getAge = StringBuilder()
        val getAtt = StringBuilder()
        //Get the variables of the tbl
        var name = findViewById(R.id.tblName1) as EditText
        var age = findViewById(R.id.tblAge1) as EditText
        var att = findViewById(R.id.tblAtt1) as EditText
        //Pull data from AWS
        Amplify.DataStore.query(Family::class.java,
            { fams ->
                while (fams.hasNext()) {
                    val fam: Family = fams.next()
                    android.util.Log.i("Tutorial", "==== Family Members ====")
                    android.util.Log.i("Tutorial", "Name: ${fam.name}")
                    getName.append(fam.name)
                    getName.appendLine()
                    android.util.Log.i("Tutorial", "Age: ${fam.age}")
                    getAge.append(fam.age?.toString())
                    getAge.appendLine()
                    android.util.Log.i("Tutorial", "Best Attribute: ${fam.attribute}")
                    getAtt.append(fam.attribute)
                    getAtt.appendLine()
                    cont.resume(fam)
                }
            //setName(getName.toString());setAge(getAge.toString());setAtt(getAtt.toString())
            },
            { android.util.Log.e("Tutorial", "Could not query DataStore", it) }
        )
        val newName = getName.toString()
        android.util.Log.i("Tutorial", "New Name: ${newName}")
        name.text = newName.toEditable()
        val newAge = getAge.toString()
        android.util.Log.i("Tutorial", "New Age: ${newAge}")
        age.text = newAge.toEditable()
        val newAtt = getAtt.toString()
        android.util.Log.i("Tutorial", "New Attribute: ${newAtt}")
        att.text = newAtt.toEditable()
    }

    private fun reset() {
        //Get the variables of the tbl
        var name = findViewById(R.id.tblName1) as EditText
        var age = findViewById(R.id.tblAge1) as EditText
        var att = findViewById(R.id.tblAtt1) as EditText
        var n = StringBuilder()
        n.append("1")
        n.appendLine()
        n.append("2")
        name.setText(n.toString())
        age.setText("NULL")
        att.setText("NULL")
    }

    private fun delete() {
        Amplify.DataStore.query(Family::class.java,
            { matches ->
                if (matches.hasNext()) {
                    val fam = matches.next()
                    var name = findViewById(R.id.tblName1) as EditText
                    var del = name.text.toString()
                    android.util.Log.i("MyAmplifyApp", "Trying to delete a post with name ${del}")
                    Amplify.DataStore.delete(fam, Where.matches(Family.NAME.eq("${del}")).queryPredicate,
                        {  android.util.Log.i("MyAmplifyApp", "Deleted a post with name ${del}") },
                        {  android.util.Log.e("MyAmplifyApp", "Failed to delte name: ${del}", it) }
                    )
                }
            },
            { android.util.Log.e("MyAmplifyApp", "Query failed.", it) }
        )
    }

}