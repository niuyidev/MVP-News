package com.niuyi.mvp_news.mvp.presenter;

import com.niuyi.mvp_news.bean.SocietyNewsBean;
import com.niuyi.mvp_news.mvp.contract.SocietyContract;
import com.niuyi.mvp_news.mvp.model.SocietyModel;
import com.orhanobut.logger.Logger;

import rx.Subscriber;
import rx.Subscription;

/**
 * 作者：${牛毅} on 2016/11/30 15:24
 * 邮箱：niuyi19900923@hotmail.com
 */
public class SocietyPresenter extends SocietyContract.Presenter {

    public SocietyPresenter(SocietyContract.View view) {
        mView = view;
        mModel = new SocietyModel();
    }

    @Override
    public void getSocietyNews() {

        Subscription subscription = mModel.getSocietyNews()
                .subscribe(new Subscriber<SocietyNewsBean>() {
                    @Override
                    public void onStart() {
                        mView.showRefreshDialog();
                    }

                    @Override
                    public void onCompleted() {
                        mView.hideRefreshDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        onCompleted();
                        mView.onRefreshFail(e.getMessage());
                    }

                    @Override
                    public void onNext(SocietyNewsBean societyNewsBean) {
                        mView.onRefreshSucceed(societyNewsBean.getResult().getData());
                    }
                });

        addSubscribe(subscription);
    }

    @Override
    public void refresh() {
        getSocietyNews();
    }

}
