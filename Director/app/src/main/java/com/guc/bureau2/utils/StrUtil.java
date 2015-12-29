package com.guc.bureau2.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import android.content.Context;

public class StrUtil {

	public static String getStrForId(Context context, Integer id) {

		return context.getText(id).toString();
	}
	public static boolean checkPwd(String password){
		String reg="^[a-zA-Z0-9]+$";
		return startCheck(reg, password);
	}

	/**
	 * ���Ϊ�ղ����ضԾ��ַ���
	 * 
	 * @deprecated Use {@link #getValue(String)} instead
	 */
	public static String getStr(String str) {
		return getValue(str);
	}

	/**
	 * �����ַ��������ظ��ַ�����ֵ��ȥ�����ߵĿո�����ַ���Ϊnull,"","null"��������""��
	 * 
	 * @param str
	 * @return
	 */
	public static String getValue(String str) {

		return str == null || str.equals("null") || str.equals("") ? "" : str
				.trim();
	}
	public static String getStringById(Context context,int id) {

		return context.getResources().getString(id);
	}
	// �ж��ַ�����ֵ�Ƿ�Ϊ��
	/**
	 * @deprecated Use {@link #IsNullValue(String)} instead
	 */
	public static boolean checkStrTrimOrNull(String str) {
		return IsNullValue(str);
	}

	/**
	 * �ж��ַ�����ֵ�Ƿ�Ϊ�գ�����null,"","null","NULL"�������Щ��������true,��֮�򷵻�false
	 * 
	 * @param str
	 * @return
	 */
	public static boolean IsNullValue(String str) {
		boolean result = true;
		if (str != null && !"".equals(str) && !"null".equals(str)
				&& !"NULL".equals(str)) {
			result = false;
		}
		return result;
	}

