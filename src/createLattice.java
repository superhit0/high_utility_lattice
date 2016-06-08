import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;


public class createLattice {
	private static <Integer> List<List<Integer>> powerset(Collection<Integer> list) {
		  List<List<Integer>> ps = new ArrayList<List<Integer>>();
		  ps.add(new ArrayList<Integer>());   // add the empty set
		 
		  // for every item in the original list
		  for (Integer item : list) {
		    List<List<Integer>> newPs = new ArrayList<List<Integer>>();
		 
		    for (List<Integer> subset : ps) {
		      // copy all of the current powerset's subsets
		      newPs.add(subset);
		 
		      // plus the subsets appended with the current item
		      List<Integer> newSubset = new ArrayList<Integer>(subset);
		      newSubset.add(item);
		      newPs.add(newSubset);
		    }
		 
		    // powerset is now powerset of list.subList(0, list.indexOf(item)+1)
		    ps = newPs;
		  }
		  return ps;
		}
	
	private List<List<java.lang.Integer>> getPowerSet(Collection<Integer> input)
	{
		Collection<Integer> set=new HashSet<Integer>();
		/*for(int i=1;i<5;i++){
			set.add(i);
		}*/
		set.addAll(input);
		List<List<Integer>> powerSet=powerset(set);
		for(List<Integer> element:powerSet){
			if(element.size()==0){
				powerSet.remove(element);
				break;
			}
		}
		Collections.sort(powerSet, new Comparator<List<Integer>>(){
			public int compare(List<Integer> o1, List<Integer> o2) {
				// compare the TWU of the items
				if(o1.size()>o2.size())
					return 1;
				else if(o1.size()<o2.size())
					return -1;
				else 
					return 0;
			}
			} );
		//System.out.println(powerSet);
		return powerSet;
	}
	
	public void createConnections(LNode node,LNode newNode){
			if(node.getSize()<newNode.getSize()-2){
				for(LNode child:node.childs){
					if(child.isin(newNode.getItemset())){
						createConnections(child, newNode);
					}
				}
			}else if(node.getSize()==newNode.getSize()-2){
				for(LNode child:node.childs){
					if(child.isin(newNode.getItemset())){
						if(!child.childs.contains(newNode))
						child.childs.add(newNode);
					}
				}
			}else{
				if(newNode.getSize()==1){
					node.childs.add(newNode);
				}
			}
	}
	
	public LNode CreateLattice(Collection<Integer> inputSet){
		LNode startRoot=new LNode();
		List<List<Integer>> powerSet=getPowerSet(inputSet);
		for(List<Integer> element:powerSet){
			LNode newNode=new LNode(new HashSet<Integer>(element));
			createConnections(startRoot, newNode);
		}
		return startRoot;
	}
}
