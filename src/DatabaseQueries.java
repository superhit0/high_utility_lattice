import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Set;


public class DatabaseQueries implements Runnable{
	boolean p_add;
	LNode p_root;
	String p_thisLine;
	int p_tid;
	
	private DatabaseQueries(boolean add, LNode root, String thisLine, int tid){
		p_add=add;
		p_root=root;
		p_thisLine=thisLine;
		p_tid=tid;
	}
	
	public DatabaseQueries(){
	}
	
	public void addTransaction(LNode node,String transaction,int tid){
		String split[] = transaction.split(":"); 
		String items[] = split[0].split(" ");
		String utilities[]=split[2].split(" ");
		int transactionUtility = Integer.parseInt(split[1]);
		for(int i=0; i <items.length; i++){
			// convert item to integer
			Integer item = Integer.parseInt(items[i]);
			Long utility=Long.parseLong(utilities[i]);
			addItem(node,tid,item%10,utility,transactionUtility);		//Item ID changed to Item ID % 7
		}
	}

	private void addItem(LNode node, Integer tid, Integer item, Long utility,
			int transactionUtility) {
		for(LNode child:node.childs){
			
			if(!child.getItemset().contains(item))
				continue;
			
			child.addtoMap(tid,item,utility,transactionUtility);
			addItem(child, tid, item, utility, transactionUtility);
		}
	}
	
	public void getDatabaseToLattice(String input, LNode root, int windowSize, int batchSize, long minUtility, int k, BufferedWriter writer) throws IOException, InterruptedException{
		BufferedReader myInput = new BufferedReader(new InputStreamReader( new FileInputStream(new File(input))));
		String thisLine="";
		int lowerLimit=1,upperLimit=lowerLimit+windowSize;
		Thread t[]=new Thread[windowSize];
		int tid=1;
		long startTime=System.currentTimeMillis();
		while ((thisLine = myInput.readLine()) != null) {
			if(tid>=lowerLimit&&tid<upperLimit){
				addTransaction(root, thisLine, tid);
				System.out.println(tid);
				/*t[tid-lowerLimit]=new Thread(new DatabaseQueries(true,root,thisLine,tid));
				t[tid-lowerLimit].start();*/
				tid++;
				//while(Runtime.getRuntime().availableProcessors()<2);
			}
			else{
				//for(int i=0;i<windowSize;i++)
				//	t[i].join();
					
				LatticeQueries obj3=new LatticeQueries();
				//obj3.pruneLattice(root, minUtility);
				Set<LNode> topk=obj3.topK(root, k);
				obj3=null;
				writetofile(topk,writer,System.currentTimeMillis()-startTime,lowerLimit,upperLimit);
				startTime=System.currentTimeMillis();
				
				for(int i=lowerLimit;i<lowerLimit+batchSize;i++)
					removeTransaction(root, i);
					/*new Thread(new DatabaseQueries(false,root,"",i)).start();*/
				
				lowerLimit+=batchSize;
				upperLimit+=batchSize;
			}
		}
		myInput.close();
	}
	
	private void writetofile(Set<LNode> topk, BufferedWriter writer, double totalTime, int lowerLimit, int upperLimit) throws IOException {
		// TODO Auto-generated method stub
		writer.newLine();
		/*writer.write(lowerLimit+" to "+upperLimit);
		for(LNode element:topk){
			writer.write(element.toString());
			writer.newLine();
		}*/
		//writer.write("Time Taken to Execute: "+totalTime+" ms\n");
		writer.write(totalTime+" "+(Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory()));
		writer.flush();
		System.out.println("done");
	}

	public void removeTransaction(LNode node,int tid){
		for(LNode child:node.childs){
			child.removeTransaction(tid);
			removeTransaction(child,tid);
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		if(p_add){
			addTransaction(p_root, p_thisLine, p_tid);
		}else{
			removeTransaction(p_root, p_tid);
		}
	}
}