	/**
	 * ����Ƿ�Ϊ�ֻ�����
	 * 
	 * @param phone
	 * @return
	 */
	public static boolean checkMobilePhone(String phone) {
		if (phone != null && !"".equals(phone) && !phone.equals("null")
				&& phone.matches("[1]{1}[3,5,8,6,7]{1}[0-9]{9}")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * �÷������ڴ�����������ɵ��ַ�����Ҫ����С��ʱ�����С��λ������2��������2λ ����������
	 * 
	 * @param args
	 */
	public static String split(String params) {

		String temp = "";

		if (params == null || params.equals("null") || params.equals("NULL")
				|| params.equals("")) {
			temp = "";
			return temp;
		}
		if (params != null && params.indexOf(".") != -1) {

			int i = params.indexOf(".");
			int len = params.length();

			if (i + 3 < len) {
				temp = params.substring(0, params.indexOf(".") + 3);
			} else {
				temp = params;
			}

		} else {
			temp = params;
		}
		return temp;
	}

	public static boolean regexChkPhoneNum(String telnumber) {
		boolean checkFlag = false;
		// �ֻ�������֤,11λ����֪����ϸ���ֻ�����Σ�ֻ����֤��ͷ������1��λ��
		if (checkCellPhone(telnumber)) {
			checkFlag = true;
		} else if (checkPhoneNr(telnumber)) {// ��֤���ڵ绰����
												// ��ʽ��010-67676767�����ų���3-4λ��������"0"��ͷ��������7-8λ
			checkFlag = true;
		} else if (checkPhoneNrWithoutCode(telnumber)) {// ��֤���ڵ绰���� ��ʽ��6767676,
														// ����λ��������7-8λ,ͷһλ������"0"
			checkFlag = true;
		} else if (checkPhoneNrWithoutLine(telnumber)) {// ��֤���ڵ绰����
														// ��ʽ��0106767676����11λ����12λ��������0��ͷ
			checkFlag = true;
		}

		System.out.println("-->" + telnumber + " is phonen?<>" + checkFlag);

		return checkFlag;

	}

	public boolean checkPhone(String phone) {
		if (phone.matches("\\d{4}-\\d{8}|\\d{4}-\\d{7}|\\d(3)-\\d(8)")) {
			return true;
		} else if (phone.matches("^[1][3,5]+\\d{9}")) {

			return true;
		} else {
			return false;
		}
	}

	public static boolean checkFixedPhone(String phone) {
		if (phone.matches("\\d{4}-\\d{8}|\\d{4}-\\d{7}|\\d(3)-\\d(8)")) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean startCheck(String reg, String string) {
		boolean tem = false;

		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(string);

		tem = matcher.matches();
		return tem;
	}

	/**
	 * ��������,����������������������0��������������-0��ͷ, ������������0��ͷ
	 * 
	 * */
	public boolean checkNr(String nr) {
		String reg = "^(-?)[1-9]+\\d*|0";
		return startCheck(reg, nr);
	}

	/**
	 * �ֻ�������֤,11λ����֪����ϸ���ֻ�����Σ�ֻ����֤��ͷ������1��λ��
	 * */
	public static boolean checkCellPhone(String cellPhoneNr) {

		String reg = "^[1][\\d]{10}";
		return startCheck(reg, cellPhoneNr);
	}

	/**
	 * ��֤���ڵ绰���� ��ʽ��010-67676767�����ų���3-4λ��������"0"��ͷ��������7-8λ
	 * */
	public static boolean checkPhoneNr(String phoneNr) {
		String regex = "^[0]\\d{2,3}\\-\\d{7,8}";

		return startCheck(regex, phoneNr);
	}

	/**
	 * ��֤���ڵ绰���� ��ʽ��6767676, ����λ��������7-8λ,ͷһλ������"0"
	 * */
	public static boolean checkPhoneNrWithoutCode(String phoneNr) {
		String reg = "^[1-9]\\d{6,7}";

		return startCheck(reg, phoneNr);
	}

	/**
	 * ��֤���ڵ绰���� ��ʽ��0106767676����11λ����12λ��������0��ͷ
	 * */
	public static boolean checkPhoneNrWithoutLine(String phoneNr) {
		String reg = "^[0]\\d{10,11}";

		return startCheck(reg, phoneNr);
	}

	/**
	 * ����հ׷�
	 * */
	public boolean checkWhiteLine(String line) {
		String regex = "(\\s|\\t|\\r)+";

		return startCheck(regex, line);
	}

	/**
	 * ���EMAIL��ַ �û�������վ���Ʊ���>=1λ�ַ�
	 * ��ַ��β��������com|cn|com|cn|net|org|gov|gov.cn|edu|edu.cn��β
	 * */
	public boolean checkEmailWithSuffix(String email) {
		String regex = "\\w+\\@\\w+\\.(com|cn|com.cn|net|org|gov|gov.cn|edu|edu.cn)";

		return startCheck(regex, email);
	}

	/**
	 * ���EMAIL��ַ �û�������վ���Ʊ���>=1λ�ַ� ��ַ��β������2λ���ϣ��磺cn,test,com,info
	 * */
	public boolean checkEmail(String email) {
		String regex = "\\w+\\@\\w+\\.\\w{2,}";

		return startCheck(regex, email);
	}

	/**
	 * �����������(�й�),6λ����һλ�����Ƿ�0��ͷ������5λ����Ϊ0-9
	 * */
	public boolean checkPostcode(String postCode) {
		String regex = "^[1-9]\\d{5}";
		return startCheck(regex, postCode);
	}

	/**
	 * �����û��� ȡֵ��ΧΪa-z,A-Z,0-9,"_",���֣�������"_"��β �û�������С���Ⱥ���󳤶����ƣ������û���������4-20λ
	 * */
	public boolean checkUsername(String username, int min, int max) {
		String regex = "[\\w\u4e00-\u9fa5]{" + min + "," + max + "}(?<!_)";
		return startCheck(regex, username);
	}

	/**
	 * �����û��� ȡֵ��ΧΪa-z,A-Z,0-9,"_",���֣�������"_"��β ����Сλ�����Ƶ��û��������磺�û�������Ϊ4λ�ַ�
	 * */
	public boolean checkUsername(String username, int min) {
		// [\\w\u4e00-\u9fa5]{2,}?
		String regex = "[\\w\u4e00-\u9fa5]{" + min + ",}(?<!_)";

		return startCheck(regex, username);
	}

	/**
	 * �����û��� ȡֵ��ΧΪa-z,A-Z,0-9,"_",���� ����һλ�ַ�������ַ�λ�������ƣ�������"_"��β
	 * */
	public boolean checkUsername(String username) {
		String regex = "[\\w\u4e00-\u9fa5]+(?<!_)";
		return startCheck(regex, username);
	}

	/**
	 * �鿴IP��ַ�Ƿ�Ϸ�
	 * */
	public boolean checkIP(String ipAddress) {
		String regex = "(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\."
				+ "(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\."
				+ "(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\."
				+ "(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])";

		return startCheck(regex, ipAddress);
	}

	/**
	 * ��֤�������֤���룺15��18λ����������ɣ�������0��ͷ
	 * */
	public boolean checkIdCard(String idNr) {
		String reg = "^[1-9](\\d{14}|\\d{17})";

		return startCheck(reg, idNr);
	}

	/**
	 * ��ַ��֤<br>
	 * �������ͣ�<br>
	 * http://www.test.com<br>
	 * http://163.com
	 * */
	public boolean checkWebSite(String url) {
		// http://www.163.com
		String reg = "^(http)\\://(\\w+\\.\\w+\\.\\w+|\\w+\\.\\w+)";

		return startCheck(reg, url);
	}

}
