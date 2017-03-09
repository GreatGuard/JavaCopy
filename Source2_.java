import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.InputStream;
import java.io.InputStreamReader;
public class Source2_ {
    static String PROJECT_NAME = "E:\\KMMicro-Platform\\Src\\trunk";
    static String fromStr = PROJECT_NAME;//C:\Users\keving.yang\Desktop\demo\javacopy
    static String toStr = "E:\\backup";
    static String[] ENCODE_FILES = { "^.*\\.sln$", "^.*\\.csproj$", "^.*\\.cs$", "^.*\\.suo$" };
    static String[] EXCLUDE_FOLDERS = { "^\\.svn$","^bin$","^obj$","^log$","^saveFiles$","^saveFiles$" };
    public static void main(String[] args) throws Exception {
        File fromFile = new File(fromStr);
        copy(fromFile);
    }
    static void copy(File file) throws Exception {
        if (file.isDirectory()) {
            File[] files = getSubFile(file);
            for (File f : files) {
                copy(f);
            }
            if (files.length == 0) {
                String fromAbs = file.getAbsolutePath();
                String toAbs = fromAbs.replace(fromStr, toStr);
                File toFile = new File(toAbs);
                if (!toFile.exists()) {
                    toFile.mkdirs();
                }
            }
        } else {
            String fromAbs = file.getAbsolutePath();
            System.out.println(fromAbs);
            String toAbs = fromAbs.replace(fromStr, toStr);
            boolean isChange = false;
            for (String reg : ENCODE_FILES) {
                if (toAbs.toLowerCase().matches(reg)) {
                    isChange = true;
                    break;
                }
            }
            if (isChange) {
                toAbs = toAbs + "_";
            }
            System.out.println(toAbs);
            File toFile = new File(toAbs);
            if (!toFile.getParentFile().exists()) {
                toFile.getParentFile().mkdirs();
            }
    // "cmd /c copy Test.c Test.txt"
            String cmd1 = "cmd /c copy " + fromAbs + " " + toAbs;
            Runtime runtime = Runtime.getRuntime();
            Process process = runtime.exec(cmd1);
            println(process.getInputStream());
        }
    }
    static void println(InputStream input) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(input, "gbk"));
        String line = null;
        while ((line = br.readLine()) != null) {
            System.out.println(line);
        }
        input.close();
    }
    static File[] getSubFile(File file) {
        return file.listFiles(new FileFilter() {
            public boolean accept(File pathname) {
                boolean ret = true;
                if (pathname.isDirectory()) {
                    for (String reg : EXCLUDE_FOLDERS) {
                        if (pathname.getName().toLowerCase().matches(reg)) {
                            ret = false;
                            break;
                        }
                    }
                }
                return ret;
            }
        });
    }
}