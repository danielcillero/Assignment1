package assignment1;

import java.util.ArrayList;

public class YbusCreation {
	
	public static ArrayList<Ybus> createYbusMatrix (ArrayList<BusBarSection> busbarList, ArrayList<Topology> topologyElements, ArrayList<ACLineSegment> lineSegmentList,
			ArrayList<PowerTransformerEnd> powerTransformerEndList){
		
		ArrayList<Ybus> YbusMatrixElements = new ArrayList<>();
		
		// Diagonal Ybus Matrix Elements calculation
		
		for (BusBarSection busbar:busbarList) {
			
			ArrayList<Complex> Admittance = new ArrayList<>();
			Complex TotalAdmittance = new Complex(0,0);
			
			for (Topology topo:topologyElements) {
				
				if (busbar.ID.equals(topo.IDBusBar1) || busbar.ID.equals(topo.IDBusBar2)) {
					
					for (ACLineSegment line:lineSegmentList) {
						
						if (topo.ID.equals(line.ID)) { 
							
							
							Complex zline = new Complex (line.R, line.X);
							Complex yline = new Complex (line.gch, line.bch);
							Complex length = new Complex (line.length, 0);
							Complex one = new Complex (1, 0);
							Complex two = new Complex (2,0);
							
							Admittance.add(one.divides(length.times(zline)).plus((length.times(yline)).divides(two)));
						}
					}
					for (PowerTransformerEnd wind:powerTransformerEndList) {
						
					//	System.out.println(wind.R);
						
						if (topo.ID.equals(wind.transformerID) && !wind.R.equals(0.0)) {
							
							Complex one = new Complex (1, 0);
							Complex ztrans = new Complex (wind.R, wind.X);
							Complex ytrans = new Complex (wind.g, wind.b);
							
							Admittance.add(one.divides(ztrans).plus(ytrans));
						}
					}
				}
			}
			
			
			for (int counter = 0; counter<Admittance.size(); counter++) {
				TotalAdmittance = TotalAdmittance.plus(Admittance.get(counter));  
			}
			
			Ybus DiagonalElement = new Ybus (busbar.ID, busbar.ID, TotalAdmittance);
			YbusMatrixElements.add(DiagonalElement);
		}
		
		
		
		// Non-diagonal Ybus matrix elements calculation
		
		for (BusBarSection busbar1:busbarList) {
			
			for (BusBarSection busbar2:busbarList) {
				
				ArrayList<Complex> Admittance = new ArrayList<>();
				Complex TotalAdmittance = new Complex(0,0);
				
				if (!busbar1.ID.equals(busbar2.ID)) {
					
					for (Topology topo:topologyElements) {
						
						if ( (busbar1.ID.equals(topo.IDBusBar1) && busbar2.ID.equals(topo.IDBusBar2)) || (busbar1.ID.equals(topo.IDBusBar2) && busbar2.ID.equals(topo.IDBusBar1)) )  {
							
							for (ACLineSegment line:lineSegmentList) {
								
								if (topo.ID.equals(line.ID)) { 
									
									Complex zline = new Complex (line.R, line.X);
									Complex length = new Complex (line.length, 0);
									Complex one = new Complex (1, 0);
									Complex minusOne = new Complex (-1,0);
									
									Admittance.add(minusOne.times(one.divides(length.times(zline))));
								}
							}
							for (PowerTransformerEnd wind:powerTransformerEndList) {
								
								if (topo.ID.equals(wind.transformerID) && !wind.R.equals(0.0)) {
									
									Complex one = new Complex (1, 0);
									Complex ztrans = new Complex (wind.R, wind.X);
									Complex minusOne = new Complex (-1,0);
									
									Admittance.add(minusOne.times(one.divides(ztrans)));
								}
							}
						}
					}
					
					for (int counter = 0; counter<Admittance.size(); counter++) {
						TotalAdmittance = TotalAdmittance.plus(Admittance.get(counter));  
					}
					
					Ybus NonDiagonalElement = new Ybus (busbar1.ID, busbar2.ID, TotalAdmittance);
					YbusMatrixElements.add(NonDiagonalElement);
					
				}
			}
			
		}
		
		return YbusMatrixElements;
	}

}
