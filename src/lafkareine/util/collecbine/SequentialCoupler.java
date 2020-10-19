
package lafkareine.util.collecbine;


import static java.util.Spliterator.*;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterators;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;


public class SequentialCoupler {

	public static <A, B> Stream<Couple<A, B>> stream(Collection<A> collectionA, Collection<B> collectionB, BiPredicate<A, B> test) {
		return StreamSupport.stream(Spliterators.spliterator(iterable(collectionA, collectionB, test).iterator(), collectionA.size(), SIZED | IMMUTABLE | NONNULL), false);
	}

	public static <A, B> Iterable<Couple<A, B>> iterable(Collection<A> collectionA, Collection<B> collectionB, BiPredicate<A, B> test) {
		return new SequentialCoupleIterator<>(collectionA, collectionB, test);
	}

	public static <A, B> List<Couple<A, B>> list(Collection<A> collectionA, Collection<B> collectionB, BiPredicate<A, B> test) {
		return stream(collectionA, collectionB, test).collect(Collectors.toList());
	}

	public static <A, B> Couple<A, B>[] array(Collection<A> collectionA, Collection<B> collectionB, BiPredicate<A, B> test) {
		return stream(collectionA, collectionB, test).toArray(i -> new Couple[i]);
	}

	private static final class SequentialCoupleIterator<A, B> implements Iterable<Couple<A, B>>, Iterator<Couple<A, B>> {

		private final Iterator<A> iteratorA;

		private final Iterator<B> iteratorB;

		private final BiPredicate<A, B> test;

		private Couple<A, B> next;

		private B targetB;

		public SequentialCoupleIterator(Collection<A> collectionA, Collection<B> collectionB, BiPredicate<A, B> test) {
			super();
			this.iteratorA = collectionA.iterator();
			this.iteratorB = collectionB.iterator();
			this.test = test;
			if (iteratorB.hasNext()) {
				targetB = iteratorB.next();
			} else {
				throw new IllegalArgumentException("コレクションBには要素のあるコレクションを渡してください");
			}
		}

		@Override
		public boolean hasNext() {
			// TODO 自動生成されたメソッド・スタブ
			return iteratorA.hasNext();
		}

		@Override
		public Couple<A, B> next() {
			// TODO 自動生成されたメソッド・スタブ
			final A nextA = iteratorA.next();
			while (true) {
				if (test.test(nextA, targetB)) {
					return new Couple(nextA, targetB);
				}
				if (iteratorB.hasNext()) {
					final B targetB = iteratorB.next();
				} else {
					throw new IndexOutOfBoundsException("ペアが見つからないままコレクションBが尽きてしまいました");

				}
			}
		}

		@Override
		public Iterator<Couple<A, B>> iterator() {
			// TODO 自動生成されたメソッド・スタブ
			return this;
		}
	}
}
