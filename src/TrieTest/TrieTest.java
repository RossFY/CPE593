package TrieTest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

class Node{
	public Node[] children;
	public boolean isWord;
	
	public Node(){
		children = new Node[26];
		isWord = false;
	}
}

class Trie{
	private Node root;
	
	public Trie() {
		root = new Node();
	}
	
	public void insert(String word) {
		Node current = root;
		for(int i = 0; i < word.length(); i++) {
			if(current.children[word.charAt(i)-'a'] == null) {
				current.children[word.charAt(i)-'a'] = new Node();
			}
			current = current.children[word.charAt(i)-'a'];
		}
		current.isWord = true;
	}
	
	public boolean contains(String word) {
		Node current = root;
		for(int i = 0; i < word.length(); i++) {
			if(current.children[word.charAt(i)-'a'] == null) {
				return false;
			}
			current = current.children[word.charAt(i)-'a'];
		}
		return current.isWord;
	}
	
	public boolean containsPrefix(String word) {
		Node current = root;
		for(int i = 0; i < word.length(); i++) {
			if(current.children[word.charAt(i)-'a'] == null) {
				return false;
			}
			current = current.children[word.charAt(i)-'a'];
		}
		return true;
	}
}

public class TrieTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Trie t = new Trie();
		try {
			File f = new File(System.getProperty("user.dir")+"/dict.txt");
			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);

			String line = null;
			while((line = br.readLine()) != null) {
				t.insert(line);
			}
			
			File f2 = new File(System.getProperty("user.dir")+"/spell.txt");
			FileReader fr2 = new FileReader(f2);
			BufferedReader br2 = new BufferedReader(fr2);

			String spell = null;
			while((spell = br2.readLine()) != null) {
				if(!t.contains(spell)) {
					System.out.println(spell);
				}
			}
			br.close();
			br2.close();
		
		}catch(Exception e) {
			e.printStackTrace();
		}

		
	}

}
