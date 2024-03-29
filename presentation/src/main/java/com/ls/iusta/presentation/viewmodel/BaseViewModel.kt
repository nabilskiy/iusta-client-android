package com.ls.iusta.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ls.iusta.presentation.utils.CoroutineContextProvider
import kotlinx.coroutines.*

abstract class BaseViewModel(
    private val contextProvider: CoroutineContextProvider
) : ViewModel() {

    private val job: Job = Job()

    abstract val coroutineExceptionHandler: CoroutineExceptionHandler

    protected fun launchCoroutineIO(block: suspend CoroutineScope.() -> Unit): Job {
        return viewModelScope.launch(contextProvider.io + job + coroutineExceptionHandler) {
            block()
        }
    }

    protected fun launchCoroutineMain(block: suspend CoroutineScope.() -> Unit): Job {
        return viewModelScope.launch(contextProvider.main + job + coroutineExceptionHandler) {
            block()
        }
    }



    public override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}
