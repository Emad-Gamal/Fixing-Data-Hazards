
public class InstructionRegisters {

	private String rs, rt, rd, ins;

	public InstructionRegisters() {
		rs = "";
		rt = "";
		rd = "";
		ins = "";
	}

	public static  InstructionRegisters parseInstruction(String toBeParsed) {

		InstructionRegisters parsed = new InstructionRegisters();
		String[] parts = toBeParsed.split(" ");
		if (parts[0].equals("sw")) {
			parsed.ins = parts[0];
			parsed.rs = parts[1];
			parsed.rt = parts[2];
			parsed.rd = parts[3];
		} else {
			parsed.ins = parts[0];
			parsed.rd = parts[1];
			parsed.rs = parts[2];
			parsed.rt = parts[3];
		}

		return parsed;
	}

	public InstructionRegisters(String rs, String rt, String rd) {
		this.rs = rs;
		this.rt = rt;
		this.rd = rd;
	}

	public String getRs() {
		return rs;
	}

	public void setRs(String rs) {
		this.rs = rs;
	}

	public String getRt() {
		return rt;
	}

	public void setRt(String rt) {
		this.rt = rt;
	}

	public String getRd() {
		return rd;
	}

	public void setRd(String rd) {
		this.rd = rd;
	}
	
	public String getIns() {
		return ins;
	}

	public void setIns(String ins) {
		this.ins = ins;
	}

}
