
package lafkareine.util.collecbine.old;


import static java.util.Spliterator.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Spliterators;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;


public class SequentialAttributer {

	public static <O, A> Stream<Attribute<O, A>> stream(Collection<O> objects, Collection<A> attributes, BiPredicate<O, A> test) {
		return objects.stream().map(o -> new Attribute<>(o, attributes.stream().filter(a -> test.test(o, a)).collect(Collectors.toList())));
	}

	public static <O, A> Stream<Attribute<O, A>> stream2(Collection<O> objects, Collection<A> attributes, BiPredicate<O, A> test) {
		return StreamSupport.stream(Spliterators.spliterator(iterable(objects, attributes, test).iterator(), objects.size(), SIZED | IMMUTABLE | NONNULL), false);
	}

	public static <O, A> Iterable<Attribute<O, A>> iterable(Collection<O> objects, Collection<A> attributes, BiPredicate<O, A> test) {
		return new SequentialAttributeIterator(objects, attributes, test);
	}

	public static <O, A> List<Attribute<O, A>> list(Collection<O> objects, Collection<A> attributes, BiPredicate<O, A> test) {
		return stream(objects, attributes, test).collect(Collectors.toList());
	}

	public static <O, A> Attribute<O, A>[] array(Collection<O> objects, Collection<A> attributes, BiPredicate<O, A> test) {
		return stream(objects, attributes, test).toArray(i -> new Attribute[i]);
	}

	private static final class SequentialAttributeIterator<O, A> implements Iterable<Attribute<O, A>>, Iterator<Attribute<O, A>> {

		private final Iterator<O> iteratorO;

		private final Iterator<A> iteratorA;

		private final BiPredicate<O, A> test;

		private A next;

		private int state = 0;

		private boolean isRemain = true;

		public SequentialAttributeIterator(Collection<O> collectionO, Collection<A> collectionA, BiPredicate<O, A> test) {
			super();
			this.iteratorO = collectionO.iterator();
			this.iteratorA = collectionA.iterator();
			this.test = test;
		}

		@Override
		public boolean hasNext() {
			// TODO 自動生成されたメソッド・スタブ
			return iteratorO.hasNext();
		}

		@Override
		public Attribute<O, A> next() {
			// TODO 自動生成されたメソッド・スタブ
			O object = iteratorO.next();
			if (isRemain) {
				final LinkedList<A> workspace = new LinkedList<A>();
				while (test.test(object, next)) {
					workspace.add(next);

					if (iteratorA.hasNext()) {
						next = iteratorA.next();
					} else {
						isRemain = false;
						break;
					}
				}

				List<A> attributes = new ArrayList<>(workspace);
				return new Attribute<O, A>(object, attributes);
			} else {
				return new Attribute<O, A>(object, new ArrayList<>());
			}
		}

		@Override
		public Iterator<Attribute<O, A>> iterator() {
			// TODO 自動生成されたメソッド・スタブ

			return null;
		}

	}
}
