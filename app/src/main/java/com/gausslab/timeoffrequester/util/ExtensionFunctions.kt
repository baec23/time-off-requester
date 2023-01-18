package com.gausslab.timeoffrequester.util

import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

fun Query.snapshotFlow(): Flow<QuerySnapshot> = callbackFlow {
    val listenerRegistration = addSnapshotListener { value, error ->
        if (error != null) {
            close()
            return@addSnapshotListener
        }
        if (value != null)
            trySend(value)
    }
    awaitClose {
        listenerRegistration.remove()
    }
}


fun String?.isJsonObject():Boolean{
    return this?.startsWith("{") == true && this.endsWith("}")
}

fun String?.isJsonArray(): Boolean{
    return this?.startsWith("[") == true && this.endsWith("]")
}