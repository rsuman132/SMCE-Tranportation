package com.android.smcetransport.app.screens.view_pass.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.smcetransport.app.R
import com.android.smcetransport.app.ui.components.AppToolBar
import com.android.smcetransport.app.ui.components.ImageUploadView
import com.android.smcetransport.app.ui.components.TitleDescCardItem
import com.android.smcetransport.app.ui.components.model.TitleDescModel
import com.android.smcetransport.app.ui.theme.theme.theme.mediumFont

@Composable
fun ViewPassScreen(
    modifier: Modifier = Modifier,
    viewPassUiState : ViewPassUiState,
    onBackPressEvent : () -> Unit
) {

    Column(modifier = modifier.fillMaxSize()
        .background(color = colorResource(R.color.white))) {
        AppToolBar(
            modifier = Modifier.fillMaxWidth(),
            toolBarText = stringResource(R.string.your_virtual_pass),
            onToolBarStartIconClick = {
                onBackPressEvent()
            },
            isShowLoading = viewPassUiState.isApiLoading
        )
        if (!viewPassUiState.isApiLoading) {
            if (viewPassUiState.titleDescList.isNotEmpty()) {
                Column(
                    modifier = Modifier.fillMaxSize()
                        .weight(1f)
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    ImageUploadView(
                        modifier = Modifier.padding(bottom = 10.dp)
                            .size(70.dp),
                        profileImage = viewPassUiState.userImage,
                        bgColor = colorResource(R.color.app_main_color),
                    )

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier.fillMaxSize().weight(1f),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        userScrollEnabled = false
                    ) {
                        items(viewPassUiState.titleDescList, key = {
                            it.id
                        }) {
                            TitleDescCardItem(
                                modifier = Modifier.fillMaxWidth(),
                                titleText = it.title,
                                descText = it.descText
                            )
                        }
                    }

                }
            } else {
                Box(
                    modifier = Modifier.fillMaxSize().weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    val emptyStateText = viewPassUiState.errorText?: stringResource(R.string.no_a_pass_desc)
                    Text(
                        text = emptyStateText,
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

@Preview
@Composable
fun PreviewViewPassScreen() {
    ViewPassScreen(
        modifier = Modifier.fillMaxSize(),
        viewPassUiState = ViewPassUiState(
            isApiLoading = false,
            titleDescList = listOf(
                TitleDescModel(
                    id = "1",
                    title = "Title 1",
                    descText = "Desc 1"
                ),
                TitleDescModel(
                    id = "2",
                    title = "Title 1",
                    descText = "Desc 1"
                ),
                TitleDescModel(
                    id = "3",
                    title = "Title 1",
                    descText = "Desc 1 Desc 1 Desc 1 Desc 1"
                ),
                TitleDescModel(
                    id = "4",
                    title = "Title 1",
                    descText = "Desc 1"
                ),
                TitleDescModel(
                    id = "5",
                    title = "Title 1",
                    descText = "Desc 1"
                ),
                TitleDescModel(
                    id = "5",
                    title = "Title 1",
                    descText = "Desc 1"
                )
            ),
            errorText = null
        ),
        onBackPressEvent = {

        }
    )
}