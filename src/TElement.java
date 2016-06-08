import java.util.HashSet;
import java.util.Set;


public class TElement {
	private int count=0;
	private long utility=0;
	private long Tutility=0;
	private Set<Integer> itemset;
	
	public TElement(int item,long utility2,long Tutility2){
		count=1;
		utility=utility2;
		Tutility=Tutility2;
		itemset=new HashSet<Integer>();
		itemset.add(item);
	}
	
	public boolean addCount(int item,long utility2)
	{
		if(!itemset.contains(item)){
		count++;
		utility+=utility2;
		itemset.add(item);
		return true;
		}else{
			return false;
		}
	}
	
	@Override
	public String toString() {
		return "TElement [count=" + count + ", utility=" + utility
				+ ", Tutility=" + Tutility + "]";
	}

	public int getCount(){
		return count;
	}
	
	public long getUtility(){
		return utility;
	}
	
	public long getTutility(){
		return Tutility;
	}
}
