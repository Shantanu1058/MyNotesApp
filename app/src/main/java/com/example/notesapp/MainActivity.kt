package com.example.notesapp

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.ActionBar
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

class MainActivity : AppCompatActivity() {
    var listOfNotes=ArrayList<Notes>(0)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadQuery("%")
        val swipeContainer=findViewById<SwipeRefreshLayout>(R.id.swipeContainer)
        swipeContainer.setOnRefreshListener {
            loadQuery("%")
            swipeContainer.isRefreshing = false
        }
        this.supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar?.setDisplayShowCustomEnabled(true);
        supportActionBar?.setCustomView(R.layout.custom_app_bar);
    }

    override fun onResume() {
        loadQuery("%")
        super.onResume()
    }

    override fun onRestart() {
        loadQuery("%")
        super.onRestart()
    }
    inner class NotesAdapter:BaseAdapter{
        var listOfNotes=ArrayList<Notes>(0)
        var context:Context?=null
        constructor(context: Context,listOfNotes:ArrayList<Notes>):super(){
            this.context=context
            this.listOfNotes=listOfNotes

        }
        override fun getCount(): Int {
            return listOfNotes.size
        }

        override fun getItem(position: Int): Any {
            return listOfNotes[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val view=layoutInflater.inflate(R.layout.notes_ticket,null)
            val myNotes=listOfNotes[position]
            val title=view.findViewById<TextView>(R.id.tvTitle)
            val notes=view.findViewById<TextView>(R.id.tvNotes)
            val delete=view.findViewById<ImageView>(R.id.ivDelete)
            val edit=view.findViewById<ImageView>(R.id.ivEdit)
            delete.setOnClickListener {
                val dbManager=DbManager(this.context!!)
                val selectionArgs= arrayOf(myNotes.noteId.toString())
                dbManager.delete("ID=?",selectionArgs)
                val adapter=NotesAdapter(this.context!!,listOfNotes)
                val notesList=findViewById<ListView>(R.id.notesList)
                notesList.adapter=adapter
                adapter.notifyDataSetChanged()
                loadQuery("%")
                Toast.makeText(this.context,"Deleted Successfully",Toast.LENGTH_SHORT).show()
            }
            edit.setOnClickListener {
                GoToUpdate(myNotes)
            }
            title.text=myNotes.noteName
            notes.text=myNotes.notes
            return view
        }

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_item,menu)
        val sv:SearchView = menu?.findItem(R.id.app_bar_search)?.actionView as SearchView

        val sm= getSystemService(Context.SEARCH_SERVICE) as SearchManager
        sv.setSearchableInfo(sm.getSearchableInfo(componentName))
        sv.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                Toast.makeText(applicationContext, query, Toast.LENGTH_LONG).show()
                loadQuery("%$query%")
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId==R.id.ivAddNote){

            val addNoteIntent= Intent(this,AddNotes::class.java)
            startActivity(addNoteIntent)
            overridePendingTransition(R.anim.fade_in,R.anim.fade_out)
        }

        return super.onOptionsItemSelected(item)
    }
    fun loadQuery(title:String){
        val dbManager=DbManager(this)
        val projection= arrayOf("ID","Title","Description")
        val selectionArgs= arrayOf(title)
        val cursor=dbManager.Query(projection,"Title like ?",selectionArgs,"Title")
        listOfNotes.clear()
        if(cursor.moveToFirst()){
            do{
               val id=cursor.getInt(cursor.getColumnIndex("ID"))
                val _title=cursor.getString(cursor.getColumnIndex("Title"))
                val desc=cursor.getString(cursor.getColumnIndex("Description"))
                listOfNotes.add(Notes(id,_title,desc))

            }while (cursor.moveToNext())
            val adapter=NotesAdapter(this,listOfNotes)
            val notesList=findViewById<ListView>(R.id.notesList)

            notesList.adapter=adapter
            adapter.notifyDataSetChanged()
        }

    }
    fun GoToUpdate(note:Notes){
        var intent=  Intent(this,AddNotes::class.java)
        intent.putExtra("ID",note.noteId)
        intent.putExtra("name",note.noteName)
        intent.putExtra("des",note.notes)
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out)
        startActivity(intent)
    }
}