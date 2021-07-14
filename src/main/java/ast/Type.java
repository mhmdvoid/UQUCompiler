package ast;

public class Type {

	 enum BasicType {
		Int(4)

		private byte size;


		BasicType(byte size) {
			this.size = size;
		}
	}
	BasicType type;
	public Type(BasicType type) 
	{
		this.type = type;
	}


}
