package ast;

import java.util.Objects;

public class Type {

	 public enum BasicType {
		Int(4),
		Bool(1);


		private int size;
		 BasicType(int size) {
		 	this.size = size;
		 }
	 }
	private BasicType type;
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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Type type1 = (Type) o;
		return type == type1.type;
	}

	@Override
	public int hashCode() {
		return Objects.hash(type);
	}
}
