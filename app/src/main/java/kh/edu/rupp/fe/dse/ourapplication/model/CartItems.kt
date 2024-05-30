package kh.edu.rupp.fe.dse.ourapplication.model

data class CartItems(
    var productName: String? = null,
    var productPrice: String? = null,
    var productDescription: String? = null,
    var productImageUrl: String? = null,
    var productQuantity: Int = 0,
) {
    constructor() : this(null, null, null, null, 0)
}
