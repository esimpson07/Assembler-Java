import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author spockm
 */
public class Assembler 
{
    Parser parser;
    List<String> output;
    SymbolTable symbolTable;
    C_Translator cTrans;
    File file; 
    BufferedWriter br;
        
    public Assembler() throws java.io.IOException
    {
        br = new BufferedWriter(new FileWriter("OUTPUT.asm"));
        file = new File("C:\\Users\\26simpsone\\Downloads\\nand2tetris\\nand2tetris\\projects\\06\\pong\\PongL.asm");
        parser = new Parser(file);
        output = new ArrayList<String>();        
        symbolTable = new SymbolTable();     
        cTrans = new C_Translator();
    }
    
    public static void main(String[] args)
    {
        try {
            Assembler assembler = new Assembler();
            assembler.firstPass();
            assembler.resetStream();
            assembler.middlePass();
            assembler.resetStream();
            assembler.secondPass();
        } catch (IOException e) {
            while(true) {
                System.out.println("No File Found");
                //nothing
            }
        }
    }
    
    /**
     * The first pass of the compiler fills the SymbolTable
     * looks for (LABELS) and @vars
     */
    public void firstPass()
    {
        while(parser.hasMoreCommands()) {
            parser.advance();
            if(parser.labelSymbol() != null && !symbolTable.contains(parser.labelSymbol())) {
                symbolTable.addEntry(parser.labelSymbol(),parser.getLineNumber());
            }
        }
    }
    
    public void middlePass()
    {
        while(parser.hasMoreCommands()) {
            parser.advance();
            if(parser.atSymbol() != null && !parser.symbolIsNumber() && !symbolTable.contains(parser.atSymbol())) {
                symbolTable.addEntry(parser.atSymbol(),SymbolTable.getNextAddress());
            }
        }
    }
    
    /**
     * The second pass of the compiler makes the .hack file (1's and 0's)
     */
    public void secondPass()
    {
        while(parser.hasMoreCommands()) {
            parser.advance();
            if(parser.getBinaryLine() != null) {
                String binary = parser.getBinaryLine();
                //System.out.println(binary);
                output.add(binary);
            }
        }
        for (String str : output) {
            try
            {
                br.write(str + System.lineSeparator());
            }
            catch (java.io.IOException ioe)
            {
                ioe.printStackTrace();
            }
        }
        try
        {
            br.close();
        }
        catch (java.io.IOException ioe)
        {
            ioe.printStackTrace();
        }
    }
        
    public void resetStream()
    {
        parser = new Parser(file);
    }
}