import java.net.URLDecoder;
import java.net.URLEncoder;

public class Tests {
    public static void main(String[] args) {
        String s = "from=${2},msgid=&lt;${4}&gt;";
        String encode = URLEncoder.encode(s);
        String decode = URLDecoder.decode(encode);
        System.out.println(decode);
    }
}
