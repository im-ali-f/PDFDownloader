package com.example.pdfdownloader.VMs


import com.example.pdfdownloader.VMs.Room.RoomModel
import com.example.pdfdownloader.VMs.Room.db
import retrofit2.Response

class Repository(val db: db) {
    suspend fun GetUsersList(): Response<UserInfoResponseList> {

        return RetrofitInstance.api.GetUserList()
    }


    suspend fun CreateUser(body: UserInfoResponseListItem): Response<UserInfoResponseListItem> {

        return RetrofitInstance.api.CreateUser(body)
    }


    //room

    suspend fun InsertPDF(RoomModel: RoomModel){
        db.PDFDAO().InsertPDF(RoomModel)
    }

    suspend fun GetAllPDF()= db.PDFDAO().GetAllPDF()

    suspend fun UpdatePDF(RoomModel:RoomModel){
        db.PDFDAO().UpdatePDF(RoomModel)
    }
}