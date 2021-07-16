package ast;

public class Type {

	 public enum BasicType {
		Int(4),
		Bool(1);


		private int size;
		 BasicType(int size) {
		 	this.size = size;
		 }
	 }
	BasicType type;
	public Type(BasicType type) 
	{
		this.type = type;
	}

	@Override
	public String toString() {
		return "Type{" +
				"type=" + type +
				'}';
	}
}
