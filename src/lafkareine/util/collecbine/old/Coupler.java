
package lafkareine.util.collecbine.old;


import static java.util.Spliterator.*;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterators;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;


/** Joinに近い動作を提供します */
public class Coupler {

	private Coupler() {
		// TODO 自動生成されたコンストラクター・スタブ
	}

	public static <A, B> Stream<Couple<A, B>> stream(Collection<A> collectionA, Collection<B> collectionB, BiPredicate<A, B> test) {
		return StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterable(collectionA, collectionB, test).iterator(), IMMUTABLE | NONNULL), false);
	}

	public static <A, B> Iterable<Couple<A, B>> iterable(Collection<A> collectionA, Collection<B> collectionB, BiPredicate<A, B> test) {
		return new CoupleIterator<>(collectionA, collectionB, test);
	}

	public static <A, B> List<Couple<A, B>> list(Collection<A> collectionA, Collection<B> collectionB, BiPredicate<A, B> test) {
		return stream(collectionA, collectionB, test).collect(Collectors.toList());
	}

	public static <A, B> Couple<A, B>[] array(Collection<A> collectionA, Collection<B> collectionB, BiPredicate<A, B> test) {
		return stream(collectionA, collectionB, test).toArray(i -> new Couple[i]);
	}

	private static final class CoupleIterator<A, B> implements Iterable<Couple<A, B>>, Iterator<Couple<A, B>> {

		private final Iterator<A> iteratorA;

		private final Collection<B> collectionB;

		private Iterator<B> iteratorB;

		private final BiPredicate<A, B> test;

		private Couple<A, B> next;

		private A targetA;

		private boolean state;

		public CoupleIterator(Collection<A> collectionA, Collection<B> collectionB, BiPredicate<A, B> test) {
			super();
			this.iteratorA = collectionA.iterator();
			this.collectionB = collectionB;
			this.test = test;
			state = true;
		}

		@Override
		public boolean hasNext() {
			// TODO 自動生成されたメソッド・スタブ
			while (true) {
				if (state) {
					if (iteratorA.hasNext()) {
						targetA = iteratorA.next();
						iteratorB = collectionB.iterator();
						state = false;
					} else {
						return false;
					}
					while (iteratorB.hasNext()) {
						B targetB = iteratorB.next();
						if (test.test(targetA, targetB)) {
							next = new Couple<A, B>(targetA, targetB);
							return true;
						}
					}
					state = true;
				}
			}
		}

		@Override
		public Couple<A, B> next() {
			// TODO 自動生成されたメソッド・スタブ
			return next;
		}

		@Override
		public Iterator<Couple<A, B>> iterator() {
			// TODO 自動生成されたメソッド・スタブ
			return this;
		}
	}
}
