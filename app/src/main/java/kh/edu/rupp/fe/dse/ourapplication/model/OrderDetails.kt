package kh.edu.rupp.fe.dse.ourapplication.model

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

class OrderDetails(): Serializable {
    var userUid: String? = null
    var userName: String? = null
    var productNames:MutableList<String>? = null
    var productPrices:MutableList<String>? = null
    var productImagesUri:MutableList<String>? = null
    var quantity:MutableList<Int>? = null
    var address: String? = null
    var totalPrice: String? = null
    var phoneNumber: String? = null
    var orderAccepted: Boolean? = null
    var paymentReceived: Boolean? = null
    var itemPushKey: String? = null
    var currentTime: Long = 0

    constructor(parcel: Parcel) : this() {
        userUid = parcel.readString()
        userName = parcel.readString()
        address = parcel.readString()
        totalPrice = parcel.readString()
        phoneNumber = parcel.readString()
        orderAccepted = parcel.readValue(Boolean::class.java.classLoader) as? Boolean
        paymentReceived = parcel.readValue(Boolean::class.java.classLoader) as? Boolean
        itemPushKey = parcel.readString()
        currentTime = parcel.readLong()
    }

    constructor(
        userId: String,
        name: String,
        productItemName: ArrayList<String>,
        productItemImage: ArrayList<String>,
        productItemQuantity: ArrayList<Int>,
        productItemPrice: ArrayList<String>,
        address: String,
        phone: String,
        totalAmount: String,
        time: Long,
        itemPushKey: String?,
        b: Boolean,
        b1: Boolean
    ):this(){
        this.userUid = userUid
        this.userName = name
        this.productNames = productItemName
        this.productImagesUri = productItemImage
        this.productPrices = productItemPrice
        this.quantity = productItemQuantity
        this.address = address
        this.totalPrice = totalAmount
        this.phoneNumber = phone
        this.currentTime = time
        this.itemPushKey = itemPushKey
        this.orderAccepted = orderAccepted
        this.paymentReceived = paymentReceived
    }


    fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(userUid)
        parcel.writeString(userName)
        parcel.writeString(address)
        parcel.writeString(totalPrice)
        parcel.writeString(phoneNumber)
        parcel.writeValue(orderAccepted)
        parcel.writeValue(paymentReceived)
        parcel.writeString(itemPushKey)
        parcel.writeLong(currentTime)
    }

    fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<OrderDetails> {
        override fun createFromParcel(parcel: Parcel): OrderDetails {
            return OrderDetails(parcel)
        }

        override fun newArray(size: Int): Array<OrderDetails?> {
            return arrayOfNulls(size)
        }
    }

}