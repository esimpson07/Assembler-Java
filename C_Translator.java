public class C_Translator 
{
    public static String dest(String in)
    {
        if(in == null) {
            return("000");
        } else if(in.equals("M")) {
            return("001");
        } else if(in.equals("D")) {
            return("010");
        } else if(in.equals("MD")) {
            return("011");
        } else if(in.equals("A")) {
            return("100");
        } else if(in.equals("AM")) {
            return("101");
        } else if(in.equals("AD")) {
            return("110");
        } else if(in.equals("AMD")) {
            return("111");
        } else {
            System.out.println(in);
            return(null);
        }
    }
    
    public static String comp(String in)
    {   
        if(in.equals("0")) {
            return("101010");
        } else if(in.equals("1")) {
            return("111111");
        } else if(in.equals("-1")) {
            return("111010");
        } else if(in.equals("D")) {
            return("001100");
        } else if(in.equals("A") || (in.equals("M"))) {
            return("110000");
        } else if(in.equals("!D")) {
            return("001101");
        } else if(in.equals("!A") || (in.equals("!M"))) {
            return("110001");
        } else if(in.equals("-D")) {
            return("001111");
        } else if(in.equals("-A") || (in.equals("-M"))) {
            return("110011");
        } else if(in.equals("D+1")) {
            return("011111");
        } else if(in.equals("A+1") || (in.equals("M+1"))) {
            return("110111");
        } else if(in.equals("D-1")) {
            return("001110");
        } else if(in.equals("A-1") || (in.equals("M-1"))) {
            return("110010");
        } else if(in.equals("D+A") || in.equals("D+M")) {
            return("000010");
        } else if(in.equals("D-A") || in.equals("D-M")) {
            return("010011");
        } else if(in.equals("A-D") || in.equals("M-D")) {
            return("000111");
        } else if(in.equals("D&A") || in.equals("D&M")) {
            return("000000");
        } else if(in.equals("D|A") || in.equals("D|M")) {
            return("010101");
        } else {
            return(null);
        }
    }
    
    public static String jump(String in)
    {
        if(in == null) {
            return("000");
        } else if(in.equals("JGT")) {
            return("001");
        } else if(in.equals("JEQ")) {
            return("010");
        } else if(in.equals("JGE")) {
            return("011");
        } else if(in.equals("JLT")) {
            return("100");
        } else if(in.equals("JNE")) {
            return("101");
        } else if(in.equals("JLE")) {
            return("110");
        } else if(in.equals("JMP")) {
            return("111");
        } else {
            System.out.println(in);
            return(null);
        }
    }
}