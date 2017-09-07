package com.jcs.magazine.mock;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.jcs.magazine.util.FileUtil;
import com.jcs.magazine.util.LocalFileManager;

/**
 * author：Jics
 * 2017/8/3 15:23
 */
public class MockConfig {
	public static final String[] URLS = new String[]{
			"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1503571662854&di=369d2e849261697eb06c31b922b2c488&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimage%2Fc0%253Dshijue1%252C0%252C0%252C294%252C40%2Fsign%3D5c73f5d5df62853586edda62f8861cb3%2Fe4dde71190ef76c660cc740c9716fdfaaf5167ff.jpg",
//			"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1503571662853&di=2f031f40c8c89446fedb7a994943235a&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimage%2Fc0%253Dshijue1%252C0%252C0%252C294%252C40%2Fsign%3Db73655df71cb0a46912f837a030a9c51%2Ff31fbe096b63f624d0a58e268d44ebf81a4ca31f.jpg",
//			"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1503571662852&di=34c8d799ec7820cc1a6e54d50aeb3253&imgtype=0&src=http%3A%2F%2Fimg10.360buyimg.com%2Fimgzone%2Fjfs%2Ft181%2F182%2F1003032794%2F118299%2Fbe1b0d6c%2F53a139bfN3b5bc4d7.jpg",
//			"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1503571866927&di=a057d7361cfe7b5c731fd48a5f3f947d&imgtype=0&src=http%3A%2F%2Fec4.images-amazon.com%2Fimages%2FI%2F51I7hEMKFvL._SY400_.jpg",
			"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1503571662868&di=9889cb43078a1d2c08e4966de0f7ca3f&imgtype=0&src=http%3A%2F%2Fwww.juke123.com%2Fres%2Fattachs%2Fimage%2F20150128%2F20150128170113_93935.png"};

	public static final String CONTENT = "　　刚刚过去的清明节，是一个特殊的日子，对于很多现代人来说，因为放假，可以不上班，可以和朋友出去约会喝酒，" +
			"似乎和春节、端午节这种团圆的节日，没什么区别。<br> <br> 　　而清明之于我却有着特殊的意义，不是缅怀离世的亲人，而是想念一位朋友，我的发小。" +
			"从小学到高中他一直都是我最好的哥们，直到读了大学我们各奔东西，联系几乎断了。大三的时候，再次回老家，突然听家里人说他在去年出车祸，" +
			"去世了。我很惊讶，很难过，之后便是内疚：为什么我过了一年才知道，为什么我曾经最好的朋友就这么离开了，我都是在别人的嘴里听到的？<br> <br> 　　和" +
			"朋友相处，很像是在养盆栽，普通朋友同事，是隔几天就要浇水的植物，但可能你不管怎么悉心照料也还是会死掉，而老朋友更像是“仙人掌”，" +
			"一个月浇一次水就可以，他依旧可以在你想他的时候生机勃勃地看着你，陪着你。但也别忘了，就算是沙漠，偶尔也是要下雨的啊。<br> <br> 　　所以" +
			"，今天，我想说一句：嘿，兄弟，好久不见，你在哪里？";

