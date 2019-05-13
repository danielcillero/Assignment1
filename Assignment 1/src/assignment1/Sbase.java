package assignment1;

import java.util.ArrayList;

public class Sbase {
	
	public static Double Smax (ArrayList<SyncMachine> syncMachList){
		
		// For power transformer ends and AC lines we need the base power which will be the maximum S of all the Synchronous Machines
		
		ArrayList<Double> maxSList = new ArrayList<>();
		
		for (SyncMachine sync:syncMachList) {
			
			maxSList.add(sync.ratedS);
		}
		
		Double maxS = 0.0;
		
		for (int i=0 ; i<maxSList.size() ; i++) {
			
			if (maxSList.get(i) > maxS) {
				maxS = maxSList.get(i);
			}
		}
		
		return maxS;
				
	}
	
}
