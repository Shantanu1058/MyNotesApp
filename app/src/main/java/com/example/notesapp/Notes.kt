package com.example.notesapp

class Notes{
    var noteId:Int?=null
    var noteName:String?=null
    var notes:String?=null
    constructor(noteId:Int,noteName:String,notes:String){
        this.noteId=noteId
        this.noteName=noteName
        this.notes=notes
    }
}