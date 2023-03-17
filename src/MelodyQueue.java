import java.io.File;

public class MelodyQueue {
	private Node front;
	private Node back;

//Add all of your methods to this class. DO NOT CHANGE ANY OTHER CLASSES!!!!
	
	public MelodyQueue() {
		front = null;
		back =  front;
	}
	
	public void enqueue(Object item) {
		if(isEmpty()) {								// checks to see if queue is empty
			front = new Node(item);
			back = front;
		}
		else {
			back = front;
			while(back.getNext() != null)			// traverses the queue with node 'back'
				back = back.getNext();
			back.setNext(new Node(item));			// points the last node in the queue to the new node
			back = back.getNext();					// points 'back' to the new node
		}
	}
	
	public Object dequeue() {
		if(isEmpty()) 								// checks to see if queue is empty
			System.out.println("Queue is empty");
		else {
			Node temp = front;						// retrieves the node at the front
			front = temp.getNext();					// point 'front' to the next node
			return temp;							// returns the node that was removed
		}
		return null;								// returns null if queue if empty
	}
	
	public Boolean isEmpty() {
		return (front == null);						// returns true or false
	}
	
	public double duration() {
		double songDuration = 0;
		for(Node curr = front; curr != null; curr = curr.getNext())
			songDuration += ((Note)curr.getItem()).getDuration();
		return songDuration + timeRepeat();
	}
	
	public double timeRepeat() {
		double repeatDuration = 0;
		Node curr = front;
		Note note = (Note)curr.getItem();
		
		while(curr.getNext() != null) {
			if(note.isRepeat()) {
				do {
					repeatDuration += ((Note)curr.getItem()).getDuration();
					curr = curr.getNext();
					note = (Note)curr.getItem();
				}while(!note.isRepeat());
				repeatDuration += ((Note)curr.getItem()).getDuration();
			}	
			curr = curr.getNext();
			note = (Note)curr.getItem();
		}	
		return repeatDuration;
	}
	
	public int size() {
		int length = 0;
		for(Node curr = front; curr != null; curr = curr.getNext())
			length++;
		return length;
	}
	
	public String makeString() {
		String songNotes = "";
		for(Node curr = front; curr != null; curr = curr.getNext()) 
			songNotes += (((Note)curr.getItem()).toString() + "\n");
		return songNotes;
	}
	
	public void tempoChange(double tempo) {
		double newDuration = 0;
		for(Node curr = front; curr != null; curr = curr.getNext()) {
			newDuration = ((Note)curr.getItem()).getDuration() * tempo;
			((Note)curr.getItem()).setDuration(newDuration);
		}
	}
	
	public void playRepeat() {
		for(Node curr = front; curr != null; curr = curr.getNext()) {
			Note note = ((Note)curr.getItem());
			note.play();
		}
	}
	
	public void appendMelody(MelodyQueue other) {
		this.back.setNext(other.front);
		this.back = other.back;
	}
	
	public MelodyQueue reverseMelody() {
		NoteStack stack = new NoteStack();
		for(Node curr = front; curr != null; curr = curr.getNext())
			stack.push(curr.getItem());
		
		MelodyQueue reverse = new MelodyQueue();
		for(Node curr = front; curr != null; curr = curr.getNext())
			reverse.enqueue(stack.pop());
		return reverse;
	}
	
	public void play() {
		Node curr = front;
		boolean isRepeated;
		MelodyQueue temp = new MelodyQueue();
		while(curr != null) {
			Note note = ((Note)curr.getItem());
			isRepeated = note.isRepeat();
			if(isRepeated) {
				do {
					temp.enqueue(note);
					note.play();
					curr = curr.getNext();
					note = ((Note)curr.getItem());
					isRepeated = note.isRepeat();
				}while(!isRepeated);
				
				temp.enqueue(note);
				note.play();
				temp.playRepeat();
				curr = curr.getNext();
			}
			else {
				note.play();
				curr = curr.getNext();
			}
		}
	}
	
}
