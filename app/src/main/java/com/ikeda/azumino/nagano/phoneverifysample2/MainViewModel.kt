package com.ikeda.azumino.nagano.phoneverifysample2

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthProvider

class MainViewModel(context: Context): ViewModel() {

    internal class Factory(
        private val context: Context
    ) : ViewModelProvider.NewInstanceFactory() {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MainViewModel(context) as T
        }
    }

    val isSuccessful = MutableLiveData<Boolean>()

//    fun verifyCode(
//        authVerificationId: String,
//        code: String,
//        terminalId: String,
//        phoneNumber: String,
//        password: String
//    ) {
////        progress.postValue(true)
//        val credential = PhoneAuthProvider.getCredential(authVerificationId, code)
//        FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener { task ->
//            isSuccessful.postValue(task.isSuccessful)
//        }
//    }
}