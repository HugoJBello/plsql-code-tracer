package plsql.structures;

import java.util.LinkedList;
import java.util.List;

public class Tree {
    private List<Tree> children = new LinkedList<Tree>();
    private Tree parent = null;
    private String data;

    public Tree(String data, Tree parent) {
        this.data = data;
        this.parent = parent;
    }
    public void addChildren (Tree c){
    	children.add(c);
    }
	public Tree getParent() {
		return parent;
	}
	public void setParent(Tree parent) {
		this.parent = parent;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
}