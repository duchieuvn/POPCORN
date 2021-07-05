package com.popcorn.cakey

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText

class WriteBlogActivity : AppCompatActivity() {
    private var layoutList: LinearLayout? = null
    private var layoutStep: LinearLayout? = null
    private var ingredientOptions: ImageView? = null
    private var stepOptions: ImageView? = null
    private lateinit var fab:FloatingActionButton
    private lateinit var blogImage:ImageView
    private lateinit var stepImage:ImageView
    private lateinit var currentView:View

    private val startForBlogImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data

            if (resultCode == Activity.RESULT_OK) {
                //Image Uri will not be null for RESULT_OK
                val uri: Uri = data?.data!!

                // Use Uri object instead of File to avoid storage permissions
                blogImage.setImageURI(uri)
            } else if (resultCode == ImagePicker.RESULT_ERROR) {
                Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
            }
        }
    private val startForStepImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data

            if (resultCode == Activity.RESULT_OK) {
                //Image Uri will not be null for RESULT_OK
                val uri: Uri = data?.data!!

                stepImage = currentView.findViewById(R.id.stepImage)
                stepImage.setImageURI(uri)

            } else if (resultCode == ImagePicker.RESULT_ERROR) {
                Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write_blog)

        layoutList = findViewById(R.id.detailList)
        layoutStep = findViewById(R.id.detailStep)

        blogImage = findViewById(R.id.blogImage)
        fab = findViewById(R.id.addImage)
        fab.setOnClickListener {
            ImagePicker.with(this)
                .crop()
                .compress(1024)			//Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                .createIntent { intent ->
                    startForBlogImageResult.launch(intent)
                }
        }
    }


    fun setView(v:View)
    {
        currentView = v
    }
    fun addStepImage(v:View)
    {
        setView(v)
        ImagePicker.with(this)
            .crop()
            .compress(1024)			//Final image size will be less than 1 MB(Optional)
            .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
            .createIntent { intent ->
                startForStepImageResult.launch(intent)
            }


    }

    fun optionsIngredient(v:View)
    {
        ingredientOptions = findViewById(R.id.ingredient_option)

        val popupMenu = PopupMenu(this,ingredientOptions)

        popupMenu.menuInflater.inflate(R.menu.menu_write_blog,popupMenu.menu)

        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.delete -> layoutList!!.removeView(v.parent as View)
                R.id.add -> addIngredient(v)
            }
            true
        }

        //Set force show menu icon
        try {
            val fieldMPopup = PopupMenu::class.java.getDeclaredField("mPopup")
            fieldMPopup.isAccessible = true
            val mPopup = fieldMPopup.get(popupMenu)
            mPopup.javaClass
                .getDeclaredMethod("setForceShowIcon", Boolean::class.java)
                .invoke(mPopup,true)
        } catch (e:Exception) {
            Log.e("WriteBlog Activity", "Error showing menu icon", e)
        } finally {
            popupMenu.show()

        }
    }

    fun optionsStep(v:View)
    {
        stepOptions = findViewById(R.id.step_option)

        val popupMenu = PopupMenu(this,stepOptions)

        popupMenu.menuInflater.inflate(R.menu.menu_write_blog,popupMenu.menu)

        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.delete -> layoutStep!!.removeView(v.parent as View)
                R.id.add -> addStep(v)
            }
            true
        }

        //Set force show menu icon
        try {
            val fieldMPopup = PopupMenu::class.java.getDeclaredField("mPopup")
            fieldMPopup.isAccessible = true
            val mPopup = fieldMPopup.get(popupMenu)
            mPopup.javaClass
                .getDeclaredMethod("setForceShowIcon", Boolean::class.java)
                .invoke(mPopup,true)
        } catch (e:Exception) {
            Log.e("WriteBlog Activity", "Error showing menu icon", e)
        } finally {
            popupMenu.show()

        }
    }


    fun addIngredient(v:View) {
        val view = layoutInflater.inflate(R.layout.add_ingredient, null)
        layoutList!!.addView(view)
    }

    fun addStep(v:View) {
        val view = layoutInflater.inflate(R.layout.add_step, null)
        val image = layoutInflater.inflate(R.layout.add_image, null)
        layoutStep!!.addView(view)
        layoutStep!!.addView(image)

    }


    fun postBlog(v: View) {
        val title: TextInputEditText = findViewById(R.id.detailTitle)


        val descriptionBlog: TextInputEditText = findViewById(R.id.contentDes)


        val serving: TextInputEditText = findViewById(R.id.numSer)


        val cookingTime: TextInputEditText = findViewById(R.id.timeCook)

        Toast.makeText(this, title.text, Toast.LENGTH_SHORT).show()
        Toast.makeText(this, descriptionBlog.text, Toast.LENGTH_SHORT).show()
        Toast.makeText(this, serving.text, Toast.LENGTH_SHORT).show()
        Toast.makeText(this, cookingTime.text, Toast.LENGTH_SHORT).show()

        for (i in 0 until layoutList!!.childCount) {
            val contentIngredient: View = layoutList!!.getChildAt(i)

            val quantity: TextInputEditText = contentIngredient.findViewById(R.id.quantity)
            val nameIng: TextInputEditText = contentIngredient.findViewById((R.id.nameIngredient))

            Toast.makeText(this, quantity.text, Toast.LENGTH_SHORT).show()
            Toast.makeText(this, nameIng.text, Toast.LENGTH_SHORT).show()
        }

        for (i in 0 until layoutStep!!.childCount) {
            val contentGuidelines: View = layoutStep!!.getChildAt(i)

            val step: TextInputEditText = contentGuidelines.findViewById((R.id.Step))

            Toast.makeText(this, step.text, Toast.LENGTH_SHORT).show()
        }


    }
}