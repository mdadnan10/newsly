package com.newses.newsly

object ColorPicker {
    val colors = arrayOf("#3EB9DF", "#3685BC", "#D36280", "#E44F55", "#FA8056", "#818BCA", "#7D659F", "#51BAB3", "#4FB66C", "#E3AD17", "#627991", "#EF8EAD", "#B5BFC6",
    "#7d6361", "#ad2b21", "#8b7094", "#2b5b6b", "#a33776", "#8f8545", "#b099a8")
    var colorIndex = 1
    fun getColor(): String{
        return colors[colorIndex++ % colors.size]
    }
}