package com.example.baseproject

import androidx.fragment.app.Fragment
import com.example.baseproject.screenone.FragmentOne
import com.example.baseproject.screentwo.FragmentTwo
import ru.terrakok.cicerone.android.support.SupportAppScreen

class FragmentOneScreen : SupportAppScreen() {
    override fun getFragment(): Fragment {
        return FragmentOne.newInstance()
    }
}

class FragmentTwoScreen : SupportAppScreen() {
    override fun getFragment(): Fragment {
        return FragmentTwo.newInstance()
    }
}