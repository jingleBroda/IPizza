package com.example.ipizza.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.ipizza.presentation.contract.Navigator
import com.example.ipizza.presentation.fragment.FragmentMainMenu.FragmentMainMenu
import com.example.ipizza.R


class MainActivity : AppCompatActivity(), Navigator {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_IPizza)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)


        if (savedInstanceState == null) {

            val fragment = FragmentMainMenu.newInstance(false)

            replaceMainMenuFragment(fragment, true)

        }


    }

    private fun launchFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .addToBackStack(null)
            .show(fragment)
            .commit()
    }

    private fun replaceMainMenuFragment(fragment: Fragment, tagShowFragment:Boolean) {

        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)

        if(tagShowFragment) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainer, fragment)
                .show(fragment)
                .commit()
        }
        else{
            supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainer, fragment)
                .commit()
        }

    }

    override fun goBack() {
        onBackPressed()
    }

    override fun showNewScreen(f: Fragment) {
        launchFragment(f)
    }


    override fun replaceMainMenu(f: Fragment, tagShowFragment:Boolean) {
        replaceMainMenuFragment(f, tagShowFragment)
    }




}