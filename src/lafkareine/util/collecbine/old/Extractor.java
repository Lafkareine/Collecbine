package lafkareine.util.collecbine.old;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Stream;

public class Extractor<T,R> implements Iterable{

	private static Iterator dammy = new Iterator() {
		@Override
		public boolean hasNext() {
			return false;
		}

		@Override
		public Object next() {
			return null;
		}
	};


	private Collection<? extends T> root;
	private Function<? super T,? extends Collection<R>> navigator;

	@Override
	public Iterator iterator() {
		return null;
	}

	private static final class ExtractIterator<T,R> implements Iterator<Couple<T,R>> {

		private Iterator<R> currentR;
		private T currentT;
		private final Iterator<T> base;
		private final Function<? super T,? extends Collection<R>> navigator;

		public ExtractIterator(Collection<T> base, Function<? super T, ? extends Collection<R>> navigator) {
			this.base = base.iterator();
			this.navigator = navigator;
			currentT = null;
			currentR = dammy;
		}

		@Override
		public boolean hasNext() {
			if(currentR.hasNext()){
				return true;
			}else{
				if(base.hasNext()){
					currentT = base.next();
					currentR = navigator.apply(currentT).iterator();
					return hasNext();
				}else{
					return false;
				}
			}
		}

		@Override
		public Couple<T, R> next() {
			return new Couple<>(currentT,currentR.next());
		}
	}

	public List list(){
		int size = 0;
		for(T e:root){
			size += navigator.apply(e).size();
		}
		ArrayList<R> list = new ArrayList<>(size);
		for(T e:root){
			list.addAll(navigator.apply(e));
		}
		return Collections.unmodifiableList(list);
	}

	public static <T,R> List<R> list(Collection<? extends T> root, Function<? super T,? extends Collection<R>> navigator){
		int i = 0;
		for(T x:root){
			navigator.apply(x).size();
		}
		ArrayList<R> r_list = new ArrayList<>(i);
		for(T x:root){
			r_list.addAll(navigator.apply(x));
		}
		return r_list;
	}

	public Stream<T> stream(){}
}
