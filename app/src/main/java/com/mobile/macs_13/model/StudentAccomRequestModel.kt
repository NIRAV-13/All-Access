package com.mobile.macs_13.model

import android.provider.ContactsContract.CommonDataKinds.Phone
import java.util.Date


data class StudentAccomRequestModel(var name:String?,
                                    var email:String?,
                                    var course : String?,
                                    var program : String?,
                                    var term : String?,
                                    var year : String?,
                                    var phone: String?,
                                    var uid:String?,
                                    var impact: String?,
                                    var consent:String?,
                                    var timeStamp : Date?)
