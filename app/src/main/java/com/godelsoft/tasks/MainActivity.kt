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

        mainCard.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.nextCategoryBtn -> {

            }
            R.id.prevCategoryBtn -> {

            }
            R.id.mainCard -> {
                Toast.makeText(this, "test", Toast.LENGTH_SHORT).show()
            }
        }
    }
}