import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;

import com.mockuai.deliverycenter.core.domain.express.Express;

public class GenIbatis {
	private static Express entity = new Express();

	private static String getClassName(String pkgName) {
		return pkgName.substring(pkgName.lastIndexOf(".") + 1);
	}

	private static String toSqlField(String pojo) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < pojo.length(); i++) {
			if (Character.isUpperCase(pojo.charAt(i))) {
				sb.append("_" + Character.toLowerCase(pojo.charAt(i)));
			} else {
				sb.append(pojo.charAt(i));
			}
		}
		return sb.toString();
	}

	private static String getWhere(String pre) {
		StringBuilder sb = new StringBuilder();
		Field[] fields = entity.getClass().getDeclaredFields();
		sb.append("1=1 and deleted=0");
		sb.append("\n");
		sb.append(pre);
		for (int i = 0; i < fields.length; i++) {
			if (i != 0)
				sb.append(pre);
			if ("deleted".equals(fields[i].getName())) {
				continue;
			} else {
				sb.append("<isNotNull prepend=\"AND\" property=\""
						+ fields[i].getName() + "\"> ");
				sb.append("\n");
				sb.append(pre);
				sb.append("	" + toSqlField(fields[i].getName()) + " = #"
						+ fields[i].getName() + ":VARCHAR#");
				sb.append("\n");
				sb.append(pre);
				sb.append("</isNotNull>");
			}
			if (i != fields.length - 1)
				sb.append("\n");
		}
		return sb.toString();
	}

	private static String getUpdate(String pre) {
		StringBuilder sb = new StringBuilder();
		Field[] fields = entity.getClass().getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			if (i != 0)
				sb.append(pre);
			sb.append("<isNotNull prepend=\",\" property=\""
					+ fields[i].getName() + "\"> ");
			sb.append("\n");
			sb.append(pre);
			sb.append("	" + toSqlField(fields[i].getName()) + " = #"
					+ fields[i].getName() + ":VARCHAR#");
			sb.append("\n");
			sb.append(pre);
			sb.append("</isNotNull>");
			if (i != fields.length - 1)
				sb.append("\n");
		}
		return sb.toString();
	}

	private static String getList(String pre) {
		StringBuilder sb = new StringBuilder();
		Field[] fields = entity.getClass().getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			if (i != 0)
				sb.append(pre);
			sb.append(toSqlField(fields[i].getName()) + " as "
					+ fields[i].getName() + ",");
			if (i != fields.length - 1)
				sb.append("\n");
		}
		return sb.toString().substring(0, sb.toString().length() - 1);
	}

	private static String getFieldList(String pre) {
		StringBuilder sb = new StringBuilder();
		Field[] fields = entity.getClass().getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			if (i != 0)
				sb.append(pre);

			sb.append("<isNotNull prepend=\",\" property=\""
					+ fields[i].getName() + "\">"
					+ toSqlField(fields[i].getName()) + " </isNotNull>");

			if (i != fields.length - 1)
				sb.append("\n");
		}
		sb.append("\n");
		sb.append(pre);
		sb.append(",deleted,gmt_created,gmt_modified");
		return sb.toString();
	}

	private static String getValueList(String pre) {
		StringBuilder sb = new StringBuilder();
		Field[] fields = entity.getClass().getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			if (i != 0)
				sb.append(pre);

			sb.append("<isNotNull prepend=\",\" property=\""
					+ fields[i].getName() + "\">#" + fields[i].getName()
					+ ":VARCHAR#</isNotNull>");

			if (i != fields.length - 1)
				sb.append("\n");
		}
		sb.append("\n");
		sb.append(pre);
		sb.append(",0,now(),now()");
		return sb.toString();
	}

	public static void main(String[] args) throws Exception {
		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		FileOutputStream fos = null;
		OutputStreamWriter osw = null;
		BufferedWriter bw = null;
		try {
			fis = new FileInputStream("f:\\codetemplate\\ibatis-template.xml");
			isr = new InputStreamReader(fis, "utf-8");
			br = new BufferedReader(isr);
			String line = "";
			StringBuilder sb = new StringBuilder();
			while ((line = br.readLine()) != null) {
				if (line.contains("$namespace")) {
					sb.append(line.replace("$namespace",
							getClassName(entity.getClass().getName())
									.toUpperCase()));
					sb.append("\n");
				} else if (line.contains("$packageName")) {
					sb.append(line.replace("$packageName",
							entity.getClass().getName()).replace("$entityName",
							getClassName(entity.getClass().getName())));
					sb.append("\n");
				} else if (line.contains("$entityName")) {
					sb.append(line.replace("$entityName", getClassName(entity
							.getClass().getName())));
					sb.append("\n");
				} else if (line.contains("$where")) {
					sb.append(line.replace("$where",
							getWhere(line.replace("$where", ""))));
					sb.append("\n");
				} else if (line.contains("$list")) {
					sb.append(line.replace("$list",
							getList(line.replace("$list", ""))));
					sb.append("\n");
				} else if (line.contains("$tableName")) {
					sb.append(line.replace("$tableName", "t"
							+ toSqlField(getClassName(entity.getClass()
									.getName()))));
					sb.append("\n");
				} else if (line.contains("$fieldNameList")) {
					sb.append(line.replace("$fieldNameList",
							getFieldList(line.replace("$fieldNameList", ""))));
					sb.append("\n");
				} else if (line.contains("$fieldValueList")) {
					sb.append(line.replace("$fieldValueList",
							getValueList(line.replace("$fieldValueList", ""))));
					sb.append("\n");
				} else if (line.contains("$update")) {
					sb.append(line.replace("$update",
							getUpdate(line.replace("$update", ""))));
					sb.append("\n");
				} else {
					sb.append(line);
					sb.append("\n");
				}
			}

			fos = new FileOutputStream("f:\\codetemplate\\ibatis-"
					+ getClassName(entity.getClass().getName()) + ".xml");
			osw = new OutputStreamWriter(fos, "utf-8");
			bw = new BufferedWriter(osw);
			bw.write(sb.toString());
			bw.flush();
			System.out.println("generate success");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			fis.close();
			isr.close();
			br.close();
			fos.close();
			osw.close();
			bw.close();
		}
	}
}
