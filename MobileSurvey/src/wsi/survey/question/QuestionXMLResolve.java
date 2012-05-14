package wsi.survey.question;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class QuestionXMLResolve {

	public static void XML2QuestionNaire(QuestionNaire qn, String fileContent) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = null;
		
		try {
			db = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		Document doc = null;
		try {
			doc = db.parse(new ByteArrayInputStream(fileContent.getBytes("UTF-8")));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		doc.getDocumentElement().normalize();	// 格式化doc
		getQuestionNaire(qn, doc);
	}

	public static void getQuestionNaire(QuestionNaire qn, Document doc) {
		
		NodeList root = doc.getElementsByTagName("questionnaire");					// questionnaire
		Element e = (Element) root.item(0);
		
		NodeList nodeList, nodeList2;
		Element element;
		
		nodeList = ((org.w3c.dom.Element) e).getElementsByTagName("description");		// description
		element = (Element) nodeList.item(0);
		if (element != null) {
			nodeList2 = element.getElementsByTagName("caption");
			Node firstNode = nodeList2.item(0).getFirstChild();
			String caption = firstNode.getNodeValue();
			qn.setCaption(caption);
			
			nodeList2 = element.getElementsByTagName("information");
			firstNode = nodeList2.item(0).getFirstChild();
			String info = firstNode.getNodeValue();
			qn.setInfo(info);
			
//			nodeList2 = element.getElementsByTagName("questionsnum");
//			firstNode = nodeList2.item(0).getFirstChild();
//			String questionsnum = firstNode.getNodeValue();
//			qn.setQuestionsNum(questionsnum);

			nodeList2 = element.getElementsByTagName("interfacetype");
			firstNode = nodeList2.item(0).getFirstChild();
			String interfacetype = firstNode.getNodeValue();
			qn.setInterfaceType(interfacetype);
		}

		
		nodeList = ((org.w3c.dom.Element)e).getElementsByTagName("tags");			// tags
		element = (Element)nodeList.item(0);
		if(element != null) {		// 如果xml问卷中有tags，则提取
			nodeList2 = element.getElementsByTagName("tag");
			int len = nodeList2.getLength();
			for(int i=0; i<len; i++) {
				Node tagNode = nodeList2.item(i);
				
				org.w3c.dom.Element tagElement = (org.w3c.dom.Element)tagNode;
				String tagName = tagElement.getAttribute("name");
				String tagLabel = tagElement.getAttribute("label");
				qn.addTags(tagName, tagLabel);
			}
		}
		qn.addTags("00_all", "全部维度");		// 添加一个默认的全部（all），显示全部tags

		
		nodeList = ((org.w3c.dom.Element)e).getElementsByTagName("remarks");			// remarks
		element = (Element)nodeList.item(0);
		if(element != null) {		// 如果xml问卷中有tags，则提取
			nodeList2 = element.getElementsByTagName("remark");
			int len = nodeList2.getLength();
			for(int i=0; i<len; i++) {
				Node tagNode = nodeList2.item(i);
				
				org.w3c.dom.Element tagElement = (org.w3c.dom.Element)tagNode;
				String tagName = tagElement.getAttribute("Tag");
				if(tagName == null || tagName == "") {
					tagName = "default_tag";
				}
				
				String tagScore = tagElement.getAttribute("Tag_for_score");
				String tagRemark = tagElement.getChildNodes().item(0).getNodeValue();
				
				qn.addRemarks(tagName, tagScore, tagRemark);
			}
		}
		qn.addTags("00_all", "全部维度");		// 添加一个默认的全部（all），显示全部tags
		
		
		nodeList = ((org.w3c.dom.Element) e).getElementsByTagName("questions");				//questions
		element = (Element)nodeList.item(0);
		if(nodeList != null) {
			nodeList2 = element.getElementsByTagName("question");
			
			int questionsNum = nodeList2.getLength();
			qn.setQuestionsNum(questionsNum);			// 设置问卷试题数
			
			for (int i = 0; i < questionsNum; i++) {
				Node questionNode = nodeList2.item(i);
				QuestionItem questionItem = new QuestionItem();
				getQuestionItem(questionItem, questionNode, qn);
				qn.setQuestionItem(i, questionItem);
			}
		}
	}

	public static void getQuestionItem(QuestionItem questionItem, Node questionNode, QuestionNaire qn) {
		if (questionNode.getNodeType() == Node.ELEMENT_NODE) {

			org.w3c.dom.Element questionElement = (org.w3c.dom.Element)questionNode;
			String qid = questionElement.getAttribute("qid");
			questionItem.setQid(qid);
			String tag = questionElement.getAttribute("tag");			
			if(tag != null && tag != "") {
				questionItem.setTag(tag);
			} else {
				questionItem.setTag("all");
				qn.addTags("default_tag", "默认");
			}
			String questionType = questionElement.getAttribute("type");
			questionItem.setQuestionType(questionType);

			NodeList nodeList, nodeList2;
			Element fstNmElmnt;
			
			nodeList = questionElement.getElementsByTagName("title");			// title
			fstNmElmnt = (Element) nodeList.item(0);
			if (fstNmElmnt != null) {
				nodeList2 = ((Node) fstNmElmnt).getChildNodes();
				String title = nodeList2.item(0).getNodeValue();
				questionItem.setTitle(title);
			}

			nodeList = questionElement.getElementsByTagName("answer");			// options[], oidx
			int optionNum = nodeList.getLength();
			questionItem.setOptionsNum(questionType, optionNum);
			for (int i = 0; i < optionNum; i++) {
				Node optionNode = nodeList.item(i);
				OptionItem optionItem = new OptionItem();
				getOptionItem(optionItem, optionNode);
				questionItem.setOptionItem(i, optionItem);
			}
		}
	} 

	public static void getOptionItem(OptionItem optionItem, Node optionNode) {
		if(optionNode.getNodeType() == Node.ELEMENT_NODE) {
			org.w3c.dom.Element optionElement = (org.w3c.dom.Element)optionNode;

			String aid = optionElement.getAttribute("aid");
			optionItem.setAid(aid);
			String score = optionElement.getAttribute("score");
			optionItem.setScore(score);
			String optionType = optionElement.getAttribute("type");
			optionItem.setOptionType(optionType);
			
			Node firstNode = optionNode.getChildNodes().item(0);
			String text = "";
			if(firstNode != null) {
				text = firstNode.getNodeValue();
			}
			optionItem.setText(text);
		}
	}
	
	
	
}
