import java.util.HashMap;

/**
 * @author spockm
 */
public class SymbolTable 
{
    public static HashMap hm;
    public static int value = 15;
    
    public SymbolTable()
    {
        hm = new HashMap();
        //Put the defaults in the list.
        hm.put("SP", 0);
        hm.put("LCL", 1);
        hm.put("ARG", 2);
        hm.put("THIS", 3);
        hm.put("THAT", 4);
        hm.put("R0", 0);
        hm.put("R1", 1);
        hm.put("R2", 2);
        hm.put("R3", 3);
        hm.put("R4", 4);
        hm.put("R5", 5);
        hm.put("R6", 6);
        hm.put("R7", 7);
        hm.put("R8", 8);
        hm.put("R9", 9);
        hm.put("R10", 10);
        hm.put("R11", 11);
        hm.put("R12", 12);
        hm.put("R13", 13);
        hm.put("R14", 14);
        hm.put("R15", 15);
        hm.put("SCREEN",16384);
        hm.put("KBD", 24576);
    }
    
    public static void addEntry(String symbol, int value)
    {
        hm.put(symbol,value);
        //System.out.println("Adding table entry: "+symbol+" , "+value);
    }
    
    public static boolean contains(String symbol)
    {
        return hm.containsKey(symbol);
    }
    
    public static int getAddress(String symbol)
    {
        return (int)hm.get(symbol);
    }
    
    public static int getNextAddress() {
        value ++;
        return(value);
    }
}