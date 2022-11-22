package com.ys_production.flickerbrowser

import android.os.Parcel
import android.os.Parcelable

//import java.io.IOException

class FlickerDataModel(
    var title: String, var link: String, var imglink: String,
    var published: String, var author: String, var tags: String,
)
//    java.io.Serializable

    : Parcelable {

//    @Throws(java.io.IOException::class)
//    private fun writeObject(out: java.io.ObjectOutputStream){
//        out.writeUTF(title)
//        out.writeUTF(link)
//        out.writeUTF(imglink)
//        out.writeUTF(published)
//        out.writeUTF(author)
//        out.writeUTF(tags)
//    }
//    @Throws(IOException::class, ClassNotFoundException::class)
//    private fun readObject(inp : java.io.ObjectInputStream){
//        title = inp.readUTF()
//        link = inp.readUTF()
//        imglink = inp.readUTF()
//        published = inp.readUTF()
//        author = inp.readUTF()
//        tags = inp.readUTF()
//    }

    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(link)
        parcel.writeString(imglink)
        parcel.writeString(published)
        parcel.writeString(author)
        parcel.writeString(tags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<FlickerDataModel> {
        override fun createFromParcel(parcel: Parcel): FlickerDataModel {
            return FlickerDataModel(parcel)
        }

        override fun newArray(size: Int): Array<FlickerDataModel?> {
            return arrayOfNulls(size)
        }
//        private const val serialVersionUID = 1L
    }
}