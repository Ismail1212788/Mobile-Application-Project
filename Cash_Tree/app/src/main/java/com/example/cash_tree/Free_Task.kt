package com.example.cash_tree

import android.opengl.Visibility
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import android.widget.Button
import android.widget.TextView

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.cash_tree.databinding.ActivityFreeTaskBinding
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdView
import kotlinx.coroutines.delay

import java.util.Locale



class Free_Task : AppCompatActivity() {

    private var interstitialAd: InterstitialAd? = null
    private lateinit var nextLevelButton: Button
    private val TAG = "Free_Task"
    private lateinit var binding: ActivityFreeTaskBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title="CASH-TREE---Free Task"
        binding = ActivityFreeTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)
        MobileAds.initialize(
            this
        ) { }
        // Load the InterstitialAd and set the adUnitId (defined in values/strings.xml).
        loadInterstitialAd()
        // Create the next level button, which tries to show an interstitial when clicked.
        nextLevelButton = binding.nextLevelButton
        nextLevelButton.isVisible=false
        nextLevelButton.isEnabled = false
        nextLevelButton.setOnClickListener { showInterstitial() }

        val adview=findViewById<AdView>(R.id.b_ad)
        val adreq:AdRequest=AdRequest.Builder().build()
        adview.loadAd(adreq)

        adview.adListener = object : AdListener(){
            override fun onAdFailedToLoad(p0: LoadAdError) {
                super.onAdFailedToLoad(p0)
                val toastMessage: String = "ad fail to load"
                Toast.makeText(applicationContext, toastMessage.toString(), Toast.LENGTH_LONG).show()
            }
            override fun onAdLoaded() {
                super.onAdLoaded()

            }
            override fun onAdOpened() {
                super.onAdOpened()
                val toastMessage: String = "ad is open"
                Toast.makeText(applicationContext, toastMessage.toString(), Toast.LENGTH_LONG).show()
            }
            override fun onAdClicked() {
                super.onAdClicked()
                val toastMessage: String = "ad is clicked"
                Toast.makeText(applicationContext, toastMessage.toString(), Toast.LENGTH_LONG).show()
            }

            override fun onAdClosed() {
                super.onAdClosed()
                val toastMessage: String = "ad is closed"
                Toast.makeText(applicationContext, toastMessage.toString(), Toast.LENGTH_LONG).show()
            }
            override fun onAdImpression() {
                super.onAdImpression()
                val toastMessage: String = "ad impression"
                Toast.makeText(applicationContext, toastMessage.toString(), Toast.LENGTH_LONG).show()
            }

        }



    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_free__task, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }

    private fun loadInterstitialAd() {
        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(this, getString(R.string.interstitial_ad_unit_id), adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(ad: InterstitialAd) {
                    // The interstitialAd reference will be null until
                    // an ad is loaded.
                    interstitialAd = ad
                    nextLevelButton.isVisible=true
                    nextLevelButton.setEnabled(true)
                    Toast.makeText(this@Free_Task, "onAdLoaded()", Toast.LENGTH_SHORT)
                        .show()
                    ad.setFullScreenContentCallback(
                        object : FullScreenContentCallback() {
                            override fun onAdDismissedFullScreenContent() {
                                interstitialAd = null
                                Log.d(TAG, "The ad was dismissed.")
                                UpdateToken()

                            }

                            override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                                 interstitialAd = null
                                Log.d(TAG, "The ad failed to show.")
                            }

                            override fun onAdShowedFullScreenContent() {
                                Log.d(TAG, "The ad was shown.")
                            }
                        })
                }

                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    // Handle the error
                    Log.i(TAG, loadAdError.message)
                    interstitialAd = null
                    nextLevelButton.setEnabled(true)
                    val error: String = String.format(
                        Locale.ENGLISH,
                        "domain: %s, code: %d, message: %s",
                        loadAdError.domain,
                        loadAdError.code,
                        loadAdError.message
                    )
                    Toast.makeText(
                        this@Free_Task,
                        "onAdFailedToLoad() with error: $error", Toast.LENGTH_SHORT
                    )
                        .show()
                }
            })
    }

    private fun showInterstitial() {
        // Show the ad if it"s ready. Otherwise toast and reload the ad.
        if (interstitialAd != null) {
            interstitialAd!!.show(this)
        } else {
            Toast.makeText(this, "Ad did not load", Toast.LENGTH_SHORT).show()
            if (interstitialAd == null) {
                loadInterstitialAd()
            }
        }
    }
    fun UpdateToken(){
        Toast.makeText(this, "Tokens Updated", Toast.LENGTH_SHORT).show()
        finish()
    }

}