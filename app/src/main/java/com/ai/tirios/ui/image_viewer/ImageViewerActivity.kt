package com.ai.tirios.ui.image_viewer

import android.os.Bundle
import android.view.View
import com.ai.tirios.BR
import com.ai.tirios.R
import com.ai.tirios.base.BaseActivity
import com.ai.tirios.databinding.ActivityImageViewerBinding
import javax.inject.Inject


class ImageViewerActivity : BaseActivity<ActivityImageViewerBinding, ImageViewerViewModel>(),
    ImageViewerMedium{

    var binding: ActivityImageViewerBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = dataBinding
        binding!!.imageViewerModel = viewModel
        if(intent.getStringExtra("image_url")!!.contains("mp4")){
            startVideo()
        }else{
            binding!!.image = intent.getStringExtra("image_url")
            binding!!.video.visibility = View.GONE
            binding!!.imageView.visibility = View.VISIBLE
        }
        viewModel.medium = this

        binding!!.video.setOnPreparedListener {
            dismissProgressbar()
        }
        binding!!.video.setOnCompletionListener {
            binding!!.imgStart.visibility = View.VISIBLE
        }

        binding!!.imgCancel.setOnClickListener(this)
        binding!!.imgStart.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.img_cancel ->{
                finish()
            }
            R.id.img_start ->{
                startVideo()
            }
        }
    }

    override val layoutId: Int
        get() = R.layout.activity_image_viewer
    override lateinit var viewModel: ImageViewerViewModel
        @Inject internal set
    override val bindingVariable: Int
        get() = BR._all

    fun startVideo(){
        binding!!.imgStart.visibility = View.GONE
        binding!!.video.visibility = View.VISIBLE
        binding!!.imageView.visibility = View.GONE
        binding!!.video.setVideoPath(intent.getStringExtra("image_url"))
        binding!!.video.requestFocus();
        showProgressbar()
        binding!!.video.start()
    }
}