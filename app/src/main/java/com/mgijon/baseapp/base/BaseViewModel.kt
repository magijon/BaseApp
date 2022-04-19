package com.mgijon.baseapp.base

import android.os.Bundle
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel(){
    abstract fun startLogic(bundle : Bundle?)
}