	public static final String BODY = "<div class=\"simditor-body\" contenteditable=\"true\"><p style=\"text-align: center;\"><span style=\"font-size: 1.5em;\"><b>父与子</b></span><br></p><p>一</p><blockquote><p>我和哥哥小时候都喜欢看德国漫画家卜劳恩的漫画《父与子》，两个人扎在一起，一边看一边哈哈大笑，觉得这一对父子真是活宝，乐趣无穷：原来德国的爸爸也不是完全的民主，会撸起袖子揍人；原来德国的爸爸也会这么逗弄儿子，幽默有趣；原来天底下所有的儿子都是这样可爱淘气，让爸爸又爱又急；原来爸爸陪着儿子玩最能玩出花样……我的父亲当然虽然不像德国爸爸，但也同样有趣。这书是借来的，一直被我俩翻得面目全非才还给人家。</p></blockquote><p style=\"text-align: center;\"><img alt=\"Image\" src=\"http://simditor.tower.im/assets/images/image.png\" width=\"120\" height=\"120\"><br></p><p>父亲去世，中间离散了好些年，也没再看过这书。</p><p>后来哥哥有了小侄子，当了爸爸，我给哥哥买了一本，希望他时常翻翻。</p><p>再后来，我也当了爸爸，有了小南风，我也给自己买了一本，时常翻翻。</p><p>但此时再看，想起父亲，少了谑笑，多了感动和喟叹。</p><div class=\"simditor-table\"><table><colgroup><col width=\"49.89224137931034%\"><col width=\"50.10775862068966%\"></colgroup><thead><tr><th><br></th><th><br></th></tr></thead><tbody><tr><td><br></td><td><br></td></tr><tr><td><br></td><td><br></td></tr><tr><td><br></td><td><br></td></tr></tbody></table><div class=\"simditor-resize-handle\" contenteditable=\"false\"></div></div><p><br></p></div>";
	public static final String[] BANNER_TITLES = new String[]{
			"每周7件Tee不重样",
//			"俏皮又知性 适合上班族的漂亮衬衫",
//			"大咸鱼",
//			"端午节这种",
			"团圆的节日"
	};
//	public static final String  HEAD="http://tva1.sinaimg.cn/crop.0.0.996.996.180/6d04a765jw8f4xgeqeitoj20ro0rpjui.jpg";
	public static final String  HEAD="http://tva3.sinaimg.cn/crop.0.0.512.512.180/6ef9803cjw8evztqvm4c0j20e80e8t95.jpg";
	/**
	 * 打印各个路径
	 */
	public static void printPathLog(Context context) {
		StringBuilder sb = new StringBuilder();
		sb.append("\nLF-getParent　　　" + context.getExternalFilesDir(null).getParent());
		sb.append("\nLF-checkSdCard　　　" + LocalFileManager.getInstance().checkSdCard());
		sb.append("\nLF-getSdCardRootPath　　　" + LocalFileManager.getInstance().getSdCardRootPath());
		sb.append("\nLF-getAppDownloadDir　　　" + LocalFileManager.getInstance().getAppDownloadDir().getAbsolutePath());
		sb.append("\nLF-getCrashLogDir　　　" + LocalFileManager.getInstance().getCrashLogDir().getAbsolutePath());
		sb.append("\nLF-getCrashLogDir　　　" + LocalFileManager.getInstance().getCrashLogDir().getAbsolutePath());
		sb.append("\nLF-getLogDir　　　" + LocalFileManager.getInstance().getLogDir());

		sb.append("\nFU-getSdcardRootPath　　　" + FileUtil.getSdcardRootPath(context));
		sb.append("\nFU-getProjectRootCache　　　" + FileUtil.getProjectRootCache().getAbsolutePath());
		sb.append("\nFU-getProjectRootFile　　　" + FileUtil.getProjectRootFile().getAbsolutePath());
		sb.append("\nFU-getImageCacheFile　　　" + FileUtil.getImageCacheFile().getAbsolutePath());
		sb.append("\nFU-getTempAvatarFile　　　" + FileUtil.getTempAvatarFile(FileUtil.DEFAULT_PIC_HEAD_NAME).getAbsolutePath());
		sb.append("\nFU-getSDcardAvailaleSize　　　" + FileUtil.getSDcardAvailaleSize());

		sb.append("\nSYS-getFilesDir　　　" + context.getFilesDir().getAbsolutePath());
		sb.append("\nSYS-getDataDir　　　" + context.getDataDir().getAbsolutePath());
		sb.append("\nSYS-getCacheDir　　　" + context.getCacheDir().getAbsolutePath());
		sb.append("\nSYS-getCodeCacheDir　　　" + context.getCodeCacheDir().getAbsolutePath());
		sb.append("\nSYS-getObbDir　　　" + context.getObbDir().getAbsolutePath());

		sb.append("\nEMT-getExternalStorageDirectory　　　" + Environment.getExternalStorageDirectory().getAbsolutePath());
		sb.append("\nEMT-getDataDirectory　　　" + Environment.getDataDirectory().getAbsolutePath());
		sb.append("\nEMT-getDownloadCacheDirectory　　　" + Environment.getDownloadCacheDirectory());
		sb.append("\nEMT-getRootDirectory　　　" + Environment.getRootDirectory().getAbsolutePath());
		sb.append("\nEMT-DIRECTORY_DCIM　　　" + Environment.DIRECTORY_DCIM);
		sb.append("\nEMT-DIRECTORY_DOWNLOADS　　　" + Environment.DIRECTORY_DOWNLOADS);

		Log.e("Jcs_path", sb.toString());
	}
	public static String uedit="<html xmlns=\"http://www.w3.org/1999/xhtml\" class=\"view\"><head><style type=\"text/css\">.view{padding:0;word-wrap:break-word;cursor:text;height:90%;}img {max-width: 100%; }body{margin:8px;font-family:sans-serif;font-size:16px;}p{margin:5px 0;}</style><style id=\"tablesort\">table.sortEnabled tr.firstRow th,table.sortEnabled tr.firstRow td{padding-right:20px;background-repeat: no-repeat;background-position: center right; </style><style id=\"table\">.selectTdClass{background-color:#edf5fa !important}table.noBorderTable td,table.noBorderTable th,table.noBorderTable caption{border:1px dashed #ddd !important}table{margin-bottom:10px;border-collapse:collapse;display:table;}td,th{padding: 5px 10px;border: 1px solid #DDD;}caption{border:1px dashed #DDD;border-bottom:0;padding:3px;text-align:center;}th{border-top:1px solid #BBB;background-color:#F7F7F7;}table tr.firstRow th{border-top-width:2px;}.ue-table-interlace-color-single{ background-color: #fcfcfc; } .ue-table-interlace-color-double{ background-color: #f7faff; }td p{margin:0;padding:0;}</style><style id=\"list\">ol,ul{margin:0;pading:0;width:95%}li{clear:both;}\n" +
			"ol.custom_num2{list-style:none;}ol.custom_num2 li{background-position:0 3px;background-repeat:no-repeat}\n" +
			"li.list-num2-paddingleft-1{padding-left:35px}\n" +
			"li.list-num2-paddingleft-2{padding-left:40px}\n" +
			"li.list-dash{background-image:url(http://bs.baidu.com/listicon/dash.gif)}\n" +
			"ul.custom_dash{list-style:none;}ul.custom_dash li{background-position:0 3px;background-repeat:no-repeat}\n" +
			"li.list-dash-paddingleft{padding-left:35px}\n" +
			"li.list-dot{background-image:url(http://bs.baidu.com/listicon/dot.gif)}\n" +
			"ul.custom_dot{list-style:none;}ul.custom_dot li{background-position:0 3px;background-repeat:no-repeat}\n" +
			"li.list-dot-paddingleft{padding-left:20px}\n" +
			".list-paddingleft-1{padding-left:0}\n" +
			".list-paddingleft-2{padding-left:30px}\n" +
			".list-paddingleft-3{padding-left:60px}</style><style id=\"pagebreak\">.pagebreak{display:block;clear:both !important;cursor:default !important;width: 100% !important;margin:0;}</style><style id=\"pre\">pre{margin:.5em 0;padding:.4em .6em;border-radius:8px;background:#f8f8f8;}</style><style id=\"loading\">.loadingclass{display:inline-block;cursor:default;background: url('http://ueditor.baidu.com/ueditor/themes/default/images/loading.gif') no-repeat center center transparent;border:1px solid #cccccc;margin-left:1px;height: 22px;width: 22px;}\n" +
			".loaderrorclass{display:inline-block;cursor:default;background: url('http://ueditor.baidu.com/ueditor/themes/default/images/loaderror.png') no-repeat center center transparent;border:1px solid #cccccc;margin-right:1px;height: 22px;width: 22px;}</style><style id=\"anchor\">.anchorclass{background: url('http://ueditor.baidu.com/ueditor/themes/default/images/anchor.gif') no-repeat scroll left center transparent;cursor: auto;display: inline-block;height: 16px;width: 15px;}</style></head>"+

