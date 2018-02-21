package org.dukecon.android.ui.injection

import android.app.Application
import android.content.Context
import com.fatboyindustrial.gsonjodatime.Converters
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.dukecon.android.api.ConferencesApi
import org.dukecon.android.cache.EventCacheImpl
import org.dukecon.android.ui.UiThread
import org.dukecon.android.ui.injection.scopes.PerApplication
import org.dukecon.data.executor.JobExecutor
import org.dukecon.data.mapper.EventMapper
import org.dukecon.data.repository.EventCache
import org.dukecon.data.repository.EventRemote
import org.dukecon.domain.executor.PostExecutionThread
import org.dukecon.domain.executor.ThreadExecutor
import org.dukecon.remote.mapper.EventRemoteImpl
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Module used to provide dependencies at an application-level.
 */
@Module
open class ApplicationModule {

    @Provides
    fun provideContext(application: Application): Context {
        return application
    }



    @Provides
    internal fun provideEventCache(entityMapper: org.dukecon.android.cache.mapper.EventEntityMapper,
                                   mapper: EventMapper): EventCache {
        return EventCacheImpl(entityMapper, mapper)
    }

    @Provides
    internal fun provideEventRemote(service: ConferencesApi, factory: org.dukecon.remote.mapper.EventEntityMapper): EventRemote {
        return EventRemoteImpl(service, "conference", factory)
    }

    @Provides
    internal fun provideThreadExecutor(jobExecutor: JobExecutor): ThreadExecutor {
        return jobExecutor
    }

    @Provides
    internal fun providePostExecutionThread(uiThread: UiThread): PostExecutionThread {
        return uiThread
    }

    @Singleton
    @Provides
    fun provideNonCachedOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        return Converters.registerDateTime(gsonBuilder).create()
    }

    @Provides
    internal fun provideConfernceService(client: OkHttpClient, gson: Gson): ConferencesApi {
        var restAdapter = Retrofit.Builder()
                .baseUrl("https://latest.dukecon.org/javaland/2017/rest/") //endpoitUrlProvider.getUrl())
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

        return restAdapter.create(ConferencesApi::class.java)
    }
}
