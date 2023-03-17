import java.io.File;

public class MelodyQueue {
	private Node front;
	private Node back;

//Add all of your methods to this class. DO NOT CHANGE ANY OTHER CLASSES!!!!
	
	public MelodyQueue() {
		front = null;							// initializes front and back to null
		back =  front;
	}
	
	public void enqueue(Object item) {
		if(isEmpty()) {							// checks to see if queue is empty
			front = new Node(item);
			back = front;
		}
		else {
			back = front;
			while(back.getNext() != null)		// traverses the queue with node 'back'
				back = back.getNext();
			back.setNext(new Node(item));		// points the last node in the queue to the new node
			back = back.getNext();				// points 'back' to the new node
		}
	}
	
	public Object dequeue() {
		if(isEmpty()) 							// checks to see if queue is empty
			System.out.println("Queue is empty");
		else {
			Node temp = front;					// retrieves the node at the front
			front = temp.getNext();				// points 'front' to the next node
			return temp;						// returns the node that was removed
		}
		return null;							// returns null if queue if empty
	}
	
	public Boolean isEmpty() {
		return (front == null);					// returns true or false
	}
	
	public double duration() {
		double songDur = 0;
		for(Node curr = front; curr != null; curr = curr.getNext()) // traversal 
			songDur += ((Note)curr.getItem()).getDuration();		// adds the duration of each note
		return songDur + timeRepeat();				// returns the duration of the song along with any repeated sections
	}
	
	public double timeRepeat() {
		double repDur = 0;
		Note note;
		
		Node curr = front;
		while(curr != null) {										
			note = (Note)curr.getItem();
			if(note.isRepeat()) {									// searches for the beginning of a repeated section(if there's one)
				do {
					repDur += ((Note)curr.getItem()).getDuration();	// adds the duration of each note in the repeated section
					curr = curr.getNext();
					note = (Note)curr.getItem();
				}while(!note.isRepeat());							// terminates do-while loop when the last note of the section is found
				repDur += ((Note)curr.getItem()).getDuration();		// adds the duration of the last note in the section
			}	
			curr = curr.getNext();
		}	
		return repDur;												// returns the duration
	}
	
	public int size() {
		int length = 0;
		for(Node curr = front; curr != null; curr = curr.getNext())
			length++;												// increments length after passing each node
		return length;												// returns the length
	}
	
	public String makeString() {
		String songInfo = "";
		for(Node curr = front; curr != null; curr = curr.getNext())
			songInfo += (((Note)curr.getItem()).toString() + "\n");	// concatenates the info of each Node to 'songInfo'
		return songInfo;											// returns the song info as a string
	}
	
	public void tempoChange(double tempo) {
		double newDur = 0;
		for(Node curr = front; curr != null; curr = curr.getNext()) {
			newDur = ((Note)curr.getItem()).getDuration() * tempo;	// gets a new duration
			((Note)curr.getItem()).setDuration(newDur);				// sets the new duration
		}
	}
	
	public void playRepeat() {
		Note note;
		for(Node curr = front; curr != null; curr = curr.getNext()) {
			note = ((Note)curr.getItem());							// gets each note in the song
			note.play();											// play each note
		}
	}
	
	public void appendMelody(MelodyQueue other) {
		this.back.setNext(other.front);		// points the last node of one song to the first node of the second song
		this.back = other.back;				// points 'back' to the last node of the second song
	}
	
	public MelodyQueue reverseMelody() {
		NoteStack stack = new NoteStack();
		for(Node curr = front; curr != null; curr = curr.getNext())	// traverses queue
			stack.push(curr.getItem());								// adds each node from queue to a stack
		
		MelodyQueue reverse = new MelodyQueue();
		for(Node curr = front; curr != null; curr = curr.getNext())	// traverses stack
			reverse.enqueue(stack.pop());							// pops each node from the stack and adds it to a new queue
		return reverse;												// returns new reversed queue
	}
	
	public void play() {
		MelodyQueue repeat = new MelodyQueue();
		Note note;
		
		Node curr = front;	
		while(curr != null) {
			note = ((Note)curr.getItem());								
			if(note.isRepeat()) {									// searches for the beginning of a repeated section(if there's one)
				do {
					repeat.enqueue(note);							// adds each note of the repeated section to a new queue
					note.play();									// plays each note
					curr = curr.getNext();
					note = ((Note)curr.getItem());
				}while(!note.isRepeat());							// terminates do-while loop when the last note of the section is found
				
				repeat.enqueue(note);								// adds last note of the section to queue
				note.play();
				repeat.playRepeat();								// plays repeated section
				curr = curr.getNext();
				note = ((Note)curr.getItem());
			}
			note.play();
			curr = curr.getNext();
		}
	}
	
}
