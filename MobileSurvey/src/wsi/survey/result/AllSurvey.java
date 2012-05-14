package wsi.survey.result;

import wsi.survey.R;


public class AllSurvey {

	public static String IMEI = "000000000000000";
	public static int deviceScreenWidth = 480;
	public static int deviceScreenHeight = 800;
	public static int titleFontSize = 24;
	
	public final static String surveyFileFolder = "surveyfiles";		// 问卷文件夹，注意：文件名称必须与下面二维数组完全一致（特别是字母大小写）
	public final static int[] imgs = { R.drawable.img01, R.drawable.img02, R.drawable.img03, R.drawable.img04, R.drawable.img05, R.drawable.img06, R.drawable.img07 };
	public final static String[][] surveyFiles = { 
									   { "SDS.xml", "自评抑郁（SDS）", "自评抑郁量表（Self-Rating Depression Scale, SDS），由William W.K.Zung于1965年编制的，为自评量表，用于衡量抑郁状态的轻重程度及其在治疗中的变化。" },
									   { "PWB.xml", "心理幸福感（PWB）", "心理幸福感(PWB, Psychological Well-being) 欢迎您填写本量表，本量表是针对微博使用而设计的。通过填写本量表，您可以评测您的心理幸福感。" } ,
									   { "SAS.xml", "焦虑自评（SAS）", "焦虑自评量表（Self-Rating Anxiety Scale, SAS），由Zung于1971年编制，分为四级评分的自评量表，用于评出焦虑病人的主观感受。" } ,
									   { "16PF.xml", "十六项人格测试（16PF）", "卡特尔十六项人格测试（16PF），本测验包括一些有关个人兴趣与态度的问题。每个人都有自己的看法，对问题的回答自然不同。无所谓正确或错误。请你尽量表达自己的意见。" } ,
									   { "BFI.xml", "大五人格（BFI）", "大五人格量表（The Big Five Inventory, BFI）" } ,
									   { "SCL-90.xml", "症状自评（SCL-90）", "症状自评量表（SCL-90）" } 
									   };

}
