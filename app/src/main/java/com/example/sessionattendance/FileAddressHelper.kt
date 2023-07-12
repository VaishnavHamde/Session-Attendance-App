package com.example.sessionattendance

import android.content.Context
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

class FileAddressHelper {

    val FILE_NAME = "list_address_info.dat"

    fun writeData(personInfoList : ArrayList<String>, context : Context){

        var fos : FileOutputStream = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE)

        var oas = ObjectOutputStream(fos)
        oas.writeObject(personInfoList)

        oas.close()
    }

    fun readData(context : Context) : ArrayList<String>{
        var personInfoList : ArrayList<String>

        try{
            var fis : FileInputStream = context.openFileInput(FILE_NAME)
            var ois = ObjectInputStream(fis)

            personInfoList = ois.readObject() as ArrayList<String>
        }
        catch (e : FileNotFoundException){
            personInfoList = ArrayList<String>()
        }

        return personInfoList
    }

}