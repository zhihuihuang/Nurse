package com.guc.bureau2.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.guc.bureau2.base.GucBaseAdapter;

public class RegisterOrganizationItemAdapter extends GucBaseAdapter {

	private Context context;
	private List<UnitBean> allItems = new ArrayList<UnitBean>();
	private List<UnitBean> searchItems = new ArrayList<UnitBean>();

	public RegisterOrganizationItemAdapter(Context context, String[] id, String[] name, String[] pinyin) {
		this.context = context;
		for (int i = 0; i < id.length; i++) {
			UnitBean uBean = new UnitBean(id[i], name[i], pinyin[i]);
			uBean.setOrgName(name[i]);
			// System.out.println("name="+uBean.getName()+"--py="+uBean.getPinyin());
			allItems.add(uBean);
		}
		System.out.println("name.length=" + name.length);
		searchItems = allItems;
	}

	public int getCount() {
		return searchItems.size();
	}

	public UnitBean getItem(int arg0) {

		return searchItems.get(arg0);
	}

	public long getItemId(int arg0) {
		return arg0;
	}

	public void searchTextChanged(String search, TextView login_not_unit, ImageView loin_unit_clear) {
		if (search.length() > 0) {
			searchItems = new ArrayList<UnitBean>();
			String oneChar = "";
			for (int i = 0; i < allItems.size(); i++) {
				List<Integer> indexList = new ArrayList<Integer>();
				List<Boolean> existList = new ArrayList<Boolean>();
				boolean flag = false;
				for (int c = 0; c < search.length(); c++) {
					oneChar = search.substring(c, c + 1).toLowerCase();
					flag = false;
					for (int index = 0; index < allItems.get(i).getName().length(); index++) {
						if (allItems.get(i).getName().substring(index, index + 1).equalsIgnoreCase(oneChar)) {
							indexList.add(index);
							flag = true;
						} else if (allItems.get(i).getPinyin().substring(index, index + 1).equalsIgnoreCase(oneChar)) {
							indexList.add(index);
							flag = true;
						}
					}
					if (flag) {
						existList.add(flag);
					}
				}
				// System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>"+indexList);
				// 去掉重复\不按顺序
				if (indexList.size() > 1) {
					for (int mmm = 0; mmm < indexList.size(); mmm++) {
						for (int nn = mmm + 1; nn < indexList.size();) {
							if (indexList.get(mmm) == indexList.get(nn)) {
								indexList.remove(nn);
							} else {
								nn++;
							}
						}
					}
					// System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<"+indexList);

					for (int z = 1; z < indexList.size(); z++) {
						if (indexList.get(0) > indexList.get(z)) {
							// System.out.println("---->"+indexList+"-->"+allItems.get(i).getName()+"-->"+allItems.get(i).getPinyin());
							indexList = new ArrayList<Integer>();
							break;
						}
					}
				}

				// System.out.println("---------------------"+indexList);
				if (indexList.size() > 0 & indexList.size() >= search.length() & existList.size() == search.length()) {
					UnitBean tuBean = new UnitBean(allItems.get(i).getId(), allItems.get(i).getName(), allItems.get(i).getPinyin());
					tuBean.setOrgName(allItems.get(i).getName());
					String tempString = tuBean.getName();

					// 排好序,从后面开始替代
					// System.out.println(">>>>-"+indexList);
					int tt;
					for (int uu = 0; uu < indexList.size(); uu++) {
						for (int jj = uu + 1; jj < indexList.size(); jj++) {
							if (indexList.get(uu) > indexList.get(jj)) {
								tt = indexList.get(uu);
								indexList.set(uu, indexList.get(jj));
								indexList.set(jj, tt);
							}
						}
					}
					// System.out.println(">>>>+"+indexList);
					// ////////////////////
					for (int r = indexList.size() - 1; r >= 0; r--) {
						if (indexList.get(r) == 0) {// 在开头
							tempString = "<font size=\"3\" color=\"red\">" + tempString.substring(0, 1) + "</font>" + tempString.substring(1);
						} else if (indexList.get(r) == allItems.get(i).getName().length() - 1) {// 在未尾
							tempString = tempString.substring(0, indexList.get(r)) + "<font size=\"3\" color=\"red\">" + tempString.substring(indexList.get(r), indexList.get(r) + 1) + "</font>";
						} else {// 在中间
							tempString = tempString.substring(0, indexList.get(r)) + "<font size=\"3\" color=\"red\">" + tempString.substring(indexList.get(r), indexList.get(r) + 1) + "</font>"
									+ tempString.substring(indexList.get(r) + 1, tempString.length());
						}

					}
					// System.out.println("-tempString="+tempString);
					// System.out.println(">>>>"+allItems.get(i).getName()+"--"+allItems.get(i).getPinyin());
					tuBean.setName(tempString);
					searchItems.add(tuBean);
				}
			}
			loin_unit_clear.setVisibility(View.VISIBLE);
		} else {
			searchItems = allItems;
			loin_unit_clear.setVisibility(View.GONE);
		}
		if (searchItems.size() > 0) {
			login_not_unit.setVisibility(View.GONE);
		} else {
			login_not_unit.setVisibility(View.VISIBLE);
		}
	}

	/**
	 * ListView Item设置
	 */
	public View getView(int position, View convertView, ViewGroup parent) {
		return new UnitLayout(position, context);
	}

	class UnitLayout extends LinearLayout {

		public UnitLayout(int position, Context context) {

			super(context);

			LayoutInflater layoutInflater = LayoutInflater.from(context);

			View view = layoutInflater.inflate(android.R.layout.simple_list_item_1, null);

			TextView orgName = (TextView) view.findViewById(android.R.id.text1);

			orgName.setText(Html.fromHtml(getItem(position).getName()));

			// TextView orgName = new TextView(context);
			// orgName.setText(Html.fromHtml(getItem(position).getName()));
			// orgName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
			// orgName.setTextColor(Color.parseColor("#292421"));
			// orgName.setPadding(5, 5, 5, 5);
			LayoutParams p_layout = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			p_layout.gravity = Gravity.CENTER_VERTICAL;
			orgName.setGravity(Gravity.CENTER_VERTICAL);

			this.addView(orgName, p_layout);
		}

	}

	public class UnitBean {
		public String id;
		public String name;
		public String orgName;
		public String pinyin;

		public UnitBean() {

		}

		public UnitBean(String id, String name, String pinyin) {
			this.id = id;
			this.name = name;
			this.pinyin = pinyin;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getPinyin() {
			return pinyin;
		}

		public void setPinyin(String pinyin) {
			this.pinyin = pinyin;
		}

		public String getOrgName() {
			return orgName;
		}

		public void setOrgName(String orgName) {
			this.orgName = orgName;
		}

	}

}