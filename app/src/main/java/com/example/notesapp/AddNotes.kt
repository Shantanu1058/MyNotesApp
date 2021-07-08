package com.example.notesapp

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AddNotes: AppCompatActivity() {
    private var id=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_notes)

        val tvName = findViewById<EditText>(R.id.tvName)
        val tvAddNotes = findViewById<EditText>(R.id.tvAddNotes)
        try {
            val bundle: Bundle? = intent.extras
            id = bundle!!.getInt("ID", 0)
            if (id != 0) {
                tvName.setText(bundle.getString("name"))
                tvAddNotes.setText(bundle.getString("des"))
            }
        } catch (ex: Exception) {
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out)


    }
    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out)
    }

    fun save(view: View) {
        val tvName=findViewById<EditText>(R.id.tvName)
        val tvAddNotes=findViewById<EditText>(R.id.tvAddNotes)
        val dbManager=DbManager(this)
        val values=ContentValues()
        values.put("Description",tvAddNotes.text.toString())
        values.put("Title",tvName.text.toString())
        val id=dbManager.insert(values)
        if(id>0){
            val intent= Intent(this,WelcomeActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.fade_in,R.anim.fade_out)

        }
        else{
            Toast.makeText(this,"Unable To Add Notes",Toast.LENGTH_LONG).show()
        }
    }
    /* override fun onCreateOptionsMenu(menu: Menu?): Boolean {
          menuInflater.inflate(R.menu.menu_save,menu)
          return true
      }*/

     /* override fun onOptionsItemSelected(item: MenuItem): Boolean {
          if(item.itemId==R.id.home) {
              var intent=  Intent(this,MainActivity::class.java)
              startActivity(intent)
          }

          return super.onOptionsItemSelected(item)
      }*/
}