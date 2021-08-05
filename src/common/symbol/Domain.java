package common.symbol;

import java.util.*;


public class Domain {
    public static Map<String, Function> functions = new HashMap<>();
    private static Deque<Domain> stack = new ArrayDeque<>();
    public static Domain globalDomain = new Domain(null);

    static {
        functions.put("getint", new Function("getint", "int"));
        functions.put("getch", new Function("getch", "int"));
        functions.put("getarray", new Function("getarray", "int"));
        functions.put("putint", new Function("putint", "void"));
        functions.put("putch", new Function("putch", "void"));
        functions.put("putarray", new Function("putarray", "void"));
        functions.put("starttime", new Function("starttime", "void"));
        functions.put("stoptime", new Function("stoptime", "void"));
    }

    public static Function currentFunc = null;
//    private static int globalOffset = 0;

    private static Domain nextDomain = null;

    static {
        stack.push(globalDomain);
    }

    private Domain father;
    public SymbolTable symbolTable = new SymbolTable(this);

    private Domain(Domain father) {
        this.father = father;
    }

    public static void enterDomain() {
        if (null == nextDomain) {
            stack.push(new Domain(stack.peek()));
        } else {
            stack.push(nextDomain);
            nextDomain = null;
        }
    }

    public static void leaveDomain() {
        stack.pop();
    }

    public static Function enterFunc(String name, String retType) {
        if (functions.containsKey(name)) {
            System.err.println("Function [" + name + "] redeclared");
            System.exit(-1);
        }
        Function function = new Function(name, retType);
        functions.put(name, function);
        currentFunc = function;
        nextDomain = new Domain(stack.peek());

        return function;
    }

    public static void leaveFunc() {
        currentFunc = null;
    }


    public static Variable addConstVar(String name) {
        return addVariableToTable(Variable.constVar(name, getDomain()));
    }

    public static Variable addConstArray(String name, List<Integer> dimensions) {
        return addVariableToTable(Variable.constArray(name, getDomain(), dimensions));
    }


    public static Variable addArray(String name, List<Integer> dimensions) {
        return addVariableToTable(Variable.array(name, getDomain(), dimensions));
    }

    public static Variable addVariable(String name) {
        return addVariableToTable(Variable.var(name, getDomain()));
    }

    public static Variable addParam(String name) {
        Variable var = Variable.var(name, getDomain());
        var.isParam = true;
        return addVariableToTable(var);
    }

    public static Variable addParam(String name, List<Integer> dimensions) {
        Variable var = Variable.array(name, getDomain(), dimensions);
        var.isParam = true;
        return addVariableToTable(var);
    }


    private static Variable addVariableToTable(Variable variable) {
        if (null != getDomain().symbolTable.searchSymbol(variable.name)) {
            System.err.println("Variable [" + variable.name + "] redeclared");
            return null;
        }

//        if (globalDomain == Domain.getDomain()) {   // 全局变量
//
//        }

        // 添加变量到对应的符号表
        getDomain().symbolTable.addVariable(variable);

        // 添加入函数
        if (null != currentFunc) {
            if (variable.isParam) {
                currentFunc.addParam(variable);
            } else {
                currentFunc.addVariable(variable);
            }
        }
        return variable;
    }


    public static Variable searchVar(String name) {
        return searchVar(name, getDomain());
    }

    public static Variable searchVar(String name, Domain domain) {
        Domain curr = domain;
        while (null != curr) {
            Variable variable = curr.symbolTable.searchSymbol(name);
            if (null != variable)
                return variable;
            else
                curr = curr.father;
        }
        System.err.println("variable [" + name + "] is undefined");
        return null;
    }

    public static Function searchFunc(String name) {
        if (functions.containsKey(name)) return functions.get(name);
        else {
            System.err.println("function [" + name + "] is undefined");
            return null;
        }
    }

    private static int getTotalOffset() {
        if (null == currentFunc) {
            return -1;
        } else {
            return currentFunc.totalOffset;
        }
    }

    public static Domain getDomain() {
        if (null == nextDomain)
            return stack.peek();
        else
            return nextDomain;
    }

}
