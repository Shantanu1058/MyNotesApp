package com.example.notesapp

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteQueryBuilder

class DbManager{
    val dbName="MyNotes"
    val dbTable="Notes"
    val colId="ID"
    val colTitle="Title"
    val colDes="Description"
    val dbVersion=1
    val sqlCreateTable=
        "CREATE TABLE IF NOT EXISTS $dbTable ($colId INTEGER PRIMARY KEY , $colTitle TEXT NOT NULL, $colDes TEXT NOT NULL);"
    var sqlDB:SQLiteDatabase?=null
    constructor(context: Context){
        var db=DatabaseHelper(context)
        sqlDB=db.writableDatabase
    }
    inner class DatabaseHelper:SQLiteOpenHelper{
        var context:Context?=null
        constructor(context: Context):super(context,dbTable,null,dbVersion){
            this.context=context
        }
        override fun onCreate(db: SQLiteDatabase?) {
            db!!.execSQL(sqlCreateTable)
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            db!!.execSQL("Drop table IF EXISTS $dbTable")
        }

    }

    fun insert(values: ContentValues): Long {
        return sqlDB!!.insert(dbTable,"", values)
    }
    fun delete(selection: String, selectionArgs: Array<String>): Int {
        return sqlDB!!.delete(dbTable, selection, selectionArgs)
    }
    fun Query(
        projection: Array<String>,
        selection: String,
        selectionArgs: Array<String>,
        sortOrder: String
    ): Cursor {
        val qb = SQLiteQueryBuilder()
        qb.tables = dbTable
        return qb.query(sqlDB, projection, selection, selectionArgs, null, null, sortOrder)
    }
    fun update(values: ContentValues, selection: String, selectionargs: Array<String>): Int {

        return sqlDB!!.update(dbTable, values, selection, selectionargs)
    }
}