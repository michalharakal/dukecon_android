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
import org.dukecon.android.cache.PreferencesHelper
import org.dukecon.android.ui.UiThread
import org.dukecon.data.executor.JobExecutor
import org.dukecon.data.repository.EventCache
import org.dukecon.data.repository.EventRemote
import org.dukecon.domain.executor.PostExecutionThread
import org.dukecon.domain.executor.ThreadExecutor
import org.dukecon.remote.mapper.EventEntityMapper
import org.dukecon.remote.mapper.EventRemoteImpl
import org.dukecon.remote.mapper.RoomEntityMapper
import org.dukecon.remote.mapper.SpeakerEntityMapper
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.security.cert.X509Certificate
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
    internal fun providePreferencesHelper(context: Context): PreferencesHelper {
        return PreferencesHelper(context)
    }


    @Provides
    @Singleton
    internal fun provideEventCache(application: Application, gson: Gson, preferencesHelper: PreferencesHelper): EventCache {
        val baseCacheFolder = application.filesDir.absolutePath
        return EventCacheImpl(baseCacheFolder, gson, preferencesHelper)
    }

    @Provides
    internal fun provideEventRemote(service: ConferencesApi,
                                    factory: EventEntityMapper,
                                    speakerMapper: SpeakerEntityMapper,
                                    roomEntityMapper: RoomEntityMapper): EventRemote {
        return EventRemoteImpl(service, "javaland2018", factory, speakerMapper, roomEntityMapper)
    }


    @Provides
    internal fun provideThreadExecutor(jobExecutor: JobExecutor): ThreadExecutor {
        return jobExecutor
    }

    @Provides
    internal fun providePostExecutionThread(uiThread: UiThread): PostExecutionThread {
        return uiThread
    }

    class DO_NOT_VERIFY_IMP : javax.net.ssl.HostnameVerifier {
        override fun verify(p0: String?, p1: javax.net.ssl.SSLSession?): Boolean {
            return true
        }

    }

    class XtmImp : javax.net.ssl.X509TrustManager {
        override fun checkClientTrusted(p0: Array<out X509Certificate>?, p1: String?) {
        }

        override fun checkServerTrusted(p0: Array<out X509Certificate>?, p1: String?) {
        }

        override fun getAcceptedIssuers(): Array<X509Certificate> {
            val x509Certificates: Array<X509Certificate> = arrayOf()
            return x509Certificates
        }

    }

    @Singleton
    @Provides
    fun provideNonCachedOkHttpClient(): OkHttpClient {

        val xtm = XtmImp()
        val sslContext = javax.net.ssl.SSLContext.getInstance("SSL")
        try {
            sslContext.init(null, arrayOf<javax.net.ssl.TrustManager>(xtm), java.security.SecureRandom())
        } catch (e: java.security.NoSuchAlgorithmException) {
            e.printStackTrace()
        } catch (e: java.security.KeyManagementException) {
            e.printStackTrace()
        }

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .sslSocketFactory(sslContext.getSocketFactory(), xtm)
                .hostnameVerifier(DO_NOT_VERIFY_IMP())
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
        var restAdapter = Retrofit.Builder() // https://latest.dukecon.org/javaland/2018/rest/conferences/javaland2018/events
                .baseUrl("https://programm.javaland.eu/2018/rest") //https://latest.dukecon.org/javaland/2018/rest/") //endpoitUrlProvider.getUrl())
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

        return restAdapter.create(ConferencesApi::class.java)
    }
}
