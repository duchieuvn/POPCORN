package com.popcorn.cakey

import android.widget.Toast
import com.parse.ParseObject
import com.parse.ParseQuery

class UserModel {
    //33. Obj class, 34. Field + var
    fun getLevel(arg1)
    val query = ParseQuery.getQuery<ParseObject>("Level")
    query.whereEqualTo("levelNumber", "1")
    query.findInBackground { List, e ->
        if (e == null) {
        }
        else {
            Toast.makeText(this, "DEAD", Toast.LENGTH_SHORT).show()
        }
    }

}