package ast.type;

enum TypeKind {
	BUILTIN_KIND,
	ARRAY_KIND,
//	TYPEALIAS_KIND;  Todo
}
public abstract class Type {
	private TypeKind kind;

	public Type(TypeKind kind) {
		this.kind = kind;
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

