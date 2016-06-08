import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;


public class runTHIS {
	public static void main(String args[])throws IOException, InterruptedException{
		if(args.length!=5)
		{
			System.out.println("params- input output k minU winSize");
			return;
		}
		String input=args[0];
		String output=args[1];
		int k=Integer.parseInt(args[2]);
		long minUtility=Long.parseLong(args[3]);
		int windowSize=Integer.parseInt(args[4]);
		int batchSize=windowSize*10/100;
		BufferedWriter writer=new BufferedWriter(new FileWriter(output,true));
		
		System.out.println("---Start of Program---");	
		//long startTime = System.currentTimeMillis();
		Set<Integer> inputSet=new HashSet<Integer>();
		for(int i=0;i<=9;i++)
			inputSet.add(i);
		
		createLattice obj=new createLattice();
		LNode root=obj.CreateLattice(inputSet);
		obj=null;
		System.out.println("---Lattice Created---");
		DatabaseQueries obj2=new DatabaseQueries();
		obj2.getDatabaseToLattice(input, root, windowSize, batchSize,minUtility,k,writer);
		obj2=null;
		System.out.println("---Database Read---");
		/*LatticeQueries obj3=new LatticeQueries();
		obj3.pruneLattice(root, minUtility);
		Set<LNode> topk=obj3.topK(root, k);
		obj3=null;
		
		
		for(LNode element:topk){
			writer.write(element.toString());
			writer.newLine();
		}*/
		/*runTHIS testobj=new runTHIS();
		testobj.printlattice(root,writer);*/
		System.out.println("---End of Program---");
		//long endTime   = System.currentTimeMillis();
		//double totalTime = (endTime - startTime);
		Runtime.getRuntime().gc();
		//writer.write("Time Taken to Execute: "+totalTime+" ms\nMemory Consumed: "+(Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory()));
		writer.close();
	}
	
	public void printlattice(LNode node,BufferedWriter writer) throws IOException
	{
		for(LNode child:node.childs){
			writer.write(child.toString());
		writer.newLine();
		printlattice(child, writer);
		}
	}

}