			"<body class=\"view\" contenteditable=\"true\" spellcheck=\"false\" style=\"overflow-y: hidden; cursor: text; height: 8556px;\"><p><br></p>" +
//			"<section class=\"\" style=\"margin: 10px auto; width: 92%; box-sizing: border-box; text-align: center;\" data-width=\"92%\"><p style=\"line-height: 1.5em; text-align: center; white-space: normal;\"><span style=\"color:#595959\"><span style=\"font-size: 14px;\">\"</span></span></p><p style=\"font-size: 16px; line-height: 1.5em; text-align: justify; white-space: normal;\"><span style=\"font-size: 14px; color: #595959;\">年少谈恋爱的时候，我们都衣食无忧。那时美棠便同我讲，情愿两人在乡间找一处僻静地方，有一片自己的园地，布衣蔬食以为乐。当时或只是少年人的浪漫。那时候我们也不知道田园牧歌里的旧中国已经走到了她的尽头，只以为我们可以像《浮生六记》里那样“买绕屋菜园十亩，课仆妪，植瓜蔬……布衣菜饭可乐终身，不必作远游计也。”</span></p><p style=\"font-size: 16px; line-height: 1.5em; text-align: justify; white-space: normal;\"><span style=\"font-size: 14px; color: #595959;\"><br></span></p><p style=\"font-size: 16px; line-height: 1.5em; text-align: justify; white-space: normal;\"><span style=\"font-size: 14px; color: #595959;\">人到中年，分隔两地，家计维艰。她又嘱我一定当心身体不要落下什么病痛，等孩子们独立了她要一个人来安徽陪我住，“我们身体好，没病痛，老了大家一块出去走走，看看电影，买点吃吃，多好。”她原是那样天真爱玩却也要求不多的一个人，两个人能清平安乐地在一起就是她操劳奔忙几十年里的寄望。</span></p><p style=\"font-size: 16px; line-height: 1.5em; text-align: justify; white-space: normal;\"><span style=\"font-size: 14px; color: #595959;\"><br></span></p><p style=\"font-size: 16px; line-height: 1.5em; text-align: justify; white-space: normal;\"><span style=\"font-size: 14px; color: #595959;\"></span></p><p style=\"font-size: 16px; white-space: normal; font-family: 微软雅黑; line-height: 25.6px; text-align: justify;\"><span style=\"font-size: 14px; color: rgb(89, 89, 89);\"><img data-ratio=\"0.9357142857142857\" data-s=\"300,640\" data-src=\"http://mmbiz.qpic.cn/mmbiz/6H2fic5Al1ekYpXNBdBlfEI8xTZqAVnCFicl2WVpY4DMJJIWxobn86RB5rG3jZDkVCXk2EphpOxHiaSh38HicxgfBw/640?\" data-w=\"560\" style=\"color: rgb(62, 62, 62); font-size: 13px; line-height: 25.5938px; white-space: pre-wrap; border: 0px; box-sizing: border-box !important; word-wrap: break-word !important; visibility: visible !important; width: auto !important; height: auto !important;\" width=\"auto\" _width=\"auto\" class=\"\" src=\"http://mmbiz.qpic.cn/mmbiz/6H2fic5Al1ekYpXNBdBlfEI8xTZqAVnCFicl2WVpY4DMJJIWxobn86RB5rG3jZDkVCXk2EphpOxHiaSh38HicxgfBw/640?tp=webp&amp;wxfrom=5&amp;wx_lazy=1\" data-fail=\"0\"></span></p><p style=\"font-size: 16px; white-space: normal; font-family: 微软雅黑; line-height: 25.6px; text-align: justify;\"><span style=\"color: rgb(178, 178, 178);\"><span style=\"font-size: 12px; letter-spacing: 3px; line-height: 25.5938px;\">「</span><span style=\"font-size: 11px; white-space: pre-wrap; line-height: 1.6;\">我第一次看见美棠。</span><span style=\"white-space: pre-wrap; font-size: 11px;\">1946年，那年我26岁，从黄埔军校毕业，在100军六十三师一八八团迫击炮连二排，父亲来信希望我借着假期回家订亲。我们两家是世交，当我们走至厅堂时我忽见左面正房窗门正开着，有个年约20岁，面容姣好的女子正在揽镜自照涂抹口红——这是我第一次看见美棠的印象。</span><span style=\"font-size: 12px; letter-spacing: 3px; line-height: 25.5938px;\">」</span></span></p><p style=\"font-size: 16px; line-height: 1.5em; text-align: justify; white-space: normal;\"><br></p><p style=\"font-size: 16px; line-height: 1.5em; text-align: justify; white-space: normal;\"><span style=\"font-size: 14px; color: #595959;\">渐至晚景，生活终于安定。我得上天眷顾，虽曾两度急病手术，但恢复良好，身长康健。美棠自己却落下病痛，多年为肾病所累，食多忌口，行动亦不便。她对生活那样简单的想往，竟终不得实现，他生未卜此生休，徒叹奈何奈何。</span></p><p style=\"font-size: 16px; line-height: 1.5em; text-align: justify; white-space: normal;\"><span style=\"font-size: 14px; color: #595959;\"><br></span></p><p style=\"font-size: 16px; line-height: 1.5em; text-align: justify; white-space: normal;\"><span style=\"font-size: 14px; color: #595959;\">二〇〇八年三月二十三日，美棠的追悼会在龙华殡仪馆举行，我挽她：</span></p><p style=\"font-size: 16px; line-height: 1.5em; text-align: justify; white-space: normal;\"><span style=\"font-size: 14px; color: #595959;\"><br></span></p><p style=\"font-size: 16px; line-height: 1.5em; text-align: justify; white-space: normal;\"><span style=\"font-size: 14px; color: #0C0C0C;\">坎坷岁月费操持，渐入平康，奈何天不假年，恸今朝，君竟归去；</span></p><p style=\"font-size: 16px; line-height: 1.5em; text-align: justify; white-space: normal;\"><span style=\"font-size: 14px; color: #0C0C0C;\">沧桑世事谁能料？阅尽荣枯，从此红尘看破，盼来世，再续姻缘。</span></p><p style=\"font-size: 16px; text-align: center; white-space: normal;\"><span style=\"font-size: 14px; color: #595959;\">\"</span></p><p style=\"font-size: 16px;\"><img data-s=\"300,640\" data-type=\"jpeg\" data-src=\"http://mmbiz.qpic.cn/mmbiz_jpg/RKcXYhkpRaOgYc9iayiajNeibnsxuvpzicvT0j2CRAGoOf7PAaDib3TzHuK6PXdcic4Zy392UxS0GQaIPSPNb7Gx88UA/0?wx_fmt=jpeg\" data-ratio=\"1.1733333333333333\" data-w=\"300\" class=\"\" src=\"http://mmbiz.qpic.cn/mmbiz_jpg/RKcXYhkpRaOgYc9iayiajNeibnsxuvpzicvT0j2CRAGoOf7PAaDib3TzHuK6PXdcic4Zy392UxS0GQaIPSPNb7Gx88UA/640?wx_fmt=jpeg&amp;tp=webp&amp;wxfrom=5&amp;wx_lazy=1\" style=\"width: auto !important; height: auto !important; visibility: visible !important;\" data-fail=\"0\"></p><p style=\"font-size: 16px;\"><br></p><p style=\"font-size: 16px;\"><span style=\"font-family: Arial, Helvetica, sans-serif; line-height: normal; font-size: 16px;\">\uD83D\uDD16</span></p></section>" +
			"  <p style=\"text-align:center;\">\n" +
			"                <img src=\"http://mmbiz.qpic.cn/mmbiz_jpg/RKcXYhkpRaPMUrqmXRSbqdZ1JHkKU2iaeYSZLXNkUI5GPiaYgs0al9hzGUfTWanKerhpSHDV5461N1DZVQYczhzw/640?wx_fmt=jpeg&tp=webp&wxfrom=5&wx_lazy=1\" class=\"\"/><br/>\n" +
			"            </p>\n" +
			"        </section>\n" +
			"        <p style=\"margin: 0px; text-align: center; line-height: 1.5em;\">\n" +
			"            <span style=\"margin: 0px; font-family: STKaiti; font-size: 14px;\"></span>\n" +
			"        </p>\n" +
			"        <p style=\"line-height: 25.6px; white-space: normal; text-align: center;\">\n" +
			"            <font face=\"STKaiti\"><span style=\"font-size: 14px; font-family: 黑体, SimHei;\">第三代</span></font>\n" +
			"        </p>\n" +
			"        <p style=\"text-align: left;\">\n" +
			"            <br/>\n" +
			"        </p>\n" +
			"        <p>\n" +
			"            <br/>\n" +
			"        </p>"+
			"<p><br></p><p><br></p></body>" +

			"</html>";
}
