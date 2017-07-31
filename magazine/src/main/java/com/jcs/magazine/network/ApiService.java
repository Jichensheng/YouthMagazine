package com.jcs.magazine.network;

import com.jcs.magazine.bean.MgzBean;
import com.jcs.magazine.bean.MagazineCoverBean;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * author：Jics
 * 2017/7/31 13:29
 */
public interface ApiService {
	@GET("magazine/cover/exhibitions")
	Observable<MgzBean<MagazineCoverBean>> getMagazineCover();

}
