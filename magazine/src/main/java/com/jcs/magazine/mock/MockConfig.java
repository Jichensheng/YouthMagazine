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

}
