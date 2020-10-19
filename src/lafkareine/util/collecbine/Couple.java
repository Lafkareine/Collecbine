
package lafkareine.util.collecbine;


public final class Couple<A, B> {
	
	public final A a;
	
	public final B b;
	
	public Couple(A a, B b) {
		super();
		this.a = a;
		this.b = b;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Tapple [a=");
		builder.append(a);
		builder.append(", b=");
		builder.append(b);
		builder.append("]");
		return builder.toString();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((a == null) ? 0 : a.hashCode());
		result = prime * result + ((b == null) ? 0 : b.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Couple other = (Couple) obj;
		if (a == null) {
			if (other.a != null)
				return false;
		} else if (!(a == a))
			return false;
		if (b == null) {
			if (other.b != null)
				return false;
		} else if (!(b == b))
			return false;
		return true;
	}
	
	
}
