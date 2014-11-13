package com.project.smsinterception.util.slipt;

/**
 * 停用词处理器
 * 
 * @author phinecos
 * 
 */
public class StopWordsHandler {
	private static String stopWordsList[] = { "的", "我们", "要", "自己", "之", "将",
			"“", "”", "，", "（", "）", "后", "应", "到", "某", "后", "个", "是", "位",
			"呵呵", "哈哈", "你", "了", "着", "还", "呢", "就", "一", "两", "在", "中", "或","哦",
			"有","么","" };// 常用停用词

	public static boolean IsStopWord(String word) {
		for (int i = 0; i < stopWordsList.length; ++i) {
			if (word.equalsIgnoreCase(stopWordsList[i]))
				return true;
		}
		return false;
	}
}
