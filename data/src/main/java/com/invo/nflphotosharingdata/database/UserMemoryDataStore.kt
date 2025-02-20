package com.invo.nflphotosharingdata.database

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class UserMemoryDataStore @Inject constructor(
    @ApplicationContext context: Context
) {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "memories")
    private val dataStore = context.dataStore
    private val SELECTED_PHOTO_URIS = stringSetPreferencesKey("selected_photo_uris")

    suspend fun savePhotoUri(uri: String) {
        dataStore.edit { preferences ->
            val currentUris = preferences[SELECTED_PHOTO_URIS] ?: emptySet()
            preferences[SELECTED_PHOTO_URIS] = currentUris + uri
        }
    }

    fun getPhotoUris(): Flow<Set<String>> = dataStore.data.map { preferences ->
        preferences[SELECTED_PHOTO_URIS] ?: emptySet()
    }
}