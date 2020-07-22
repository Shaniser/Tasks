package com.godelsoft.tasks

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        DataManager.initialize({Toast.makeText(this, "Loading error", Toast.LENGTH_SHORT).show()}) {
            // TODO refresh UI with content
        }

        mainMessage.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.nextCategoryBtn -> {

            }
            R.id.prevCategoryBtn -> {

            }
            R.id.mainMessage -> {
                Toast.makeText(this, "test", Toast.LENGTH_SHORT).show()
            }
        }
    }
}