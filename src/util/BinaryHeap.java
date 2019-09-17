package util;

import java.util.NoSuchElementException;

public class BinaryHeap {
	
	private int size;
	
	private Tile[] data;
	
	public BinaryHeap(int size) {
		data = new Tile[size];
	}
	
	public boolean isEmpty() {
		return size == 0;
	}
	
	public boolean isFull() {
		return size == data.length;
	}
	
	/**
	 * Clears the data
	 */
	public void clear() {
		size = 0;
	}
	
	/**
	 * Gets the parent index.
	 * @param index
	 * @return
	 */
	public int getParentIndex(int index) {
		return (index - 1) / 2;
	}
	
	/**
	 * Gets the kth child.
	 * @param index
	 * @param k
	 * @return
	 */
	public int getKthChild(int index, int k) {
		return 2 * index + k;
	}
	
	public void add(Tile data, boolean greater) {
		if (isFull()) {
			throw new StackOverflowError();
		}
		this.data[size++] = data;
		heapifyUp(size - 1, greater);
	}
	
	public Tile minElement() {
		if (isEmpty()) {
			Tile highF = new Tile(1,1);
			highF.f = Double.MAX_VALUE;
			return highF;
		}
		return data[0];
	}
	
	public Tile delete(int index, boolean greater) {
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		Tile item = data[index];
		data[index] = data[size - 1];
		size --;
		heapifyDown(index, greater);
		return item;
	}
	
	private void heapifyUp(int index, boolean greater) {
		Tile temp = data[index];			
		int parentIndex;
		while (index > 0 && (temp.f < data[(parentIndex = getParentIndex(index))].f || 
				data[parentIndex].f == temp.f && greater ? temp.g > data[parentIndex].g : temp.g < data[parentIndex].g)) {
			
			data[index] = data[parentIndex];
			index = parentIndex;
		}
		data[index] = temp;
	}
	
	private void heapifyDown(int index, boolean greater) {
		Tile temp = data[index];
		int child;
		while (getKthChild(index, 1) < size) {
			child = getMinChild(index);
			if (data[child].f < temp.f) {
				data[index] = data[child];
			}  else if (data[child].f == temp.f) {
				if (greater && data[child].g > temp.g) {
					data[index] = data[child];
				} else if (!greater && data[child].g < temp.g) {
					data[index] = data[child];
				}
			}  else {
			
			
				break;
			}
			index = child;
		}
		data[index] = temp;
	}
	
	private int getMinChild(int index) {
		int kth = getKthChild(index, 1);
		int k = 2;
		int position = getKthChild(index, k);
		while((k <= 2) && (position < size)) {
			if (data[position].f < data[kth].f) {
				kth = position;
			}
			position = getKthChild(index, k++);
		}
		return kth;
	}
	
	public int getIndex(Tile check) {
		for (int i = 0; i < size; i ++) { 
			if (check == data[i]) {
				return i;
			}
		}
		return -1;
	}
}
