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
		if(front == null)							// checks to see if queue is empty
			front = new Node(item);
		else {
			back = front;
			while(back.getNext() != null)			// traverses the queue with node 'back'
				back = back.getNext();
			back.setNext(new Node(item));			// points the 'back' to the new node
		}
	}
	
	public Object dequeue() {
		if(front == null) 							// checks to see if queue is empty
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
		return songDuration;
	}
	
	public double timeRepeat() {
		return 0;
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
		
	}
	
	public void playRepeat() {
		
	}
	
	public void appendMelody(MelodyQueue other) {
		
	}
	
	public MelodyQueue reverseMelody() {
		return null;
	}
	
	public void play() {
		
	}
	
}
