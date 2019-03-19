/*
 * Ye Fang - 10431002
 * reference: 
 * https://www.youtube.com/watch?v=jtMwp0FqEcg
 */

package Hw_6;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashSet;

class Link{
	public char ch;
	public boolean isEnd;
	public Link next;
	
	public Link(char c) {
		ch = c;
		isEnd = false;
	}
}

class LinkedList{
	public Link head;
	public int collision;
	
	public LinkedList() {
		head = null;
		collision = 0;
	}
	
	public void insertEnd(Link l) {
		if(head == null){
			head = l;
			head.next = null;
			return;
		}
		Link p;
		for(p = head; p.next != null; p = p.next) {
			;
		}
		Link tmp = l;
		p.next = tmp;
		p.next.next = null;
	}
}

class HashMap{
	private LinkedList[] table;
	private int capacity = 9999; // because there are too many words in the file, I choose a big number for initiating the hash table
	HashSet<Integer> index; // in order to recognize the collision of the keys, store the keys without duplicating
	
	public HashMap() {
		table = new LinkedList[capacity];
		for(int i = 0; i < table.length; i++) {
			table[i] = null;
		}
		index = new HashSet<>();
	}
	
	private int hashFunc(char[] arr) {
		int sum = 0;
		int g = 31;
		
		for(int i = 0; i < arr.length; i++) {
			sum = g * arr[i] + sum;
		}
		return sum % capacity;
	}
	
	public void add(String word) {
		// use Linear Chaining, store one letter in one Link. e.g.: store "ple": p -> l -> e(wordEnd) -> null
		char[] arr = word.toCharArray();
		int pos = hashFunc(arr);
		
		if(!index.add(pos)) {
			// if pos exists, means collision
			table[pos].collision++;
		}
		
		if(table[pos] == null) {
			table[pos] = new LinkedList();
		}
		Link p = table[pos].head;
		
		for(int i = 0; i < arr.length; i++) {
			while(p != null) {
				p = p.next;
			}
			p = new Link(arr[i]);
			table[pos].insertEnd(p);
		}
		p.isEnd = true;
	}
	
	public boolean find(String word) {
		char[] arr = word.toCharArray();
		int pos = hashFunc(arr);
		
		if(table[pos] == null) {
			// if there isn't any word begin with this key in the dictionary, return false
			return false;
		}
		Link p = table[pos].head;
		
		int i = 0;
		
		while(i < arr.length) {
			while(p!=null) {
				if(p.ch == arr[i]) {
					// if find arr[i]
					if(i == arr.length - 1) {
						if(p.isEnd) {
							// if this Link's isEnd is true, means this is word end. Return true
							return true;
						}else {
							// skip to the next Link which isEnd = false, then set i = 0, continue searching
							while(!p.isEnd) {
								p = p.next;
							}
							i = 0;
							p = p.next;
						}
					}else {
						// continue matching
						p = p.next;
						i++;
					}
				}else if(p.ch != arr[i]) {
					// if not matching, skip to the next Link which isEnd = false, then set i = 0, continue searching
					while(!p.isEnd) {
						p = p.next;
					}
					i = 0;
					p = p.next;
				}
			}
			if(p == null) {
				// if p == null, means not found, break while, then return false
				break;
			}
		}
		
		return false;
	}
	
	public void countCollision() {
		int sum = 0;
		
		for(int i : index) {
			if(table[i] != null) {
				sum += table[i].collision;
			}
		}
		System.out.println("HashMap Keys' collision times:" + sum);
	}
}

class TrieNode{
	public TrieNode[] children;
	public HashMap next;
	public boolean isWord;
	
	public TrieNode() {
		children = new TrieNode[26];
		next = new HashMap(); 
		isWord = false;
	}
}

class HashTrie{
	public TrieNode root;
	
	public HashTrie() {
		root = new TrieNode();
	}
	
	public void insert(String words) {
		TrieNode current = root;
		
		// first insert first two characters into the Trie
		if(current.children[words.charAt(0)-'a'] == null) {
			current.children[words.charAt(0)-'a'] = new TrieNode();
		}
		current = current.children[words.charAt(0)-'a'];
		if(current.children[words.charAt(1)-'a'] == null) {
			current.children[words.charAt(1)-'a'] = new TrieNode();
		}
		
		if(words.length()<=2) {
			// if the word has only two characters, marked as end
			current.children[words.charAt(1)-'a'].isWord = true;
		}else{
			// insert the rest of the words into hashmap
			String newString = "";
			for(int i = 2; i < words.length(); i++) {
				newString+=words.charAt(i);
			}
			current.children[words.charAt(1)-'a'].next.add(newString);
		}
		
	}
	
	public boolean find(String words) {
		TrieNode current = root;
		
		// first compare the first two characters in Trie, if not matching, return false
		if(current.children[words.charAt(0)-'a'] == null) {
			return false;
		}
		current = current.children[words.charAt(0)-'a'];
		if(current.children[words.charAt(1)-'a'] == null) {
			return false;
		}

		if(words.length()<=2) {
			// if the word only contains two characters, if its the end of the word, return true, else return false
			return current.children[words.charAt(1)-'a'].isWord;
		}else{
			// find the rest of the words in hashmap
			String newString = "";
			for(int i = 2; i < words.length(); i++) {
				newString+=words.charAt(i);
			}
			return current.children[words.charAt(1)-'a'].next.find(newString);
		}
		
	}
	
	public void displayCollision() {
		TrieNode current = root;
		
		for(int i = 0; i < 26; i++) {
			for(int j = 0; j < 26; j++) {
				if(current.children[i].children[j]!=null) {
					System.out.println("For the words begin with " + "'" + ((char)(i + 'a')) + ((char)(j + 'a')) + "'" + ":");
					current.children[i].children[j].next.countCollision();
				}
			}
		}
	}
	
	public void readFile(String filename) {
		// read file function
		try {
			File f = new File(System.getProperty("user.dir")+"/"+filename);
			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);

			String line = null;
			while((line = br.readLine()) != null) {
				insert(line);
			}
			
			br.close();
		
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void checkFile(String filename) {
		// check file function
		try {
			File f = new File(System.getProperty("user.dir")+"/"+filename);
			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);

			String line = null;
			while((line = br.readLine()) != null) {
				if(!find(line)) {
					System.out.println(line);
				}
			}
			
			br.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}

public class Hw_6 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		HashTrie ht = new HashTrie();
		
		// read file
		ht.readFile("dict.txt");
		
		// show collisions
		System.out.println("Collisions of HashMap: ");
		ht.displayCollision();
		System.out.println("***********************************************");
		
		// check file
		System.out.println("The following words are not in the dictionary:");
		ht.checkFile("spell.txt");
	}

}
