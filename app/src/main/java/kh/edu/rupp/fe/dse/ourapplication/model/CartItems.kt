package kh.edu.rupp.fe.dse.ourapplication.model

data class CartItems(
    var productName: String? = null,
    var productPrice: String? = null,
    var productDescription: String? = null,
    var productImage: String? = null,
    var productQuantity: Int = 0,
)