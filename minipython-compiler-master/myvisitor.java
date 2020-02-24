import minipython.analysis.*;
import minipython.node.*;
import java.util.*;

public class myvisitor extends DepthFirstAdapter 
{
	private Hashtable symtable;	

	private int errorCounter;
	
	myvisitor(Hashtable symtable) 
	{
		this.symtable = symtable;
		errorCounter = 0;
	}

	public void inASfuncStatement(ASfuncStatement node) 
	{
		String fName = node.getId().toString();
		int line = ((TId) node.getId()).getLine();
		if (symtable.containsKey(fName))
		{
			System.out.println("Line " + line + ": " +" Function " + fName +" is already defined");
			errorCounter++;
		}
		else
		{
			symtable.put(fName, node);
		}
	}
	
	// Definition
	public void outAAssignmentStatement(AAssignment node) {
		String varName = node.getIdentifier().toString();
		int line = ((TId) node.getId()).getLine();
		if (!symtable.containsKey(varName)) {
			symtable.put(varName, node);
			// valuetable.put(varname, node.getExpression());
		}
	}
	
	// Check
	public void inAIdentifierExpression(AIdentifierExpression node) {
		
		String varName = node.getIdentifier().toString();
		int line = ((TIdentifier) node.getIdentifier()).getLine();
		if (!symtable.containsKey(varName)) {
			System.out.println("Line " + line + ": " +"Variable " + varName + " is not defined!");
			errorCounter++;
		}
	}
	
	public int getErrors() {
		return errorCounter;
	}

}
