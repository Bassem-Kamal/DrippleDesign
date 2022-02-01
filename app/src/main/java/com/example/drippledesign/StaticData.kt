package com.example.drippledesign

object ConstantData {
    val naturalFoodList: List<NaturalFood> = listOf(
        NaturalFood(
            1,
            "https://image.shutterstock.com/image-photo/whole-wheat-bread-bakery-isolate-600w-371617006.jpg"
        ),
        NaturalFood(
            2,
            "https://image.shutterstock.com/image-photo/pasta-assortment-italian-dishes-including-600w-1721872819.jpg"
        ), NaturalFood
            (3, "https://image.shutterstock.com/image-photo/plate-mexican-salad-copy-space-600w-762175516.jpg")
    )
    val savedItemsList: List<SavedItem> = listOf(
        SavedItem(1,"https://image.shutterstock.com/image-photo/cute-flower-pancakes-banana-kids-600w-552167443.jpg","Avodamo","$40.00",4.0),
        SavedItem(2,"https://image.shutterstock.com/image-photo/brussels-sprouts-pistachios-raisins-skordalia-600w-2090094067.jpg","Brussels","$40.00",3.0),
        SavedItem(3,"https://image.shutterstock.com/image-photo/truffle-mushroom-cream-soup-cube-600w-1577688802.jpg","Truffles","$40.00",3.0,1),
        SavedItem(4,"https://image.shutterstock.com/image-photo/selective-focus-piece-apple-pie-600w-1787711810.jpg","Whipped","$40.00",2.0),
    )
    val mealList: List<Meal> = listOf(
        Meal(
            1,
            "Ice cream",
            "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fcdn2.iconfinder.com%2Fdata%2Ficons%2Fsummer-travel-7%2F512%2FLine_Icon_23-512.png&f=1&nofb=1"
        ),
        Meal(
            2,
            "Coffee",
            "https://pngset.com/images/breakfast-coffee-cup-isolated-line-mug-outline-icon-text-plot-silhouette-drawing-transparent-png-1702511.png"
        ),
        Meal(
            3,
            "Breakfast",
            "https://cdn4.iconfinder.com/data/icons/food-and-equipment-outline/32/dish-512.png"
        ),
        Meal(
            4,
            "Spoon",
            "https://cdn2.iconfinder.com/data/icons/ice-cream-42/64/scoop-dipper-spoon-ice-cream-512.png"
        ),
        Meal(
            5,
            "burger",
            "https://image.shutterstock.com/image-photo/whole-wheat-bread-bakery-isolate-600w-371617006.jpg"
        ),
    )
    val minutesList: List<DeliveryTime> = listOf(
        DeliveryTime(1, 10, "MIN"),
        DeliveryTime(2, 20, "MIN"),
        DeliveryTime(3, 30, "MIN"),
        DeliveryTime(4, 45, "MIN"),
        DeliveryTime(5, 60, "MIN"),
        DeliveryTime(6, 80, "MIN"),
        DeliveryTime(7, 100, "MIN"),
        DeliveryTime(8, 120, "MIN"),
    )
}
data class SavedItem(val id:Int, val imageUrl:String, val name:String, val price:String, val rate:Double, val count:Int=0)
data class Meal(val id: Int, val name: String, val icon: String)
data class DeliveryTime(val id: Int, val minutes: Int, val measurementUnit: String)