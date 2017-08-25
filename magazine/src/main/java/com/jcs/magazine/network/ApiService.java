package com.jcs.magazine.network;

import com.jcs.magazine.bean.ArticleBean;
import com.jcs.magazine.bean.BannerItem;
import com.jcs.magazine.bean.BaseListTemplet;
import com.jcs.magazine.bean.BaseMgz;
import com.jcs.magazine.bean.ContentsBean;
import com.jcs.magazine.bean.MgzCoverBean;
import com.jcs.magazine.bean.MomentBean;
import com.jcs.magazine.bean.UserBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * author：Jics
 * 2017/7/31 13:29
 */
public interface ApiService {
	/**
	 *
	 * @param page 分页中的第page页
	 * @param count 每页个数
	 * @return
	 */
	@GET("magazine/cover/{page}/{count}")
	Observable<BaseListTemplet<MgzCoverBean>> getMagazineCover(@Path("page") int page, @Path("count") int count);

	/**
	 *
	 * @param id 某期杂志的编号
	 * @return
	 */
	@GET("magazine/contents/{volId}")
	Observable<BaseListTemplet<ContentsBean>> getContents(@Path("volId") int id);

	/**
	 *
	 * @param id 文章id获取文章正文
	 * @return
	 */
	@GET("magazine/article/{articleId}")
	Observable<BaseMgz<ArticleBean>> getArticle(@Path("articleId") String id);
	/**
	 *
	 * @param id 根据用户id获取信息
	 * @return
	 */
	@GET("magazine/user/{uid}")
	Observable<BaseMgz<UserBean>> getUser(@Path("uid") String id);

	/**
	 *
	 * @param versionNo 本地版本号
	 * @return
	 */
	@GET("magazine/version/{versionNo}")
	Observable<BaseMgz<ArticleBean>> checkUpdata(@Path("articleId") String versionNo);

	/**
	 *	获取广场列表
	 * @param page
	 * @param count
	 * @return
	 */
	@GET("magazine/moment/{page}/{count}")
	Observable<BaseListTemplet<MomentBean>> getMomentLists(@Path("page") int page, @Path("count") int count);
	/**
	 *
	 * 获取广场banner
	 * @return
	 */
	@GET("magazine/moment/banner")
	Observable<BaseListTemplet<BannerItem>> getMomentBannder();
}
