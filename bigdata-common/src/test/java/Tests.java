import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tests {
    public static void main(String[] args) {
        String s = "aa.20.7.23/50070jddjhh";
//        String s = "10.20.7.23/8088";
        String pattern = "((((\\d{1,3}|\\w{2})\\.\\d{1,3})\\.\\d{1,3})\\.\\d{1,3}(/?)(\\w*?)j)";
//        String pattern = "((\\d{1,3}\\.\\d{1,3})\\.\\d{1,3})\\.\\d{1,3}\\/(\\d*?)";
        Pattern pattern1 =Pattern.compile(pattern);
        Matcher matcher = pattern1.matcher(s);
//        System.out.println(matcher.matches());

       if(matcher.find())
       {
           System.out.println(matcher.groupCount());
            for (int i = 1;i<=matcher.groupCount();i++) {
                System.out.println("value: " + matcher.group(i) + " i: " + i);
            }
        }
    }
}
