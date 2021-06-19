package com.example.testcoroutine.ApiClient.ApiServer;

import com.example.testcoroutine.ApiClient.Exception.XApiException;
import com.example.testcoroutine.ApiClient.Model.AuthTokenInfo;
import com.example.testcoroutine.ApiClient.Model.IResultContainer;
import com.example.testcoroutine.ApiClient.Request.RefreshTokenRequest;
import com.example.testcoroutine.ApiClient.Subscriber.ApiClientSubscriber;
import com.example.testcoroutine.CustomApplication;
import com.example.testcoroutine.Utils.DeviceUtils;

import java.util.concurrent.ConcurrentLinkedDeque;

import rx.Single;
import rx.SingleSubscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ApiRequester {

    private static final String TAG = ApiRequester.class.getSimpleName();
    private static final long MILLIS_PER_DAY = 24L * 60 * 60 * 1000;

    private static final ApiRequester inst = new ApiRequester();

    private boolean isRefreshTokenRunning = false;
    public static ApiRequester instance() {
        return inst;
    }

    private final ConcurrentLinkedDeque<SingleSubscriber<IResultContainer>> requests = new ConcurrentLinkedDeque<>();

    private ApiRequester() {

    }

    public <T extends IResultContainer> void request(final Single<T> api, final SingleSubscriber<T> responseObserver) {
        request(api, responseObserver, false);
    }

    public <T extends IResultContainer> void request(final Single<T> api, final SingleSubscriber<T> responseObserver, boolean forcedRefresh) {
        if (needToRefreshToken() == false && !forcedRefresh) {
            api.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SingleSubscriber<T>() {
                        @Override
                        public void onSuccess(T value) {
                            responseObserver.onSuccess(value);
                        }

                        @Override
                        public void onError(Throwable error) {
                            if (error instanceof XApiException) {
                                if (((XApiException) error).getErrorCode() == 480) {
                                    CustomApplication.getPreferenceUtils().setApiTokenExpireTime(System.currentTimeMillis());
                                    request(api, responseObserver, true);
                                    return;
                                }
                            }
                            responseObserver.onError(error);
                        }
                    });
            return;
        }

        SingleSubscriber<IResultContainer> request = new SingleSubscriber<IResultContainer>() {
            @Override
            public void onSuccess(IResultContainer value) {
                api.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(responseObserver);
            }

            @Override
            public void onError(Throwable error) {
                responseObserver.onError(error);
            }
        };
        requests.add(request);
        refreshToken();
    }

    private boolean needToRefreshToken() {
        //checked 1-day
        long lastTimeOfRefreshToken = CustomApplication.getPreferenceUtils().getLastTimeOfRefreshToken();
        long currTime = System.currentTimeMillis();
        if ((currTime - lastTimeOfRefreshToken) > MILLIS_PER_DAY || lastTimeOfRefreshToken == 0) {
            return true;
        }
        return false;
    }

    private void refreshToken(){
        if (isRefreshTokenRunning) {
            return;
        }
        isRefreshTokenRunning = true;
        String refreshToken = CustomApplication.getPreferenceUtils().getRefreshToken();
        UserServer.requestRefreshAccessToken(new RefreshTokenRequest(refreshToken, DeviceUtils.getDeviceId(CustomApplication.getInstance())), new ApiClientSubscriber<IResultContainer<AuthTokenInfo>>() {
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                try {
                    while (true) {
                        SingleSubscriber<IResultContainer> request = requests.poll();
                        if (request == null) {
                            break;
                        }
                        request.onError(e);
                    }
                    isRefreshTokenRunning = false;
                } catch (Exception ex) {
                }
            }

            @Override
            public void onSuccess(IResultContainer<AuthTokenInfo> value) {
                AuthTokenInfo authTokenInfo = value.getData();
                if (authTokenInfo != null){
                    CustomApplication.getPreferenceUtils().setRefreshToken(authTokenInfo.getRefreshToken());
                }

                try {
                    while (true) {
                        SingleSubscriber<IResultContainer> request = requests.poll();
                        if (request == null) {
                            break;
                        }
                        request.onSuccess(value);
                    }
                    isRefreshTokenRunning = false;
                } catch (Exception ex) {
                }
            }
        });


    }

}
