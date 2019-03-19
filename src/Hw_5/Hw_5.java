/*
 * Ye Fang - 10431002
 * reference:
 * 1. https://blog.csdn.net/qq_26849233/article/details/72910010
 * 2. https://blog.csdn.net/wangjinyu501/article/details/7625636
 * 3. https://blog.csdn.net/u013066244/article/details/53197756
 * 4. https://www.geeksforgeeks.org/evaluation-of-expression-tree/
 */

package Hw_5;

import java.util.*;
import java.util.regex.*;
import java.io.*;
import java.math.*;

class Node{
	public String val;
	public Node left;
	public Node right;
	
	public Node(String v, Node l, Node r) {
		val = v;
		left = l;
		right = r;
	}
	
	public void inorder() {
		if (left != null)
			left.inorder();
		System.out.print(val+" ");
		if (right != null)
			right.inorder();
	}

	public void preorder() {
		System.out.print(val+" ");
		if (left != null)
			left.preorder();
		if (right != null)
			right.preorder();
	}

	public void postorder() {
		if (left != null)
			left.postorder();
		if (right != null)
			right.postorder();
		System.out.print(val+" ");
	}
}

class BinaryTree{
	private Node root;
	
	public BinaryTree() {
		root = null;
	}
	
	// preorder()
	public void preOrder() {
		if(root == null) {
			return;
		}
		root.preorder();
		System.out.println();
	}
	
	// inorder()
	public void inOrder() {
		if(root == null) {
			return;
		}
		root.inorder();
		System.out.println();
	}
	
	// postorder()
	public void postOrder() {
		if(root == null) {
			return;
		}
		root.postorder();
		System.out.println();
	}
	
	// Build Expression Tree
	public void insert(String[] input) {
		Stack<Node> stack = new Stack<Node>();
		
		for(String str : input) {
			Node newNode = new Node(str, null, null);
			if(str.equals("+")||str.equals("-")||str.equals("*")||str.equals("/")) { 
				// if the operator is "+","-","*","/", pop from stack and build tree
				newNode.right = stack.pop();
				newNode.left = stack.pop();
				stack.push(newNode);
			}else if(str.equals("sqrt")) {
				// if the operator is "sqrt", pop from stack and add to the right children
				newNode.right = stack.pop();
				stack.push(newNode);
			}else { 
				// if not operator, push into the stack
				stack.push(newNode);
			}
		}
		root = stack.pop();
	}
	
	// Optimize Expression Tree
	public void Optimize() {
		System.out.println("After Optimizing: ");
		System.out.println(Optimize(root));
	}
	
	private String Optimize(Node CurrentRoot) {
		if(CurrentRoot == null) {
			return null;
		}
		if(CurrentRoot.left == null && CurrentRoot.right == null) {
			return CurrentRoot.val;
		}
		String left = Optimize(CurrentRoot.left);
		String right = Optimize(CurrentRoot.right);
		
		if(CurrentRoot.val.equals("+")) {
			if(left.equals("0") || left.equals("-0")) {
				return right;
			}else if(right.equals("0") || right.equals("-0")) {
				return left;
			}else if(right.equals("-" + left) || left.equals("-" + right)) {
				return "0";
			}
		}
		if(CurrentRoot.val.equals("-")) {
			if(left.equals("0") || left.equals("-0")) {
				return "(" + "-" + right + ")";
			}else if(right.equals("0") || right.equals("-0")) {
				return left;
			}else if(left.equals(right)) {
				return "0";
			}
		}
		if(CurrentRoot.val.equals("*")) {
			if(left.equals("0") || right.equals("0") || left.equals("-0") || right.equals("-0")) {
				return "0";
			}else if(left.equals("1")) {
				return right;
			}else if(right.equals("1")) {
				return left;
			}
		}
		if(CurrentRoot.val.equals("/")) {
			if(left.equals("0") || left.equals("-0")) {
				return "0";
			}else if(right.equals("1")) {
				return left;
			}else if(left.equals(right)) {
				return "1";
			}else if(right.equals("-" + left) || left.equals("-" + right)) {
				return "-1";
			}
		}
		if(CurrentRoot.val.equals("sqrt")) {
			if(right.equals("0") || right.equals("-0")) {
				return "0";
			}else if(right.equals("1")){
				return "1";
			}
			return "(" + CurrentRoot.val + right + ")";
		}
		return "(" + left + CurrentRoot.val + right + ")";
	}
	
	// Evaluate the Tree and print the result
	public void calc() {
		System.out.println("The result is: "+calc(root));
		System.out.println("**********************************");
	}
	
	private double calc(Node CurrentRoot) {
		Calc c = new Calc();
		if(CurrentRoot == null) {
			return 0.0;
		}
		if(CurrentRoot.left == null && CurrentRoot.right == null) {
			if(isNumeric(CurrentRoot.val)) { 
				// if CurrentRoot.val is number
				Double x = Double.parseDouble(CurrentRoot.val);
				return x;
			}else { 
				// if CurrentRoot.val = x, a, b...
				return 0.0;
			}
		}
		
		double left = calc(CurrentRoot.left);
		double right = calc(CurrentRoot.right);
		
		if(CurrentRoot.val.equals("+")) {
			return c.add(left, right);
		}
		if(CurrentRoot.val.equals("-")) {
			return c.minus(left, right);
		}
		if(CurrentRoot.val.equals("*")) {
			return c.multiple(left, right);
		}
		if(CurrentRoot.val.equals("/")) {
			return c.divide(left, right);
		}
		return c.sqrt(right);
	}
	
	private boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("-?[0-9]+(\\.[0-9]+)?");
        String bigStr;
        try {
            bigStr = new BigDecimal(str).toString();
        } catch (Exception e) {
            return false;
        }

        Matcher isNum = pattern.matcher(bigStr);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }
}

class Calc{
	public double add(double left, double right) {
		return (left + right);
	}
	
	public double minus(double left, double right) {
		return (left - right);
	}
	
	public double multiple(double left, double right) {
		return (left * right);
	}
	
	public double divide(double left, double right) {
		return (left / right);
	}
	
	public double sqrt(double right) {
		return Math.sqrt(right);
	}
}

public class Hw_5 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BinaryTree bt = new BinaryTree();
		try {
			File f = new File(System.getProperty("user.dir")+"/hw8inp.dat");
			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);

			String line = null;
			while((line = br.readLine()) != null) {
				System.out.println("The Original Expression is:");
				System.out.println(line);
				String[] input = line.split(" ");
				bt.insert(input);
				System.out.println("InOrder of the Expression Tree:");
				bt.inOrder();
				System.out.println("PostOrder of the Expression Tree:");
				bt.postOrder();
				System.out.println("PreOrder of the Expression Tree:");
				bt.preOrder();
				bt.Optimize();
				bt.calc();
			}
			
			br.close();
		
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
