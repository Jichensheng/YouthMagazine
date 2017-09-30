package com.jcs.magazine.network;

import com.jcs.magazine.bean.ArticleBean;
import com.jcs.magazine.bean.BannerItem;
import com.jcs.magazine.bean.BaseListTemplet;
import com.jcs.magazine.bean.BaseMgz;
import com.jcs.magazine.bean.CommentBean;
import com.jcs.magazine.bean.ContentsBean;
import com.jcs.magazine.bean.MgzCoverBean;
import com.jcs.magazine.bean.MomentBean;
import com.jcs.magazine.bean.TalkBean;
import com.jcs.magazine.bean.UserBean;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
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
	 *	获取某用户的发帖列表
	 * @param page
	 * @param count
	 * @return
	 */
	@GET("magazine/user/{uid}/{page}/{count}")
	Observable<BaseListTemplet<MomentBean>> getUserPostLists(@Path("uid") String uid,@Path("page") int page, @Path("count") int count);
	/**
	 *
	 * 获取广场banner
	 * @return
	 */
	@GET("magazine/moment/banner")
	Observable<BaseListTemplet<BannerItem>> getMomentBannder();
	/**
	 *
	 * 获取广场banner
	 * @return
	 */
	@GET("magazine/love/banner")
	Observable<BaseListTemplet<BannerItem>> getLoveBannder();

	/**
	 *	获取Talk列表
	 * @param page
	 * @param count
	 * @return
	 */
	@GET("magazine/talk/{page}/{count}")
	Observable<BaseListTemplet<TalkBean>> getTalkLists(@Path("page") int page, @Path("count") int count);

	/**
	 *	获取Talk列表
	 * @param page
	 * @param count
	 * @return
	 */
	@GET("magazine/radio/{page}/{count}")
	Observable<BaseListTemplet<ContentsBean.ArticlesBean>> getRadioLists(@Path("page") int page, @Path("count") int count);
	/**
	 *	获取Talk列表
	 * @param page
	 * @param count
	 * @return
	 */
	@GET("magazine/everything/{page}/{count}")
	Observable<BaseListTemplet<ContentsBean.ArticlesBean>> getEverythingLists(@Path("page") int page, @Path("count") int count);
	/**
	 *	获取评论列表
	 * @param page
	 * @param count
	 * @return
	 */
	@GET("magazine/comment/{mid}/{page}/{count}")
	Observable<BaseListTemplet<CommentBean>> getCommentLists(@Path("mid") String mid, @Path("page") int page, @Path("count") int count);
	/**
	 *	获取用户列表
	 * @param page
	 * @param count
	 * @return
	 */
	@GET("magazine/follow/{uid}/{type}/{page}/{count}")
	Observable<BaseListTemplet<UserBean>> getFollowLists(@Path("uid") String uid,@Path("type") int type, @Path("page") int page, @Path("count") int count);


	/**
	 *
	 * @param id 根据用户id获取信息
	 * @return
	 */
	@GET("magazine/user/id/{uid}")
	Observable<BaseMgz<UserBean>> getUser(@Path("uid") String id);

	//Todo 测试临时用 get
	@GET("magazine/user/login/{nick}/{psw}")
	Observable<BaseMgz<UserBean>> login(@Path("nick") String nick,@Path("psw") String psw);

	/**
	 * Content-Type: application/x-www-form-urlencoded
	 * @param nick
	 * @param psw
	 * @return
	 */
	@FormUrlEncoded
	@POST("magazine/user/login")
	Observable<BaseMgz<UserBean>> loginPost(@Field("nick") String nick, @Field("psw") String psw );

	/**
	 * 更新用户信息 RequestBody形式
	 * @param Body
	 * @return
	 */
	@POST("magazine/user/updataUserInfo")
	Observable<BaseMgz<UserBean>> updataUserInfo(@Body RequestBody Body);

	/**
	 * 用户注册 RequestBody形式
	 * @param Body
	 * @return
	 */
	@POST("magazine/user/regist")
	Observable<BaseMgz<UserBean>> registUserInfo(@Body RequestBody Body);
	/**
	 * 发表说说 RequestBody形式
	 * @param Body
	 * @return
	 */
	@POST("magazine/moment/post")
//	@POST("upload")
	Observable<BaseMgz> makePost(@Body RequestBody Body);

	/**
	 * 发表评论
	 * @param commentBean
	 * @return
	 */
	@POST("magazine/comment/sendComment")
	Observable<BaseMgz> sendComment(@Body CommentBean commentBean);
}
