package view.json;

import org.json.simple.JSONObject;

import java.io.*;

/**
 * Created with IntelliJ IDEA.
 * User: asus
 * Date: 12/07/12
 * Time: 07:22
 * To change this template use File | Settings | File Templates.
 */
public class JsonParser {

    public JsonParser() {}

    public void JsonImport(String path) {
        //JSONObject jsonObject = JSONObject.
         try{
             InputStream ips=new FileInputStream(path);
             InputStreamReader ipsr=new InputStreamReader(ips);
             BufferedReader br=new BufferedReader(ipsr);
             //br.r
             br.close();
         }catch (IOException e) {
             e.printStackTrace();
         }
    }

    public void JsonExport(String path, JSONObject jsobject) {
        try{
            FileWriter fileWriter = new FileWriter(path, false);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(jsobject.toJSONString());
            bufferedWriter.flush();
            bufferedWriter.close();

        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
