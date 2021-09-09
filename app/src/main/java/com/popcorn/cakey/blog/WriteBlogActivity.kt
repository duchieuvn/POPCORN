package com.popcorn.cakey.blog

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.URLUtil
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.textfield.TextInputEditText
import com.parse.ParseFile
import com.parse.ParseObject
import com.parse.ParseUser
import com.popcorn.cakey.R
import com.popcorn.cakey.databinding.ActivityWriteBlogBinding
import com.popcorn.cakey.mainscreen.MainActivity
import org.json.JSONArray
import org.json.JSONObject
import java.io.ByteArrayOutputStream


class WriteBlogActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWriteBlogBinding
    private lateinit var stepImage: ImageView
    private lateinit var currentView: View
    private var blogImgFlag = false
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
                    blogImgFlag = true
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
            del.setOnClickListener {
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
                val author = ParseUser.getCurrentUser()

                val blog = ParseObject("Blog")
                blog.put("author", author)
                blog.put("name", binding.detailTitle.text.toString())
                if (binding.contentDes.text.toString() != "")
                    blog.put("description", binding.contentDes.text.toString())

                blog.put("servings", binding.numSer.text.toString().toInt())


                var ingredients = JSONArray()
                for (i in 0 until binding.detailList.childCount) {
                    val contentIngredient: View = binding.detailList.getChildAt(i)

                    val quantity: TextInputEditText = contentIngredient.findViewById(R.id.quantity)
                    val unit: TextInputEditText = contentIngredient.findViewById(R.id.unit)
                    val nameIng: TextInputEditText =
                        contentIngredient.findViewById((R.id.nameIngredient))

                    //database purpose (Model class)
                    var newIngre = JSONObject()
                    newIngre.put("name", nameIng.text.toString())
                    newIngre.put("amount", quantity.text.toString().toInt())
                    newIngre.put("measurement", unit.text.toString())

                    ingredients.put(newIngre)
                }

                // Link video
                var link = binding.insertLink.text.toString()

                // Save data to database
                var blogContent = ParseObject("BlogContent")
                if (link != "")
                    blogContent.put("videoUrl", link)
                blogContent.put("ingredient", ingredients)
                blogContent.save()

                //Put blog Image here
                val drawable = binding.blogImage.drawable as BitmapDrawable
                val bitmap = drawable.bitmap

                val stream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                val data: ByteArray = stream.toByteArray()
                var filename = "blogCover.jpeg"
                val file = ParseFile(filename, data)
                blog.put("img",file)

                blog.put("blogContent", blogContent)
                blog.save()

                for (i in 0 until binding.detailStep.childCount step 2) {

                    val Step = ParseObject("Step")

                    val contentGuidelines: View = binding.detailStep.getChildAt(i)
                    val step: TextInputEditText = contentGuidelines.findViewById((R.id.Step))

                    //store steps for database purpose
                    Step.put("text", step.text.toString())

                    val contentImage: View = binding.detailStep.getChildAt(i + 1)
                    val image: ImageView = contentImage.findViewById((R.id.stepImage))

                    if (image.drawable != null) {
                        val drawable = image.drawable as BitmapDrawable
                        val bitmap = drawable.bitmap

                        val stream = ByteArrayOutputStream()
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                        val data: ByteArray = stream.toByteArray()
                        var filename = "step$i.jpeg"
                        val file = ParseFile(filename, data)

                        Step.put("img", file)
                    }
                    Step.put("blog",blog)
                    Step.put("position", (i/2+1).toInt())
                    Step.saveInBackground()
                }




                //move to main screens
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }


        //Cancel blog
        binding.buttonCancel.setOnClickListener {
            val builder = AlertDialog.Builder(this)

            builder.setTitle("Cakey Warning!!!")
            builder.setMessage("You do not save this blog.")
            builder.setPositiveButton("GO TO MAIN") { _, _ ->
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            builder.setNegativeButton("BACK TO BLOG") { _, _ -> }
            builder.show()
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
        if (binding.detailTitle.text.toString().isEmpty() || binding.detailTitle.text.toString().isBlank()) {
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
        if (binding.insertLink.text.toString() != "" && !URLUtil.isValidUrl(binding.insertLink.text.toString()))
        {
            binding.insertLink.error = "Invalid link!"
            binding.insertLink.requestFocus()
            return false
        }

        if (!blogImgFlag)
        {
            Toast.makeText(this, "Blog Cover required!", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

}