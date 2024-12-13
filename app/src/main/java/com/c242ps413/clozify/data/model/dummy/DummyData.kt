package com.c242ps413.clozify.data.model.dummy

import com.c242ps413.clozify.data.api.response.BottomWearItem
import com.c242ps413.clozify.data.api.response.FootwearItem
import com.c242ps413.clozify.data.api.response.RecommendationItem
import com.c242ps413.clozify.data.api.response.Recommendations
import com.c242ps413.clozify.data.api.response.TopWearItem

object DummyData {

    val recommendations = Recommendations(
        topWear = listOf(
            TopWearItem(
                recommendationsItem = RecommendationItem(
                    image = "android.resource://com.c242ps413.clozify/drawable/baju",
                    name = "Baju"
                ),
                moreRecommendedItems = listOf(
                    RecommendationItem(
                        image = "android.resource://com.c242ps413.clozify/drawable/dummybaju2",
                        name = "Blouse"
                    ),
                    RecommendationItem(
                        image = "android.resource://com.c242ps413.clozify/drawable/dummybaju3",
                        name = "Jacket"
                    ),
                    RecommendationItem(
                            image = "android.resource://com.c242ps413.clozify/drawable/cardigan",
                            name = "Cardigan"
                    )
                )
            ),
            TopWearItem(
                recommendationsItem = RecommendationItem(
                    image = "android.resource://com.c242ps413.clozify/drawable/baju",
                    name = "Baju"
                ),
                moreRecommendedItems = listOf(
                    RecommendationItem(
                        image = "android.resource://com.c242ps413.clozify/drawable/dummybaju",
                        name = "Sweater"
                    ),
                    RecommendationItem(
                        image = "android.resource://com.c242ps413.clozify/drawable/jaket",
                        name = "Jacket"
                    ),
                    RecommendationItem(
                        image = "android.resource://com.c242ps413.clozify/drawable/kaos",
                        name = "T-Shirt"
                    ),
                )
            )
        ),
        bottomwear = listOf(
            BottomWearItem(
                inputItem = RecommendationItem(
                    image = "android.resource://com.c242ps413.clozify/drawable/celana",
                    name = "Celana"
                ),
                recommendedItems = listOf(
                    RecommendationItem(
                        image = "android.resource://com.c242ps413.clozify/drawable/dummycelana2",
                        name = "Skirt"
                    ),
                    RecommendationItem(
                        image = "android.resource://com.c242ps413.clozify/drawable/dummycelana3",
                        name = "Jeans"
                    )
                )
            ),
            BottomWearItem(
                inputItem = RecommendationItem(
                    image = "android.resource://com.c242ps413.clozify/drawable/celana",
                    name = "Celana"
                ),
                recommendedItems = listOf(
                    RecommendationItem(
                        image = "android.resource://com.c242ps413.clozify/drawable/dummycelanaa",
                        name = "Cloth Pants"
                    ),
                    RecommendationItem(
                        image = "android.resource://com.c242ps413.clozify/drawable/rokpolos",
                        name = "Skirt"
                    ),
                    RecommendationItem(
                        image = "android.resource://com.c242ps413.clozify/drawable/cargo",
                        name = "Cargo"
                    ),
                )
            )
        ),
        footwear = listOf(
            FootwearItem(
                recommendationsItem = RecommendationItem(
                    image = "android.resource://com.c242ps413.clozify/drawable/sepatu",
                    name = "Sepatu"
                ),
                moreRecommendedItems = listOf(
                    RecommendationItem(
                        image = "android.resource://com.c242ps413.clozify/drawable/dummysepatuu",
                        name = "Boots"
                    ),
                    RecommendationItem(
                        image = "android.resource://com.c242ps413.clozify/drawable/dummysepatu2",
                        name = "High Heels"
                    )
                )
            ),
            FootwearItem(
                recommendationsItem = RecommendationItem(
                    image = "android.resource://com.c242ps413.clozify/drawable/sepatu",
                    name = "Sepatu"
                ),
                moreRecommendedItems = listOf(
                    RecommendationItem(
                        image = "android.resource://com.c242ps413.clozify/drawable/dummysepatu3",
                        name = "Sneakers"
                    ),
                    RecommendationItem(
                        image = "android.resource://com.c242ps413.clozify/drawable/flatshoes",
                        name = "Flatshoes"
                    ),
                )
            )
        )
    )
}
