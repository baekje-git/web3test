package ever.support.cmmn;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class XMLGenerator {
	private HashMap objDataType;

	StringBuffer sXMLStr = new StringBuffer();

	String sTopElementStr = "";

	public XMLGenerator() {
		this.objDataType = new HashMap<>();
		this.objDataType.put("String", new Integer(1));
		this.objDataType.put("Integer", new Integer(1));
		this.objDataType.put("BigDecimal", new Integer(1));
		this.objDataType.put("Long", new Integer(1));
		this.objDataType.put("Double", new Integer(1));
		this.objDataType.put("Timestamp", new Integer(1));
		this.objDataType.put("Map", new Integer(2));
		this.objDataType.put("HashMap", new Integer(2));
		this.objDataType.put("LinkedCaseInsensitiveMap", new Integer(2));
		this.objDataType.put("Collection", new Integer(3));
		this.objDataType.put("List", new Integer(4));
		this.objDataType.put("ArrayList", new Integer(4));
		this.objDataType.put("ListOrderedMap", new Integer(5));
	}

	public String nullToStr(String str) {
		if (str == null)
			return "";
		if (str == "null")
			return "";
		return str;
	}

	public Object nullToStr(Object obj) {
		if (obj == null)
			return "";
		return obj;
	}

	public String replaceHTMLString(String sStr) {
		String sStrTmp = sStr;
		if (sStr == null)
			return sStr;
		sStrTmp = sStrTmp.replace("&", "&amp;");
		sStrTmp = sStrTmp.replace("<", "&lt;");
		sStrTmp = sStrTmp.replace(">", "&gt;");
		sStrTmp = sStrTmp.replace("\"", "&quot;");
		sStrTmp = sStrTmp.replace("\"", "&quot;");
		return sStrTmp;
	}

	public String genXMLtoStringData(String sMapName, String sValue) {
		sMapName = sMapName.toLowerCase();
		try {
			if (nullToStr(sValue).trim().equals(""))
				return "<" + sMapName + "/>\n";
			return "<" + sMapName + ">" + replaceHTMLString(nullToStr(String.valueOf(sValue))) + "</" + sMapName
					+ ">\n";
		} catch (Exception e) {
			System.out.println("Gen XML to String Error( " + sMapName + " ): " + e.toString());
			return "";
		}
	}

	public String genXMLtoMapData(String sMapName, HashMap sMap) throws Exception {
		StringBuffer sTmpXml = new StringBuffer();
		try {
			sTmpXml.append("<" + sMapName + ">\n");
			String sObjSimpleName = "";
			String sElementStr = "";
			String sGetElement = "";
			Object getObj = null;
			Iterator it = null;
			if (sMap != null) {
				it = sMap.keySet().iterator();
				while (it.hasNext()) {
					sElementStr = it.next().toString();
					sGetElement = sElementStr;
					getObj = sMap.get(sElementStr);
					if (getObj != null) {
						sObjSimpleName = getObj.getClass().getSimpleName();
						switch (Integer.parseInt(this.objDataType.get(sObjSimpleName).toString())) {
						case 1:
							sTmpXml.append(
									genXMLtoStringData(sElementStr, nullToStr(sMap.get(sGetElement)).toString()));
							break;
						case 2:
							sTmpXml.append(genXMLtoMapData(sElementStr, (HashMap) sMap.get(sGetElement)));
							break;
						case 3:
							sTmpXml.append(genXMLtoCollectionData(sElementStr, sElementStr,
									(Collection) sMap.get(sGetElement)));
							break;
						case 4:
							sTmpXml.append(genListData(sElementStr, sElementStr, (List) sMap.get(sGetElement)));
							break;
						case 5:
							break;
						}
					}
				}
			}
			sTmpXml.append("</" + sMapName + ">\n");
		} catch (Exception e) {
			throw e;
		}
		return sTmpXml.toString();
	}

	public String genXMLtoCollectionData(String sMapName, String topName, Collection col) throws Exception {
		StringBuffer sTmpXml = new StringBuffer();
		this.sTopElementStr = topName;
		try {
			sTmpXml.append("<" + sMapName + ">\n");
			String sObjSimpleName = "";
			Object getObj = null;
			Iterator it = null;
			if (col != null) {
				it = col.iterator();
				while (it.hasNext()) {
					getObj = it.next();
					if (getObj != null) {
						sObjSimpleName = getObj.getClass().getSimpleName();
						switch (Integer.parseInt(this.objDataType.get(sObjSimpleName).toString())) {
						case 1:
							sTmpXml.append(genXMLtoStringData(this.sTopElementStr, getObj.toString()));
							break;
						case 2:
							sTmpXml.append(genXMLtoMapData(this.sTopElementStr, (HashMap) getObj));
							break;
						case 3:
							sTmpXml.append(genXMLtoCollectionData(this.sTopElementStr, this.sTopElementStr,
									(Collection) getObj));
							break;
						case 4:
							sTmpXml.append(genListData(this.sTopElementStr, this.sTopElementStr, (List) getObj));
							break;
						case 5:
							break;
						}
					}
				}
			}
			sTmpXml.append("</" + sMapName + ">");
		} catch (Exception e) {
			throw e;
		}
		return sTmpXml.toString();
	}

	public String genListData(String sMapName, String topName, List arList) throws Exception {
		StringBuffer sTmpXml = new StringBuffer();
		this.sTopElementStr = topName;
		try {
			sTmpXml.append("<" + sMapName + ">\n");
			String sObjSimpleName = "";
			Object getObj = null;
			if (arList != null)
				for (int i = 0; i < arList.size(); i++) {
					getObj = arList.get(i);
					if (getObj != null) {
						sObjSimpleName = getObj.getClass().getSimpleName();
						switch (Integer.parseInt(this.objDataType.get(sObjSimpleName).toString())) {
						case 1:
							sTmpXml.append(genXMLtoStringData(this.sTopElementStr, getObj.toString()));
							break;
						case 2:
							sTmpXml.append(genXMLtoMapData(this.sTopElementStr, (HashMap) getObj));
							break;
						case 3:
							sTmpXml.append(genXMLtoCollectionData(this.sTopElementStr, this.sTopElementStr,
									(Collection) getObj));
							break;
						case 4:
							sTmpXml.append(genListData(this.sTopElementStr, this.sTopElementStr, (List) getObj));
							break;
						}
					}
				}
			sTmpXml.append("</" + sMapName + ">\n");
		} catch (Exception e) {
			throw e;
		}
		return sTmpXml.toString();
	}
}
