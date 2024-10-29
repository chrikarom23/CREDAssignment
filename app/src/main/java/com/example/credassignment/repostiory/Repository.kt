package com.example.credassignment.repostiory

import androidx.lifecycle.MutableLiveData
import com.example.credassignment.network.Body
import com.example.credassignment.network.BodyX
import com.example.credassignment.network.Card
import com.example.credassignment.network.ClosedState
import com.example.credassignment.network.Item
import com.example.credassignment.network.ItemX
import com.example.credassignment.network.MockResponse
import com.example.credassignment.network.NetworkService
import com.example.credassignment.network.OpenState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repository() {


    // The API has too many values with same key. The subtitle which has both String and Long datatypes is where I gave up hard coding
//    val list = listOf(
//        Item(
//            closed_state = ClosedState(
//                Body(
//                key1 = "credit amount",
//                key2 = ""
//            )),
//            open_state = OpenState(
//                BodyX(
//                    title = "nikunj how much do you need?",
//                    subtitle = "move the dial and set any amount you need upto ₹487891",
//                    card = Card(
//                        header = "credit amount",
//                        description = "@1.04% monthly",
//                        max_range = 487891,
//                        min_range = 500
//                    ),
//                    footer = "stash is instant. money will be credited within seconds",
//                    items = emptyList()
//                )
//            ),
//            cta_text = "Proceed to EMI selection"
//        ),
//        Item(
//            closed_state = ClosedState(
//                Body(
//                    key1=  "emi",
//                    key2= "duration"
//                )),
//            open_state = OpenState(
//                BodyX(
//                    title = "how do you wish to repay?",
//                    subtitle = "choose one of our recommended plans or make your own ",
//                    card = Card(),
//                    footer = "create your own plan",
//                    items = listOf( ItemX(
//                        emi = "₹4,247 /mo",
//                        duration = "12 months",
//                        title = "₹4,247 /mo for 12 months",
//                        subtitle= "See calculations",
//
//                        )
//                    )
//                )
//            ),
//            cta_text = "Proceed to EMI selection"
//        ),
//        Item(
//            closed_state = ClosedState(
//                Body(
//                    key1 = "credit amount",
//                    key2 = ""
//                )),
//            open_state = OpenState(
//                BodyX(
//                    title = "nikunj how much do you need?",
//                    subtitle = "move the dial and set any amount you need upto ₹487891",
//                    card = Card(
//                        header = "credit amount",
//                        description = "@1.04% monthly",
//                        max_range = 487891,
//                        min_range = 500
//                    ),
//                    footer = "stash is instant. money will be credited within seconds",
//                    items = emptyList()
//                )
//            ),
//            cta_text = "Proceed to EMI selection"
//        )
//
//    )
//
//    var data =  MockResponse(
//
//    )


//    //I didnt have enough time to deserialize the data as some fields have the same key
//    suspend fun getData(): Boolean{
//        return withContext(Dispatchers.IO){
//            try{
//                data.value   = NetworkService.retrofitInstance.getMockData().await()
//                true
//            }
//            catch (e: Exception){
//                e.printStackTrace()
//                false
//            }
//        }
//    }
}