package com.example.gdg_task

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

data class ContentItem(
    val text: String = "",
    val imageUrl: String = "",
    val actionUrl: String? = null
)

class FirestoreContentRepository(private val db: FirebaseFirestore) {
    fun listenToContent(): Flow<List<ContentItem>> = callbackFlow {
        val registration = db.collection("contents")
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    trySend(emptyList()).isSuccess
                    return@addSnapshotListener
                }
                val items = snapshot?.documents?.map { doc ->
                    ContentItem(
                        text = doc.getString("text") ?: "",
                        imageUrl = doc.getString("imageUrl") ?: ""
                    )
                } ?: emptyList()
                trySend(items).isSuccess
            }
        awaitClose { registration.remove() }
    }
}