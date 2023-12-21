import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * @author spockm
 */
public class Parser 
{
    private Scanner stream;
    private String currentLine = "//";  
    private int lineNumber = 0;
    
    public final int NO_COMMAND = 0; //These are constants to make the code more readable. 
    public final int A_COMMAND = 1;
    public final int C_COMMAND = 2;
    public final int L_COMMAND = 3; //(Not really a command type, but a (LABEL).
    
    public Parser(File f)
    {        
        try //open the file as a Scanner stream. 
        {
            stream = new Scanner(f);
        }
        catch(FileNotFoundException e)
        {
            System.out.println("File not found!!! " + f.getName());
        }
    }
    
    public boolean hasMoreCommands()
    {
        return stream.hasNext();
    }
    
    //This method sets the global variable currentLine to the next valid ASM line in the file.  
    public void advance()
    {
        try {
            currentLine = stream.nextLine();
            removeComment();
            currentLine = currentLine.trim(); //remove whitespace
            /* If the line is blank, attempt to get another command.  
            If the last line is just whitespace, nothing we can do (yet?)
            */
            if(commandType() == NO_COMMAND && hasMoreCommands()) {
                advance();
            } else if(commandType() != L_COMMAND){
                lineNumber ++;
            }
        } catch (NumberFormatException e) {
            
        }
    }
    
    //Remove any comments (//) from the currentLine
    public void removeComment()
    {
        if(currentLine.indexOf("//") != -1) {
            currentLine = currentLine.substring(0,currentLine.indexOf("//"));
        }
    }
    
    //Return which type of command the currentLine is.  (Use the CONSTANTS defined at top)
    public int commandType()
    {
        if(currentLine.indexOf("@") != -1) {
            return(A_COMMAND);
        } else if(currentLine.indexOf("(") != -1 && currentLine.indexOf(")") != -1) {
            return(L_COMMAND);
        } else if(currentLine.indexOf("=") != -1 || currentLine.indexOf(";") != -1) {
            return(C_COMMAND);
        } else {
            return(NO_COMMAND);
        }
    }       
    
    // return the symbol contained in an @symbol or (LABEL) line
    public String atSymbol()
    {
        if(currentLine.indexOf("@") != -1) {
            if(SymbolTable.contains(currentLine.substring(currentLine.indexOf("@") + 1,currentLine.length()))) {
                return(Integer.toString(SymbolTable.getAddress(currentLine.substring(currentLine.indexOf("@") + 1,currentLine.length()))));
            }
            return(currentLine.substring(currentLine.indexOf("@") + 1,currentLine.length()));
        }
        return(null);
    }
    
    public String labelSymbol()
    {
        if(currentLine.indexOf("(") != -1 && currentLine.indexOf(")") != -1) {
            return(currentLine.substring(currentLine.indexOf("(") + 1,currentLine.indexOf(")")));
        }
        return(null);
    }
    
    public int getLineNumber() {
        return(lineNumber);
    }

    public boolean symbolIsNumber()
    {
        try
        {   //It was a number, just convert String to Integer
            int value = Integer.parseInt(atSymbol());
            return true;
        }
        catch(NumberFormatException e)
        {   //must be a symbol... use the table.
            return false;
        }
    }
    
    //return the Destination part of the C-instruction, null if not present.  
    public String dest()
    {
        if(commandType() == C_COMMAND) {
            if(currentLine.indexOf("=") != -1) {
                return(currentLine.substring(0,currentLine.indexOf("=")));
            }
            return(null);
        } else {
            return(null);
        }
    }

    //return the Computation (ALU task) part of the C-instruction.  
    public String comp()
    {
        if(commandType() == C_COMMAND) {
            if(currentLine.indexOf(";") != -1 && currentLine.indexOf("=") != -1) {
                return(currentLine.substring(currentLine.indexOf("=") + 1,currentLine.indexOf(";")));
            } else if(currentLine.indexOf(";") != -1 && currentLine.indexOf("=") == -1) {
                return(currentLine.substring(0,currentLine.indexOf(";")));
            } else if(currentLine.indexOf(";") == -1 && currentLine.indexOf("=") != -1) {
                return(currentLine.substring(currentLine.indexOf("=") + 1,currentLine.length()));
            } 
            return(null);
        } else {
            return(null);
        }
    }

    //return the Jump part of the C-instruction, null if not present.      
    public String jump()
    {
        if(commandType() == C_COMMAND && currentLine.indexOf(";") != -1) {
            return(currentLine.substring(currentLine.indexOf(";") + 1,currentLine.length()));
        } else {
            return(null);
        }
    }

    public String getCurrentLine()
    {  
        return(currentLine);
    }  
    
    public String getBinaryLine()
    {
        if(commandType() == A_COMMAND) {
            return(translate_A());
        } else if(commandType() == C_COMMAND) {
            return(translate_C());
        } else {
            return(null);
        }
    }
    
    public Boolean aValue() 
    {
        if(comp().indexOf("M") != -1) {
            return true;
        }
        return false;
    }
    
    //return the binary representation of the A-instruction    
    public String translate_A()
    {
        String binaryString = "";
        if(!symbolIsNumber()) {
            if(SymbolTable.contains(atSymbol())) {
                binaryString = Integer.toBinaryString(Integer.valueOf(SymbolTable.getAddress(atSymbol())));
            }
        } else {
            binaryString = Integer.toBinaryString(Integer.valueOf(atSymbol()));
        }
        
        String zeros = generateString(16 - binaryString.length());
        return(new String(zeros + binaryString));
    }
    
    public String generateString(int size) {
        StringBuffer s = new StringBuffer();
        for(int i = 0; i < size; i ++)
            s.append("0");
        return(s.toString());
    }
    //return the binary representation of the C-instruction
    public String translate_C()
    {
        String dest = C_Translator.dest(dest());
        String comp = C_Translator.comp(comp());
        String jump = C_Translator.jump(jump());
        
        String binaryString;
        if(aValue()) {
            binaryString = "1111";
        } else {
            binaryString = "1110";
        }
        binaryString = new String(binaryString + comp);
        binaryString = new String(binaryString + dest);
        binaryString = new String(binaryString + jump);
        return(binaryString);
    }
}
