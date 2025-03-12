package com.android.smcetransport.app.screens.overall_data.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.android.smcetransport.app.R
import com.android.smcetransport.app.ui.components.AppToolBar
import com.android.smcetransport.app.ui.components.TableItemView
import com.android.smcetransport.app.ui.theme.theme.theme.mediumFont

@Composable
fun OverAllDataTableScreen(
    modifier: Modifier = Modifier,
    overAllDataUIModel : OverAllDataUIModel,
    onBackPressEvent : () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.white))
    ) {

        val toolBarText = "${overAllDataUIModel.departmentText} • ${overAllDataUIModel.yearText}"

        AppToolBar(
            modifier = Modifier.fillMaxWidth(),
            toolBarText = toolBarText,
            onToolBarStartIconClick = {
                onBackPressEvent()
            },
            isShowLoading = overAllDataUIModel.isApiLoading
        )
        if (!overAllDataUIModel.isApiLoading) {
            if (overAllDataUIModel.busRequestModelList.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                ) {
                    item {
                        TableItemView(
                            modifier = Modifier.fillMaxWidth(),
                            firstText = stringResource(R.string.name_text),
                            secondText = stringResource(R.string.start_point_text),
                            thirdText = stringResource(R.string.amount_text)
                        )
                    }
                    item {
                        HorizontalDivider(
                            modifier = Modifier.fillMaxWidth(),
                            color = colorResource(R.color.black)
                        )
                    }
                    overAllDataUIModel.busRequestModelList.forEach {
                        val amountText = it.amount ?: 0.0
                        item {
                            TableItemView(
                                modifier = Modifier.fillMaxWidth(),
                                firstText = it.requesterUserModel?.name ?: "–",
                                secondText = it.pickupPoint?:"–",
                                thirdText = if(amountText > 0.0) {
                                    "$amountText"
                                } else {
                                    "–"
                                }
                            )
                        }
                        item {
                            HorizontalDivider(
                                modifier = Modifier.fillMaxWidth(),
                                color = colorResource(R.color.black)
                            )
                        }
                    }
                    repeat(2) {
                        item {
                            TableItemView(
                                modifier = Modifier.fillMaxWidth(),
                                firstText = "–",
                                secondText = "–",
                                thirdText = "–"
                            )
                        }
                        item {
                            HorizontalDivider(
                                modifier = Modifier.fillMaxWidth(),
                                color = colorResource(R.color.black)
                            )
                        }
                    }
                }
            } else {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(R.string.no_data_found),
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.Center),
                        textAlign = TextAlign.Center,
                        fontFamily = FontFamily(mediumFont),
                        fontSize = 16.sp,
                        color = colorResource(R.color.black)
                    )
                }
            }
        }
    }
}