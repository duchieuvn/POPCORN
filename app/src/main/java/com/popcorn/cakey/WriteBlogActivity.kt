package com.popcorn.cakey

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.textfield.TextInputEditText
import com.popcorn.cakey.databinding.ActivityWriteBlogBinding
import com.popcorn.cakey.mainscreen.MainActivity
import com.popcorn.cakey.profile.Achievement

class WriteBlogActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWriteBlogBinding
    private var ingredientOptions: ImageView? = null
    private var stepOptions: ImageView? = null
    private lateinit var stepImage: ImageView
    private lateinit var currentView: View

    private val startForBlogImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data

            when (resultCode) {
                Activity.RESULT_OK -> {
                    //Image Uri will not be null for RESULT_OK
                    val uri: Uri = data?.data!!

                    // Use Uri object instead of File to avoid storage permissions
                    binding.blogImage.setImageURI(uri)
                }
                ImagePicker.RESULT_ERROR -> {
                    Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
                }
            }
        }
    private val startForStepImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data

            when (resultCode) {
                Activity.RESULT_OK -> {
                    //Image Uri will not be null for RESULT_OK
                    val uri: Uri = data?.data!!
                    stepImage = currentView.findViewById(R.id.stepImage)
                    stepImage.setImageURI(uri)

                }
                ImagePicker.RESULT_ERROR -> {
                    Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWriteBlogBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        //Add blog image
        binding.fabAddImage.setOnClickListener {
            ImagePicker.with(this)
                .crop()
                .compress(1024)            //Final image size will be less than 1 MB(Optional)
                .maxResultSize(
                    1080,
                    1080
                )    //Final image resolution will be less than 1080 x 1080(Optional)
                .createIntent { intent ->
                    startForBlogImageResult.launch(intent)
                }
        }

        //Add ingredients list
        binding.buttonAddIng.setOnClickListener {
            val v = layoutInflater.inflate(R.layout.add_ingredient, null)
            binding.detailList.addView(v)

            val optionIngredient = v.findViewById<ImageView>(R.id.ingredientOption)
            optionIngredient.setOnClickListener {
                val popupMenu = PopupMenu(this, optionIngredient)

                popupMenu.menuInflater.inflate(R.menu.menu_write_blog, popupMenu.menu)

                popupMenu.setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.delete -> binding.detailList.removeView(v)
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
                        .invoke(mPopup, true)
                } catch (e: Exception) {
                    Log.e("WriteBlog Activity", "Error showing menu icon", e)
                } finally {
                    popupMenu.show()

                }
            }
        }


        //Add guidelines
        binding.buttonAddSteps.setOnClickListener {
            val v = layoutInflater.inflate(R.layout.add_step, null)
            val image = layoutInflater.inflate(R.layout.add_image, null)

            binding.detailStep.addView(v, binding.detailStep.childCount)

            binding.detailStep.addView(image, binding.detailStep.childCount)

            val del = v.findViewById<ImageView>(R.id.step_option)
            del.setOnClickListener{
                val popupMenu = PopupMenu(this, del)

                popupMenu.menuInflater.inflate(R.menu.menu_write_blog, popupMenu.menu)

                popupMenu.setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.delete -> {
                            binding.detailStep.removeView(v)
                            binding.detailStep.removeView(image)
                        }
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
                        .invoke(mPopup, true)
                } catch (e: Exception) {
                    Log.e("WriteBlog Activity", "Error showing menu icon", e)
                } finally {
                    popupMenu.show()

                }
            }

        }

        //Post blogs
        binding.buttonPost.setOnClickListener {
            if (validateBlog()) {

                Toast.makeText(this, binding.detailTitle.text, Toast.LENGTH_SHORT).show()
                Toast.makeText(this, binding.contentDes.text, Toast.LENGTH_SHORT).show()
                Toast.makeText(this, binding.numSer.text, Toast.LENGTH_SHORT).show()

                for (i in 0 until binding.detailList.childCount) {
                    val contentIngredient: View = binding.detailList.getChildAt(i)

                    val quantity: TextInputEditText = contentIngredient.findViewById(R.id.quantity)
                    val unit: TextInputEditText = contentIngredient.findViewById(R.id.unit)
                    val nameIng: TextInputEditText =
                        contentIngredient.findViewById((R.id.nameIngredient))

                    Toast.makeText(this, quantity.text, Toast.LENGTH_SHORT).show()
                    Toast.makeText(this, unit.text, Toast.LENGTH_SHORT).show()
                    Toast.makeText(this, nameIng.text, Toast.LENGTH_SHORT).show()
                }

                for (i in 0 until binding.detailStep.childCount) {
                    if (i % 2 == 0) {
                        val contentGuidelines: View = binding.detailStep.getChildAt(i)

                        val step: TextInputEditText = contentGuidelines.findViewById((R.id.Step))

                        Toast.makeText(this, step.text, Toast.LENGTH_SHORT).show()
                    }
                }

                //move to main screens
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }


    private fun setView(v: View) {
        currentView = v
    }

    fun addStepImage(v: View) {
        setView(v)
        ImagePicker.with(this)
            .crop()
            .compress(1024)            //Final image size will be less than 1 MB(Optional)
            .maxResultSize(
                1080,
                1080
            )    //Final image resolution will be less than 1080 x 1080(Optional)
            .createIntent { intent ->
                startForStepImageResult.launch(intent)
            }
    }



    private fun validateBlog(): Boolean {
        if (binding.detailTitle.text.toString().isEmpty()) {
            binding.detailTitle.error = "Title required!"
            binding.detailTitle.requestFocus()
            return false
        }

        if (binding.numSer.text.toString().isEmpty()) {
            binding.numSer.error = "Servings required!"
            binding.numSer.requestFocus()
            return false
        }

        if (binding.detailList.childCount <= 0) {
            Toast.makeText(this, "Ingredients list required!", Toast.LENGTH_SHORT).show()
            return false
        } else {
            for (i in 0 until binding.detailList.childCount) {
                val contentIngredient: View = binding.detailList.getChildAt(i)

                val quantity: TextInputEditText = contentIngredient.findViewById(R.id.quantity)
                val unit: TextInputEditText = contentIngredient.findViewById(R.id.unit)
                val nameIng: TextInputEditText =
                    contentIngredient.findViewById((R.id.nameIngredient))

                if (quantity.text.toString().isEmpty()) {
                    quantity.error = "Quantity required!"
                    quantity.requestFocus()
                    return false
                }
                if (unit.text.toString().isEmpty()) {
                    unit.error = "Quantity required!"
                    unit.requestFocus()
                    return false
                }
                if (nameIng.toString().isEmpty()) {
                    nameIng.error = "Name ingredient required!"
                    nameIng.requestFocus()
                    return false
                }

            }
        }

        if (binding.detailStep.childCount <= 0) {
            Toast.makeText(this, "Guidelines required!", Toast.LENGTH_SHORT).show()
            return false
        } else {
            for (i in 0 until binding.detailStep.childCount) {
                if (i % 2 == 0) {
                    val contentGuidelines: View = binding.detailStep.getChildAt(i)

                    val step: TextInputEditText = contentGuidelines.findViewById((R.id.Step))

                    if (step.text.toString().isEmpty()) {
                        step.error = "Step required!"
                        step.requestFocus()
                        return false
                    }
                }
            }
        }

        return true
    }


}