import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


public class LatticeQueries {
	public Set<LNode> topK(LNode node,int k){
		Set<LNode> set=new HashSet<LNode>();
		parseforTopK(node,set,k);
		return set;
	}

	private void parseforTopK(LNode node, Set<LNode> set, int k) {
		for(LNode child:node.childs){
			if(set.size()<=k)
				set.add(child);
			else{
				LNode temp=Collections.min(set, new Comparator<LNode>(){
			public int compare(LNode o1, LNode o2) {
				// compare the TWU of the items
				return (o1.twu>o2.twu)?1:-1;
			}
			});
				if(temp.twu<child.twu){
					set.remove(temp);
					set.add(child);
                    parseforTopK(child,set,k);
				}else{
                    if(temp.twu==child.twu)
                        parseforTopK(child,set,k);
                }
			}
			//parseforTopK(child, set, k);
		}
	}
	
	public void pruneLattice(LNode node,long minUtility){
		for(Iterator<LNode> iterator=node.childs.iterator();iterator.hasNext();){
			LNode value=iterator.next();
			if(value.utility<minUtility)
				iterator.remove();
			else
				pruneLattice(value, minUtility);
		}
	}
}
