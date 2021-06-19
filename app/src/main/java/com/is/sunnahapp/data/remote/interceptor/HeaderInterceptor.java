package com.is.sunnahapp.data.remote.interceptor;

import com.is.sunnahapp.Config;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author Ahmed Eid Hefny
 * @date 4/1/21
 * <p>
 * ahmedeid2026@gmail.com
 **/
public class HeaderInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request()
                .newBuilder()
                .addHeader("X-API-Key", Config.API_KEY)
                //.removeHeader("User-Agent")
                .build();
        Response response = chain.proceed(request);
        return response;
    }
}