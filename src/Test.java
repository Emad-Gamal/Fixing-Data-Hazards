import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Test {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		// InstructionRegisters instructionRegs = new InstructionRegisters();
		
		//PrintWriter writer = new PrintWriter("the-file-name.txt");
		
		List<String> instructionList = new ArrayList<String>();
		/*instructionList.add("add t0 t1 t2");
		instructionList.add("add t1 t0 t1");
		instructionList.add("add t3 t2 t4");
		instructionList.add("add t0 t1 t2");
		instructionList.add("add t5 t3 t4");
		instructionList.add("add t6 t2 t7");*/
		
		List<String> lines = Files.readAllLines(Paths.get("ins.txt"));
		
		for(int i=0; i<lines.size(); i++){
			instructionList.add(lines.get(i));
		}
		
		
		/*instructionList.add("add t1 zero 1");
		instructionList.add("lw t3 20 t0");
		instructionList.add("add t5 t3 t1");
		instructionList.add("sub t2 t5 t1");
		instructionList.add("addi t3 t3 2");
		instructionList.add("add t4 t1 t0");
		instructionList.add("lw t5 34 t3");
		instructionList.add("add t6 t6 t5");
		instructionList.add("add t7 t6 t5");*/

		List<InstructionRegisters> parsedInstructionList = new ArrayList<>();

		for (String x : instructionList) {
			parsedInstructionList.add(InstructionRegisters.parseInstruction(x));
		}

		/*
		 * for (InstructionRegisters x : parsedInstructionList) { System.out
		 * .println(x.getRd() + "  " + x.getRs() + "   " + x.getRt()); }
		 */
		System.out.println();
		Scheduleing(parsedInstructionList);
		//testScheduleing(parsedInstructionList);

	}

	public static void Scheduleing(List<InstructionRegisters> parsedInstructions) throws FileNotFoundException {
		final List<InstructionRegisters> nodeValueList = new ArrayList<InstructionRegisters>();
		Graph<InstructionRegisters> graph = new Graph<InstructionRegisters>(
				new NodeValueListener<InstructionRegisters>() {
					public void evaluating(InstructionRegisters nodeValue) {
						nodeValueList.add(nodeValue);
					}
				});

		boolean added ;
		for (int i = 0; i < parsedInstructions.size(); i++) {
			added = false;
			for (int j = i + 1; j < parsedInstructions.size(); j++) {
				if (parsedInstructions.get(j).getRd().equals(parsedInstructions.get(i).getRd())) {
					graph.addDependency(parsedInstructions.get(i),
							parsedInstructions.get(j));
					added = true;
				}else if((parsedInstructions.get(j).getRs().equals(parsedInstructions.get(i).getRd()) || 
						  parsedInstructions.get(j).getRt().equals(parsedInstructions.get(i).getRd()))){
					
					graph.addDependency(parsedInstructions.get(i),
							parsedInstructions.get(j));
					added=true;
					
				}else if((parsedInstructions.get(i).getRs().equals(parsedInstructions.get(j).getRd()) || 
						  parsedInstructions.get(i).getRt().equals(parsedInstructions.get(j).getRd()))){
					
					graph.addDependency(parsedInstructions.get(i),
							parsedInstructions.get(j));
					added=true;
					
				}
			}
			if (!added)
				graph.addDependency(parsedInstructions.get(i),
						new InstructionRegisters());
		}

		graph.generateDependencies();
		for (InstructionRegisters x : nodeValueList) {
			System.out
					.println(x.getIns() + "  " + x.getRd() + "  " + x.getRs() + "   " + x.getRt());
		}
		
		System.out.println("Done1");
		
		PrintWriter writer = new PrintWriter("output.txt");
		
		for (int i = 0; i < nodeValueList.size() - 1; i++) {
			
			if(!nodeValueList.get(i).getRd().equals("")){
			System.out.println(nodeValueList.get(i).getIns() + "  "
					+ nodeValueList.get(i).getRd() + "  "
					+ nodeValueList.get(i).getRs() + "  "
					+ nodeValueList.get(i).getRt());
			
			writer.println(nodeValueList.get(i).getIns() + "  "
					+ nodeValueList.get(i).getRd() + "  "
					+ nodeValueList.get(i).getRs() + "  "
					+ nodeValueList.get(i).getRt());
			}
			
			if (nodeValueList.get(i).getRd().equals(nodeValueList.get(i + 1).getRd())&&!nodeValueList.get(i).getRd().equals("")) {
					System.out.println("NOP");
					writer.println("NOP");
			} else if ((nodeValueList.get(i+1).getRs().equals(nodeValueList.get(i).getRd()) ||
					    nodeValueList.get(i+1).getRt().equals(nodeValueList.get(i).getRd()))&&!nodeValueList.get(i).getRd().equals("")) {
				System.out.println("NOP");
				writer.println("NOP");
			}

		}
		
		System.out.println("Done2");
		writer.close();
		
	}
	


}
