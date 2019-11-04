package com.malawidiyanto.randomgiveaway

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import io.paperdb.Paper
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

class AddActivity : AppCompatActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        val edt_candidat = findViewById<TextInputEditText>(R.id.edt_candidat)
        val btn_save = findViewById<MaterialButton>(R.id.btn_save)

        var userList = Paper.book().read("userList", ArrayList<User>())
        var candidatesBefore:String = ""
        for(i in 0..userList.size-1){
            if(!userList.get(i).name!!.isEmpty()){
                candidatesBefore += userList.get(i).name + System.lineSeparator()
            }
        }
        edt_candidat.setText(candidatesBefore)


        btn_save.setOnClickListener {
            var mUserList = ArrayList<User>()
            var candidate = edt_candidat.text.toString()
            var candidates = candidate.split(System.lineSeparator())
            for(i in 0..candidates.size-1){
//                println("data : {$i} : "+candidates.get(i))
                if(!candidates.get(0).isEmpty()){
                    var u = User()
                    u.name = candidates.get(i)
                    mUserList.add(u)
                }
            }

            Paper.book().write("userList", mUserList)

            Toast.makeText(this, "Berhasil Menyimpan Data", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        intent = Intent(this@AddActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }


    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }
}