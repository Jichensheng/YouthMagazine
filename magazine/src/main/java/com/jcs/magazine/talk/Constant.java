package com.jcs.magazine.talk;

/**
 * Created by dingmouren on 2017/1/19.
 */

public class Constant {
    public static final String NOTIFICATION_INTENT_ACTION = "notification_intent_action";
    //客户端.what的属性
    public static final int PLAYING_ACTIVITY_PLAY = 0x10001;
    public static final int PLAYING_ACTIVITY_NEXT = 0x10002;
    public static final int PLAYING_ACTIVITY_SINGLE = 0x10003;
    public static final int MusicAcitcity_ACTIVITY = 0x10004;//初始化PlayingActivity的Messenger对象的标识
    public static final int PLAYING_ACTIVITY_CUSTOM_PROGRESS = 0x10005;//用户拖动进度条时，更新音乐播放器的播放进度
    public static final int PLAYING_ACTIVITY_INIT = 0x10006;//向播放器传递的歌曲集合数据,进行初始化
    public static final int PLAYING_ACTIVITY_PLAYING_POSITION = 0x10007;//播放歌曲的位置
    public static final int PLAYING_ACTIVITY_PLAY_MODE = 0x10008;//播放模式
    public static final int PLAYING_ACTIVITY_PLAY_NOW = 0x10009;//播放模式
    public static final String PLAYING_ACTIVITY_DATA_KEY = "playing_activity_data_key";//向播放器传递的歌曲集合数据的key

    //MediaPlayerService的message.what的属性值
    public static final int MEDIA_PLAYER_SERVICE_PROGRESS = 0x20001;
    public static final int MEDIA_PLAYER_SERVICE_SONG_PLAYING = 0x20002;
    public static final int MEDIA_PLAYER_SERVICE_IS_PLAYING = 0x20003;//播放器是否在播放音乐，用于修改PlayingActivity的UI
    public static final int MEDIA_PLAYER_SERVICE_UPDATE_SONG = 0x20004;//通知PalyingActivity跟换专辑图片  歌曲信息等
    public static final int MEDIA_PLAYER_SERVICE_DOWNLOAD_PROGRESS = 0x20005;//通知PalyingActivity跟换专辑图片  歌曲信息等
    public static final String MEDIA_PLAYER_SERVICE_MODEL_PLAYING = "song_playing";//服务端正在播放的歌曲
    public static final String MUSIC_LOCAL = "27";//本地音乐

}
