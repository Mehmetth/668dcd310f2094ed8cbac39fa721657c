package com.mehmetpetek.satellitedemo.ui.detail

import android.os.CountDownTimer
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.mehmetpetek.satellitedemo.app.R
import com.mehmetpetek.satellitedemo.app.databinding.FragmentDetailBinding
import com.mehmetpetek.satellitedemo.common.extensions.showSnackBar
import com.mehmetpetek.satellitedemo.data.local.model.Position
import com.mehmetpetek.satellitedemo.data.local.model.Satellite
import com.mehmetpetek.satellitedemo.data.local.model.SatelliteDetail
import com.mehmetpetek.satellitedemo.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment :
    BaseFragment<FragmentDetailBinding>(FragmentDetailBinding::inflate) {

    private val viewModel by viewModels<DetailViewModel>()
    private var countDownTimer: CountDownTimer? = null

    override fun bindScreen() {
        lifecycleScope.launchWhenResumed {
            viewModel.effect.collect {
                when (it) {
                    is DetailEffect.ShowError -> {
                        requireView().showSnackBar(
                            it.exceptionMessage.toString(),
                            getString(R.string.ok)
                        )
                    }
                    is DetailEffect.Back -> Unit
                }
            }
        }

        lifecycleScope.launchWhenResumed {
            viewModel.state.collect {
                setLoadingState(it.isLoading)

                setSatelliteAttr(it.satellite, it.satelliteDetail)
                setSatellitePosition(it.positionList)
            }
        }
    }

    private fun setSatelliteAttr(satellite: Satellite?, satelliteDetail: SatelliteDetail?) {
        binding.tvSatelliteName.text = satellite?.name
        binding.tvDate.text = satelliteDetail?.first_flight
        binding.tvHeightMassDesc.text = "${satelliteDetail?.height}/${satelliteDetail?.mass}"
        binding.tvCoastDesc.text = "${satelliteDetail?.cost_per_launch}"
    }

    private fun setSatellitePosition(positionList: ArrayList<Position?>?) {
        var counter = 0
        binding.tvLastPositionDesc.text =
            "(${positionList?.get(counter)?.posX},${positionList?.get(counter)?.posY})"

        countDownTimer = object : CountDownTimer(3000, 1000) {
            override fun onTick(millisUntilFinished: Long) {}
            override fun onFinish() {
                if ((positionList?.size?.minus(1)) == counter)
                    counter = 0
                else
                    counter += 1

                binding.tvLastPositionDesc.text =
                    "(${positionList?.get(counter)?.posX},${positionList?.get(counter)?.posY})"
                start()
            }
        }.start()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        countDownTimer?.cancel()
    }
}