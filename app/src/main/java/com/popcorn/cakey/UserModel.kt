package com.popcorn.cakey

import android.widget.Toast
import com.parse.ParseObject
import com.parse.ParseQuery

class UserModel {
    //33. Obj class, 34. Field + var
    fun getLevel(levelNumber : Int) : ParseObject {
        val query = ParseQuery.getQuery<ParseObject>("Level")
        query.whereEqualTo("levelNumber", levelNumber)

        return query.first
    }
    

}