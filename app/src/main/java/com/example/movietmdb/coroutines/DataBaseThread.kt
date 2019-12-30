package com.example.movietmdb.coroutines

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

@Parcelize
class DataBaseThread() : CoroutineScope, Parcelable {

    private val job: Job = Job()


    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    fun stopJob() {
        job.cancel()
    }


}