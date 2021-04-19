import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.sql.ResultSet;
import java.util.*;
import java.util.stream.*;
import java.io.FileOutputStream;

public class ReplaceText {
      static ConnectionDb cnt = new ConnectionDb();

    public File replace(Object id, File FileIn) throws Exception {
        Map<String, String> variableMap = fillMap(id);
        String str = FileIn.getPath().replace(".html", "_" + id + ".html");
        File FileOut = new File(str); // change nom
        copierFichier(FileIn, FileOut);

        Path path = Paths.get(FileOut.getPath());

        Stream<String> lines;
        try {
            lines = Files.lines(path, Charset.forName("UTF-8"));
            List<String> replacedLines = lines.map(line -> replaceTag(line, variableMap)).collect(Collectors.toList());

            Files.write(path, replacedLines, Charset.forName("UTF-8"));
            lines.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return FileOut;
    }

    public static Map<String, String> fillMap(Object id) throws Exception {
        Map<String, String> map = new HashMap<String, String>();

        Calendar calendar = Calendar.getInstance();
        java.sql.Date ourJavaDateObject = new java.sql.Date(calendar.getTime().getTime());
        String str = ourJavaDateObject.toString();

        ResultSet rs = new ConnectionDb().getStudent(id);
        if (rs.next()) {
            if (cnt.GetDocType(id).equals("Attestation de scolarité")) {
                map.put("--nom--", rs.getString(2) + " " + rs.getString(3));
                map.put("--CIN--", rs.getString(5));
                map.put("--CNE--", rs.getString(8));
                map.put("--date naissance--", rs.getString(4));
                map.put("--ville--", rs.getString(9));
                map.put("--annee_univ--", str.substring(0, 4));
                map.put("--filière--", rs.getString(7));
                map.put("--niveau étude--", rs.getString(10));
                map.put("--date--", str);
                map.put("--code Apogée--", rs.getString(1));
            }
            else
            {
                map.put("--nom--", rs.getString(2) + " " + rs.getString(3));
                map.put("--année--", str.substring(0, 4));
                map.put("--niveau--", rs.getString(10));
                map.put("--date--", str);
            }
        }
        return map;
    }

    public static void copierFichier(File a, File b) throws IOException {
        InputStream inStream = null;
        OutputStream outStream = null;

        try {
            inStream = new FileInputStream(a);
            outStream = new FileOutputStream(b);

            byte[] buffer = new byte[1024];
            int length;
            // copy the file content in bytes
            while ((length = inStream.read(buffer)) > 0) {
                outStream.write(buffer, 0, length);
            }
            inStream.close();
            outStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    private static String replaceTag(String str, Map<String, String> map) {
        for (String k : map.keySet()) {
            if (str.contains(k)) {
                str = str.replace(k, map.get(k));
            }
        }
        return str;
    }

}