package ast.type;

public abstract class Type {
	private TypeKind kind;
	public String name;
	public Type(TypeKind kind, String name) {
		this.kind = kind;
		this.name = name;
	}

	// underlying
	public abstract boolean equalType(Type type);
	public abstract boolean binAccept();
	public TypeKind getKind() {
		return kind;
	}

	// DEBUG
	@Override
	public String toString() {
		return "Type{" +
				"kind=" + kind +
				'}';
	}
}

