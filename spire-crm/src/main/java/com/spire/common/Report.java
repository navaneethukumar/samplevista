package com.spire.common;


public class Report {

	public static StringBuilder createHeader() {

		StringBuilder sb = new StringBuilder();
		sb.append("<html>");
		sb.append("<head>");
		sb.append("</head>");
		sb.append("<table>");
		sb.append("<th style = \"background: #333; color: white; font-weight: bold; padding: 6px; border: 1px solid #ccc; text-align: left;\"> S.NO");
		sb.append("</th>");
		sb.append("<th style = \"background: #333; color: white; font-weight: bold; padding: 6px; border: 1px solid #ccc; text-align: left;\"> Git URL");
		sb.append("</th>");
		sb.append("<th style = \"background: #333; color: white; font-weight: bold; padding: 6px; border: 1px solid #ccc; text-align: left;\"> Current Branch Name");
		sb.append("</th>");
		sb.append("<th style = \"background: #333; color: white; font-weight: bold; padding: 6px; border: 1px solid #ccc; text-align: left;\"> New Branch Name");
		sb.append("</th>");
		
		return sb;

	}
	
	

	public static StringBuilder createBody(StringBuilder sb) {

							
		return sb;
	}
	
	

	public static StringBuilder closeTable(StringBuilder sb) {

		sb.append("</table>");
		sb.append("</body>");
		sb.append("</html>");

		return sb;
	}
	
	
	public static void main(String[] args) throws Exception{
    	

    }

}
