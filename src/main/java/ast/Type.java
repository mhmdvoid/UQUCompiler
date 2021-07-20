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
	public BasicType kind;
	public Type(BasicType kind)
	{
		this.kind = kind;
	}

	@Override
	public String toString() {
		return "Type{" +
				"type=" + kind +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Type type1 = (Type) o;
		return kind == type1.kind;
	}

	@Override
	public int hashCode() {
		return Objects.hash(kind);
	}
}
