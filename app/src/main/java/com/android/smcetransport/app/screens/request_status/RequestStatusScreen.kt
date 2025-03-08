package com.android.smcetransport.app.screens.request_status

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import com.android.smcetransport.app.R
import com.android.smcetransport.app.ui.components.AppToolBar
import com.android.smcetransport.app.ui.theme.theme.theme.mediumFont
import kotlinx.coroutines.launch

@Composable
fun RequestStatusScreen(
    modifier: Modifier = Modifier,
    requestStatusUIState: RequestStatusUIState
) {

    val pagerState = rememberPagerState { 2 }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.white))
    ) {

        AppToolBar(
            modifier = Modifier.fillMaxWidth(),
            onToolBarStartIconClick = {

            }
        )

        TabRow(
            selectedTabIndex = pagerState.currentPage,
            modifier = Modifier.fillMaxWidth()
        ) {
            val isSelected = pagerState.currentPage == requestStatusUIState.selectedPage
            Tab(
                selected = isSelected,
                text = {
                    Text(
                        text = stringResource(R.string.student_text),
                        fontFamily = FontFamily(mediumFont)
                    )
                },
                onClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(0)
                    }
                }
            )
            Tab(
                selected = isSelected,
                text = {
                    Text(
                        text = stringResource(R.string.staff_text),
                        fontFamily = FontFamily(mediumFont)
                    )
                },
                onClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(1)
                    }
                }
            )
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        ) { page ->
            if (page == 0) {
                // Student Request List...
            } else {
                // Staff Request List...
            }
        }

    }
}


@Preview
@Composable
fun PreviewRequestStatusScreen() {
    RequestStatusScreen(
        modifier = Modifier.fillMaxSize(),
        RequestStatusUIState()
    )
}