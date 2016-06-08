import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class LNode {
	private Set<Integer> itemset;
	long utility;
	long twu;
	private int sizeOfSet;
	/*@Override
	public String toString() {
		return "LNode [itemset=" + itemset + ", utility=" + utility + ", twu="
				+ twu + ", sizeOfSet=" + sizeOfSet + ", transactions="
				+ transactions + "]";
	}*/

	private Map<Integer,TElement> transactions;
	@Override
	public String toString() {
		return "LNode [itemset=" + itemset + ", utility=" + utility + ", twu="
				+ twu + "]";
	}

	List<LNode> childs;
	
	public LNode(){
		sizeOfSet=0;
		childs=new ArrayList<LNode>();
	}
	
	public LNode(Set<Integer> newSet){
		itemset=new HashSet<Integer>();
		itemset.addAll(newSet);
		sizeOfSet=newSet.size();
		utility=0;
		twu=0;
		transactions=new HashMap<Integer, TElement>();
		childs=new ArrayList<LNode>();
	}
	
	private void addTransaction(int tid,TElement element){
		transactions.put(tid, element);
		if(element.getCount()%sizeOfSet==0){
			synchronized (this) {
			utility+=element.getUtility();
			twu+=element.getTutility();
			}
		}
	}
	
	public void removeTransaction(int tid){
		TElement element=transactions.get(tid);
		if(element!=null){
			synchronized (this) {
		if(element.getCount()%sizeOfSet==0){
			utility-=element.getUtility();
			twu-=element.getTutility();
		}
		transactions.remove(tid);
			}
		}
	}
	
	private void updateTransaction(int tid,int item, Long utility2){
		TElement element=transactions.get(tid);
		boolean condition=element.addCount(item,utility2);
		/*utility=0;
		twu=0;
		for(int i:transactions.keySet()){
			element=transactions.get(i);
			if(element.getCount()%sizeOfSet!=0)
				continue;
			utility+=element.getUtility();
			twu+=element.getTutility();
		}*/
		if(condition){
			if(element.getCount()%sizeOfSet==0){
				synchronized(this){
				utility+=element.getUtility();
				twu+=element.getTutility();
				}
			}
		}
		
	}
	
	public int getSize(){
		return sizeOfSet;
	}
	
	public Set<Integer> getItemset(){
		return itemset;
	}

	public boolean isin(Set<java.lang.Integer> itemset2) {
		return itemset2.containsAll(itemset);
	}

	public void addtoMap(Integer tid, Integer item, Long utility2, int transactionUtility) {
		TElement temp=transactions.get(tid);
		if(temp==null){
			addTransaction(tid, new TElement(item,utility2, transactionUtility));
		}else{
			updateTransaction(tid, item,utility2);
		}
	}
}
