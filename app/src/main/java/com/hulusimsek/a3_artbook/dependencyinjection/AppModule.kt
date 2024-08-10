package com.hulusimsek.a3_artbook.dependencyinjection

import android.content.Context
import androidx.fragment.app.FragmentFactory
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.hulusimsek.a3_artbook.R
import com.hulusimsek.a3_artbook.adapter.ArtRecyclerAdapter
import com.hulusimsek.a3_artbook.adapter.ImageRecyclerAdapter
import com.hulusimsek.a3_artbook.api.RetrofitAPI
import com.hulusimsek.a3_artbook.repo.ArtRepository
import com.hulusimsek.a3_artbook.repo.ArtRepositoryInterface
import com.hulusimsek.a3_artbook.roomdb.ArtDao
import com.hulusimsek.a3_artbook.roomdb.ArtDatabase
import com.hulusimsek.a3_artbook.util.Util.BASE_URL
import com.hulusimsek.a3_artbook.view.ArtFragmentFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun injectRoomDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, ArtDatabase::class.java, "ArtBookDB").build()

    @Singleton
    @Provides
    fun injectDao(database: ArtDatabase) = database.artDao()

    @Singleton
    @Provides
    fun injectRetrofitAPI() : RetrofitAPI {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL).build().create(RetrofitAPI::class.java)
    }

    @Singleton
    @Provides
    fun injectNormalRepo(dao: ArtDao, api:RetrofitAPI) = ArtRepository(dao,api) as ArtRepositoryInterface

    @Singleton
    @Provides
    fun injecrtGlide(@ApplicationContext context: Context) = Glide.with(context)
        .setDefaultRequestOptions(
            RequestOptions().placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
        )

    @Singleton
    @Provides
    fun provideFragmentFactory(
        artRecyclerAdapter: ArtRecyclerAdapter,
        glide: RequestManager,
        imageRecyclerAdapter: ImageRecyclerAdapter
    ): ArtFragmentFactory {
        return ArtFragmentFactory(artRecyclerAdapter, glide, imageRecyclerAdapter)
    }
}






















