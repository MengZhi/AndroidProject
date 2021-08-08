package com.example.mengzhi.myapplication2

import android.animation.*
import android.app.Activity
import android.content.Intent
import android.graphics.SurfaceTexture
import android.os.Bundle
import android.util.Log
import android.view.Surface
import android.view.TextureView
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import java.io.IOException
import java.lang.IllegalArgumentException
import java.lang.IllegalStateException


class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.hitouch_version)
        initButton()
        addAnimation()
        attachVideo()
    }

    private fun initButton() {
        val button = findViewById<Button>(R.id.main_button)
        button.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            startActivityForResult(intent, 1)
        }
    }

    private fun attachVideo() {
        val textureView = findViewById<TextureView>(R.id.textureView)
        textureView.surfaceTextureListener = getTextureListener()
    }

    private val mMediaPlayer = MediaPlayer()

    private fun getTextureListener() : TextureView.SurfaceTextureListener {
        return object : TextureView.SurfaceTextureListener{

            override fun onSurfaceTextureAvailable(surface: SurfaceTexture?, width: Int, height: Int) {
                var s = Surface(surface)
                try {
                    val mediaUri =
                        Uri.parse("android.resource://com.example.mengzhi.myapplication2/" + R.raw.candela_anim)
                    mMediaPlayer.setDataSource(this@MainActivity, mediaUri)
                    mMediaPlayer.setSurface(s)
                    mMediaPlayer.prepare()
//                    mMediaPlayer.setOnBufferingUpdateListener(this)
//                    mMediaPlayer.setOnCompletionListener(this)
//                    mMediaPlayer.setOnPreparedListener(this)
//                    mMediaPlayer.setOnVideoSizeChangedListener(this)
//                    mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
                    mMediaPlayer.start()
                    mMediaPlayer.isLooping = true
                } catch (e: IllegalArgumentException) {
                    e.printStackTrace()
                } catch (e: SecurityException) {
                    e.printStackTrace()
                } catch (e: IllegalStateException) {
                    e.printStackTrace()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }

            override fun onSurfaceTextureSizeChanged(surface: SurfaceTexture?, width: Int, height: Int) {
                Log.d(TAG, "onSurfaceTextureSizeChanged")
            }

            override fun onSurfaceTextureDestroyed(surface: SurfaceTexture?): Boolean {
                Log.d(TAG, "onSurfaceTextureDestroyed")
                mMediaPlayer.stop()
                return false
            }

            override fun onSurfaceTextureUpdated(surface: SurfaceTexture?) {
                Log.d(TAG, "onSurfaceTextureUpdated")
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.d(TAG, "onActivityResult: requestCode:$requestCode, $resultCode")
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1 && resultCode == 3) {
            val result = data?.getStringExtra("result")
            findViewById<TextView>(R.id.version).text = result
        }

    }

    private fun addAnimation() {
        val animBackground = findViewById<View>(R.id.anim_bg)
        val animEnter = AnimatorInflater.loadAnimator(this, R.animator.anim_enter) as ObjectAnimator
        val animExit = AnimatorInflater.loadAnimator(this, R.animator.anim_exit) as ObjectAnimator

        val enterExitAnimSet = AnimatorSet()
        enterExitAnimSet.play(animExit).after(50000).after(animEnter)
        enterExitAnimSet.setTarget(animBackground)

        val hivisionView = animBackground.findViewById<TextView>(R.id.animTextView1)
        val taobaoView = animBackground.findViewById<TextView>(R.id.animTextView2)

        val animFirstPhase = AnimatorSet()
        animFirstPhase.playTogether(getZoomInAnimator(hivisionView), getHideAnimator(hivisionView), getZoomOutAnimator(taobaoView), getShowAnimator(taobaoView))


        val animSecondPhase = AnimatorSet()
        animSecondPhase.playTogether(getZoomInAnimator(taobaoView), getHideAnimator(taobaoView), getZoomOutAnimator(hivisionView), getShowAnimator(hivisionView))
        animSecondPhase.startDelay = 1000

        val animBubbleFlash = AnimatorSet()
        animBubbleFlash.playTogether(animFirstPhase, animSecondPhase)
        animBubbleFlash.addListener(object: Animator.AnimatorListener{
            override fun onAnimationRepeat(animation: Animator?) {
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationStart(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?) {
                animation?.startDelay = 1000
                animation?.start()
            }
        })

        val animFinal = AnimatorSet()
        animFinal.playTogether(animBubbleFlash, enterExitAnimSet)
        animFinal.start()


        Log.d("zhimeng", "BuildConfig.DEBUG: " + BuildConfig.debug)
    }

    private fun getHideAnimator(view: View): AnimatorSet {
        val animShow = AnimatorInflater.loadAnimator(this, R.animator.anim_hide) as ObjectAnimator
        val animSet = AnimatorSet()
        animSet.play(animShow)
        animSet.setTarget(view)
        return animSet
    }

    private fun getShowAnimator(view: View): AnimatorSet {
        val animShow = AnimatorInflater.loadAnimator(this, R.animator.anim_show) as ObjectAnimator
        val animSet = AnimatorSet()
        animSet.play(animShow)
        animSet.setTarget(view)
        return animSet
    }

    private fun getZoomInAnimator(view: View) : Animator {
        val animScaleXSmall = AnimatorInflater.loadAnimator(this, R.animator.anim_zoom_in_x) as ObjectAnimator
        val animScaleYSmall = AnimatorInflater.loadAnimator(this, R.animator.anim_zoom_in_y) as ObjectAnimator

        val animSet = AnimatorSet()
        animSet.playTogether(animScaleXSmall, animScaleYSmall)
        animSet.setTarget(view)
        return animSet
    }

    private fun getZoomOutAnimator(view: View) : Animator {
        val animScaleXLarge = AnimatorInflater.loadAnimator(this, R.animator.anim_zoom_out_x) as ObjectAnimator
        val animScaleYLarge = AnimatorInflater.loadAnimator(this, R.animator.anim_zoom_out_y) as ObjectAnimator

        val animSet = AnimatorSet()
        animSet.playTogether(animScaleXLarge, animScaleYLarge)
        animSet.setTarget(view)
        return animSet
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}