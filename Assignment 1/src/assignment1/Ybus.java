package assignment1;

import java.util.ArrayList;

public class Ybus {
	
	String FromBus;
	String ToBus;
	Complex Admittance;
	
	public Ybus (String FromBus, String ToBus, Complex Admittance) {
		this.FromBus = FromBus;
		this.ToBus = ToBus;
		this.Admittance = Admittance;
	}
		
	public static ArrayList<BusBarSection> realBuses (ArrayList<BusBarSection> busbarList, ArrayList<Topology> topologyElements) {
		
		ArrayList<BusBarSection> realbusbarList = new ArrayList<>();
		
		for (BusBarSection busbar:busbarList) {
			for (Topology topo:topologyElements) {
				if (busbar.ID.equals(topo.IDBusBar1) || busbar.ID.equals(topo.IDBusBar2)) {
					if (realbusbarList.isEmpty()) {
						realbusbarList.add(busbar);
						break;
					} else {
						for (BusBarSection realbusbar:realbusbarList) {
							if (!busbar.ID.equals(realbusbar.ID)) {
								realbusbarList.add(busbar);
								break;
							}
						}
						
						break;
					}					
				}
			}
		}
		
		return realbusbarList;
	}
	
	public static ArrayList<Ybus> createYbusMatrix (ArrayList<BusBarSection> realbusbarList, ArrayList<Topology> topologyElements, ArrayList<ACLineSegment> lineSegmentList,
			ArrayList<PowerTransformerEnd> powerTransformerEndList){
		
		ArrayList<Ybus> YbusMatrixElements = new ArrayList<>();
		
		// Diagonal Ybus Matrix Elements calculation
		
		for (BusBarSection busbar:realbusbarList) {
			
			ArrayList<Complex> Admittance = new ArrayList<>();
			Complex TotalAdmittance = new Complex(0,0);
			
			for (Topology topo:topologyElements) {
				
				if (busbar.ID.equals(topo.IDBusBar1) || busbar.ID.equals(topo.IDBusBar2)) {
					
					for (ACLineSegment line:lineSegmentList) {
						
						if (topo.ID.equals(line.ID)) { 
							
							
							Complex zline = new Complex (line.Rpu, line.Xpu);
							Complex yline = new Complex (line.gchpu, line.bchpu);
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
							Complex ztrans = new Complex (wind.Rpu, wind.Xpu);
							Complex ytrans = new Complex (wind.gchpu, wind.bchpu);
							
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
		
		for (BusBarSection busbar1:realbusbarList) {
			
			for (BusBarSection busbar2:realbusbarList) {
				
				ArrayList<Complex> Admittance = new ArrayList<>();
				Complex TotalAdmittance = new Complex(0,0);
				
				if (!busbar1.ID.equals(busbar2.ID)) {
					
					for (Topology topo:topologyElements) {
						
						if ( (busbar1.ID.equals(topo.IDBusBar1) && busbar2.ID.equals(topo.IDBusBar2)) || (busbar1.ID.equals(topo.IDBusBar2) && busbar2.ID.equals(topo.IDBusBar1)) )  {
							
							for (ACLineSegment line:lineSegmentList) {
								
								if (topo.ID.equals(line.ID)) { 
									
									Complex zline = new Complex (line.Rpu, line.Xpu);
									Complex length = new Complex (line.length, 0);
									Complex one = new Complex (1, 0);
									Complex minusOne = new Complex (-1,0);
									
									Admittance.add(minusOne.times(one.divides(length.times(zline))));
								}
							}
							for (PowerTransformerEnd wind:powerTransformerEndList) {
								
								if (topo.ID.equals(wind.transformerID) && !wind.R.equals(0.0)) {
									
									Complex one = new Complex (1, 0);
									Complex ztrans = new Complex (wind.Rpu, wind.Xpu);
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
	
	public static Complex[][] YBusMatrix(ArrayList<Ybus> YbusMatrixElements, ArrayList<BusBarSection> realbusbarList) {
		
		Complex[][] matrix = new Complex[realbusbarList.size()][realbusbarList.size()];
		
		for (int i=0 ; i<realbusbarList.size() ; i++) {
			
			int j = 0;
			
			for (Ybus ybus:YbusMatrixElements) {
								
				if (ybus.FromBus.equals(realbusbarList.get(i).ID) && !ybus.FromBus.equals(ybus.ToBus)) {
					
					matrix[i][j] = ybus.Admittance;
					j = j+1;
						
				} else if (ybus.FromBus.equals(realbusbarList.get(i).ID) && ybus.FromBus.equals(ybus.ToBus)) {
					
					matrix[i][i] = ybus.Admittance;
				}
				
				if (j == i) {
					j = j+1;
				}				
			}
		}
		return matrix;
	}
	
	
	public static void printYbusMatrix (Complex[][] YbusMatrix){
		
		System.out.println();
		System.out.println("The columns of the Ybus Matrix are the following:");
		System.out.println();
		
		for (int i=0; i<YbusMatrix.length; i++){
			
			int bus1 = i+1;
			System.out.println ("---- BUS " + bus1 + " ----");
			
			for (int j=0; j<YbusMatrix.length; j++){
				
				int bus2 = j+1;
				System.out.println("Bus "+ bus2 + ":    " + YbusMatrix[j][i]);
			}
			System.out.println();
		}
	}

}
