package com.android.smcetransport.app.screens.bus_managment.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.smcetransport.app.R
import com.android.smcetransport.app.core.dto.BusAndRouteModel
import com.android.smcetransport.app.ui.components.AppToolBar
import com.android.smcetransport.app.ui.components.BusItemView
import com.android.smcetransport.app.ui.theme.theme.theme.mediumFont

@Composable
fun BusListScreen(
    modifier: Modifier = Modifier,
    busListUIState : BusListUIState,
    onBusListActionEvent : (BusListActionEvent) -> Unit
) {

    Column(modifier = modifier.fillMaxSize()) {
        AppToolBar(
            modifier = Modifier.fillMaxWidth(),
            toolBarText = stringResource(R.string.bus_list_text),
            onToolBarStartIconClick = {
                onBusListActionEvent(BusListActionEvent.OnBackPressEvent)
            },
            isShowLoading = false,
            firstRightIcon = painterResource(R.drawable.ic_add),
            onFirstRightIconClick = {
                onBusListActionEvent(BusListActionEvent.OnAddBusEvent)
            }
        )
        if (!busListUIState.isBusListGetApiLoading) {
            if (busListUIState.busList.isNotEmpty()) {
                LazyColumn(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                        .weight(1f)
                        .background(color = colorResource(R.color.white)),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(
                        items = busListUIState.busList,
                        key = {
                            "${it.id}"
                        }
                    ) {
                        BusItemView(
                            busNumber = "${it.busNumber}",
                            busRegisterNumber = "${it.registrationNumber}",
                            busDriverName = "${it.driverName}",
                            routeStartPoint = "${it.startingPoint}",
                            viaRoute = "${it.busRoute}",
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            } else {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(R.string.no_department_added),
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
fun PreviewBusListScreen() {
    BusListScreen(
        modifier = Modifier.fillMaxSize(),
        busListUIState = BusListUIState(
            busList = listOf(
                BusAndRouteModel(
                    id = "1",
                    busNumber = "1",
                    registrationNumber = "TN 10T 9606",
                    startingPoint = "Nagercoil, Kanyakumari",
                    busRoute = "Marthandam, Kanyakumari",
                    driverName = "Suman R"
                ),
                BusAndRouteModel(
                    id = "2",
                    busNumber = "1",
                    registrationNumber = "TN 10T 9606",
                    startingPoint = "Nagercoil, Kanyakumari",
                    busRoute = "Marthandam, Kanyakumari",
                    driverName = "Suman R"
                ),
                BusAndRouteModel(
                    id = "3",
                    busNumber = "1",
                    registrationNumber = "TN 10T 9606",
                    startingPoint = "Nagercoil, Kanyakumari",
                    busRoute = "Marthandam, Kanyakumari",
                    driverName = "Suman R"
                ),
                BusAndRouteModel(
                    id = "4",
                    busNumber = "1",
                    registrationNumber = "TN 10T 9606",
                    startingPoint = "Nagercoil, Kanyakumari",
                    busRoute = "Marthandam, Kanyakumari",
                    driverName = "Suman R"
                )
            )
        ),
        onBusListActionEvent = {

        }
    )
}