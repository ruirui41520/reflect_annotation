package com.example.testcoroutine.ApiClient;

import com.example.testcoroutine.ApiClient.Exception.ApiException;
import com.example.testcoroutine.ApiClient.Exception.NetworkException;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import rx.Single;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.HttpException;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.functions.Func1;

public class RxErrorHandlingCallAdapterFactory extends CallAdapter.Factory {
//    private final RxJavaCall
    private final RxJavaCallAdapterFactory factory;

    public RxErrorHandlingCallAdapterFactory() {
        this.factory = RxJavaCallAdapterFactory.create();
    }

    public static CallAdapter.Factory create(){
        return new RxErrorHandlingCallAdapterFactory();
    }

    @Override
    public CallAdapter<?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
        return new RxCallAdapterWrapper(factory.get(returnType,annotations,retrofit));
    }

    private static class RxCallAdapterWrapper implements CallAdapter<Single<?>> {
        private final CallAdapter<?> wrapped;
        public RxCallAdapterWrapper(CallAdapter<?> wrapped) {
            this.wrapped = wrapped;
        }

        @Override
        public Type responseType() {
            return wrapped.responseType();
        }

        @Override
        public <R> Single<?> adapt(Call<R> call) {
            return ((Single) wrapped.adapt(call)).onErrorResumeNext(new Func1<Throwable, rx.Single>() {
                @Override
                public Single call(Throwable throwable) {
                    return Single.error(asApiException(throwable));
                }
            });
        }

        private ApiException asApiException(Throwable throwable){
            if (throwable != null) {
                try {
                    // We had non-200 http error
                    if (throwable instanceof HttpException) {
                        HttpException httpException = (HttpException) throwable;
                        try {
                            return ApiErrorResolver.asXApiException(httpException);
                        } catch (IOException e) {

                        }
                    }
                    // A network error happened
                    if (throwable instanceof IOException) {
                        return new NetworkException(throwable);
                    }

                    // We don't know what happened. We need to simply convert to an unknown error
                    return new ApiException(throwable);
                } catch (Exception e) {
                    return new ApiException();
                }
            } else {
                return new ApiException();
            }
        }
    }
}
