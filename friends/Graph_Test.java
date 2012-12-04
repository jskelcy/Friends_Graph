/*Written by Stewart Smith and Jake Skelci for CS 112 Fall 2012*/


package friends;

import java.io.*;
import java.util.*;
import java.util.Queue;




public class Graph_Test {
	static BufferedReader br1, br2;
	static Scanner stdin = new Scanner(System.in);

	public static final int STUDENTS = 1;
	public static final int SHORT = 2;
	public static final int CLIQUE = 3;
	public static final int CONNECT = 4;
	public static final int QUIT = 5;

	public static int getChoice()
			throws IOException {
		System.out.println();
		System.out.println(STUDENTS + ". Students at school");
		System.out.println(SHORT + ". Shortest intro chain");
		System.out.println(CLIQUE + ". Cliques at school");
		System.out.println(CONNECT + ". Connectors");
		System.out.println(QUIT + ". QUIT");
		System.out.print("\tEnter choice # => ");
		return (Integer.parseInt(br1.readLine()));
	}

	public static void main(String[] args) throws IOException {
		br1 = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("Enter the name of the friendship file => ");
		br2 = new BufferedReader(new FileReader(br1.readLine()));

		int count = Integer.parseInt(br2.readLine())+1;	//number of people
		String[] personBuk = new String[count-1];	

		for(int i=0; i<count-1; i++){			//put the people lines in an array
			String line= br2.readLine();
			personBuk[i]= line;
		}

		ArrayList<String> friendsBuk = new ArrayList<String>();
		String line = br2.readLine();
		friendsBuk.add(line);

		while(line != null){	//put the friends line in an array
			line = br2.readLine();
			friendsBuk.add(line);
		}

		Person[] zoo = new Person[count];
		zoo = build(personBuk, friendsBuk);	

		int choice = getChoice();
		while (choice != QUIT) {
			if (choice < 1 || choice > QUIT) {
				System.out.println("\tIncorrect choice " + choice);
			} else {
				switch (choice) {
				case STUDENTS: 
					System.out.print("Enter the name of the school => ");
					String school = stdin.nextLine();
					subgraph(school, zoo, true); 
					break;
				case SHORT: 
					System.out.print("Enter the name of the starting person => ");
					String start = stdin.nextLine();
					System.out.print("Enter the name of the target person => ");
					String target = stdin.nextLine();
					shortest(start, target, zoo); 
					break;
				case CLIQUE: 
					System.out.print("Enter the name of the school => ");
					String sc = stdin.nextLine();
					cliques(sc, zoo); 
					break;	
				case CONNECT: 
					connectors(zoo);
					break;    
				default: break;
				}
			}
			choice = getChoice();
			for(int i=0; i<zoo.length;i++){			//reset visited each time
				zoo[i].visited=false;
				zoo[i].back = -1;
			}
		}
	}
	public static hastable (person [] zoo){
	Hashtable hash = new HashTable <String, Ineteger> (zoo.size);
	}
	
	public static Person[] build(String[] people, ArrayList<String> friends){
		Person[] zoo = new Person[people.length];
		for(int i=0; i<people.length; i++){		// go through names and make them people
			String raw = people[i];
			String school=null;
			String name;

			name = raw.substring(0, raw.indexOf("|"));	//get name until '|'

			if(raw.charAt(raw.indexOf("|")+1)=='y'){				//attends school?
				school=raw.substring(raw.lastIndexOf('|')+1, raw.length());	//get the schoolname
			}
			Person body = new Person(name, school, null, false, -1,-1,-1,-1);	//create a new person
			zoo[i]= body;	//put him in the zoo
			hash.put(body.name, new Integer(i)) // this is new
		}

		for(int j=0; j<friends.size()-1; j++){		//go through the friends
			String raw = friends.get(j);
			String first = raw.substring(0, raw.indexOf('|'));		//the first friend name
			String second = raw.substring(raw.indexOf('|')+1);			//the second friend name
			Friendex firstDex = new Friendex(0,null);
			Friendex secDex = new Friendex(0, null);
			boolean firstMatched = false;
			boolean secMatched =false;

			for(int k=0; k<zoo.length; k++){				//look for first and second name

				if (zoo[k].name.equalsIgnoreCase(first)){	//firstname found
					firstDex.friendNum=k;					//friendNum = where the match is
					firstMatched=true;
				}
				if (zoo[k].name.equalsIgnoreCase(second)){
					secDex.friendNum=k;
					secMatched=true;
				}	
				if(firstMatched && secMatched){
					Friendex temp1 = zoo[firstDex.friendNum].front;		//keep the chain that is already attached
					Friendex temp2 = zoo[secDex.friendNum].front;

					zoo[firstDex.friendNum].front = secDex;				//update fronts
					zoo[secDex.friendNum].front = firstDex;

					firstDex.next = temp2;								//attach old chains
					secDex.next = temp1;
					break;
				}
			}
		}
		return zoo;		
	}
	public static ArrayList<Person> subgraph(String school, Person[] zoo, boolean printSub){

		ArrayList<Person> schoolZoo = new ArrayList<>();
		int schoolZoodex = 0;
		for(int i=0; i< zoo.length; i++){			//go through all people in zoo----linear	
			if(zoo[i].school != null && zoo[i].school.equalsIgnoreCase(school)){		//if their school matches
				zoo[i].schoolIndex=schoolZoodex;
				Person tempPer = new Person(zoo[i].name, zoo[i].school, zoo[i].front, false, i,schoolZoodex,-1,-1);	//copy zoo person
				schoolZoo.add(tempPer);				//add the person to the arrayList
				schoolZoodex++;
			}
		}
		for(int k=0; k<schoolZoo.size(); k++){		//go through schoolZoo
			Person student =  schoolZoo.get(k);		//person in the schoolZoo
			Friendex ptr = student.front;
			Friendex prev = null;
			while(ptr != null){		//go through the attached chain
				if(zoo[ptr.friendNum].schoolIndex <0){
					if(prev==null){			//first person doesn't attend target school
						student.front = ptr.next;
					}else{
						prev.next = ptr.next;		//cut chains to non-attending person
					}
				}else{
					ptr.friendNum = zoo[ptr.friendNum].schoolIndex;
					prev=ptr; 		//student attends--- don't modify
				}
				ptr=ptr.next;
			}
		}
		if(printSub){
			printSub(schoolZoo, zoo);
		}

		return schoolZoo;
	}

	public static void printSub(ArrayList<Person> schoolZoo, Person[] zoo){
		for(int i=0; i<schoolZoo.size(); i++){		//print out names and school--- i.e. "nick|y|rutgers"
			String name = schoolZoo.get(i).name;
			String school =schoolZoo.get(i).school;
			if(school != null){
				System.out.println(name + "|y|" + school);
			}else{
				System.out.println(name + "|n");
			}
		}
		//print out relationships--- i.e. "kaitlin|nick"
		for(int k=0; k<schoolZoo.size(); k++){			//run through vertical array
			String first = schoolZoo.get(k).name;
			Person test = zoo[schoolZoo.get(k).zooIndex];
			test.visited =true;		//refers back to zoo from schoolZoo index
			Friendex ptr = schoolZoo.get(k).front;
			while(ptr!=null){							//run through horizontal LL
				if(!(zoo[schoolZoo.get(k).zooIndex].visited && zoo[ptr.friendNum].visited)){		//if one isn't marked, print them out
					String second = zoo[ptr.friendNum].name;
					System.out.println(first+ "|" + second);
					zoo[ptr.friendNum].visited = true;
				}
				ptr=ptr.next;
			}
		}
	}

	public static void shortest(String start, String target, Person[] zoo)throws IOException {
		Person perStart = null;
		Stack<Person> printStack = new Stack<Person>();
		Queue<Integer> newQ = new LinkedList<Integer>();
		boolean complete = false;		//SS
		
		for(int i=0; i<zoo.length;i++){ // finds the start person in zoo
			if(zoo[i].name.equalsIgnoreCase(start)){
				perStart = zoo[i];
				zoo[i].zooIndex= i;
				newQ.add(i);  // add the start person to queue for BFS
				break;			// SS added--- person found break out of loop
			}
		}
		
		while (!newQ.isEmpty()){          //BFS queue build
			if(complete) break;		//SS
			zoo[newQ.peek()].visited = true;
			Person parent = zoo[newQ.poll()]; //dequeue front
			Friendex friendPtr = parent.front;
			while (friendPtr!=null){              //move horizontally through friends
				Person temp = zoo[friendPtr.friendNum];  //friend currently looking at
				if (!temp.visited){ //unvisited friends
					temp.zooIndex = friendPtr.friendNum;
					temp.back = parent.zooIndex;  //tells where the person came from
				}
				else{		//if friend has already been visited
					friendPtr = friendPtr.next;
					continue;
				} 
				if (target.equalsIgnoreCase(temp.name)){ // target is found
					Person parentPtr = temp;
					while (parentPtr.zooIndex != perStart.zooIndex){ //moves back and populates print stack
						printStack.push(parentPtr);
						parentPtr=zoo[parentPtr.back];
					}
					printStack.push(perStart);	//  SS----adds last starting person to printStack
					complete = true;	//SS
					break;	//  SS--- target found so break out of loop
				}else {newQ.add(friendPtr.friendNum); //if temp is not the target put on BFS queue
				friendPtr=friendPtr.next;			
				}	
			}
		}
		String answer = "";
		if(printStack.isEmpty()){	
			System.out.println("No possible path");
		}
		else{
			while (!printStack.isEmpty()){
				String name = printStack.pop().name;
				answer += name + "--";
			}
			answer = answer.substring(0,answer.length()-2);
			System.out.println(answer);
		}
	}


	public static void cliques(String school, Person[] zoo){

		ArrayList<Person> schoolZoo = subgraph(school, zoo, false);	//creates subgraph with school
		ArrayList<ArrayList<Person>> answer = new ArrayList<>(); 		

		for(int i=0; i< schoolZoo.size(); i++){		//go through vertical array
			Person vert = schoolZoo.get(i);
			Queue<Person> newQ = new LinkedList<Person>();
			ArrayList<Person> newClique = new ArrayList<>();
			boolean addClique = false;
			if(!vert.visited){		//the person has not been visited so he must be part of a new clique
				addClique =true;
				newQ.add(vert);
				while(!newQ.isEmpty()){
					Person justDQd = newQ.remove();
					justDQd.visited = true;				//maybe mixing up schoolZoo and zoo visited
					newClique.add(justDQd);
					Friendex ptr = justDQd.front;
					while(ptr != null){				//go through LL horizontally 
						if(!schoolZoo.get(ptr.friendNum).visited){
							newQ.add(schoolZoo.get(ptr.friendNum));
						}
						ptr = ptr.next;
					}
				}
			}
			if(addClique){
				answer.add(newClique);
			}
		}
		//Print out the cliques and members
		for(int p=0; p<answer.size(); p++){
			ArrayList <Person> clique = answer.get(p);
			System.out.println("Clique " + (p+1) +":");
			for(int k=0; k<clique.size(); k++){
				String name = clique.get(k).name;
				String sch = clique.get(k).school;
				System.out.println(name + "|" + "y" + "|" + sch);
			}
		}
	}
	
	public static void connectors(Person[] zoo){
		dfs(zoo);
	}
	
	private static void dfs(Person[] zoo){
		for(int i=0; i<zoo.length; i++){		//move vertically looking for new cliques
			Person dfsStart = zoo[i];
			
			if(!dfsStart.visited){
				Stack<Person> dfsStack = new Stack<Person>();
				dfsStack.push(dfsStart);
				dfsStart.visited=true;
				while(!dfsStack.isEmpty()){			//stack allows forward movement when push and backward when popped
					Person curr = dfsStack.peek();
					Friendex ptr = curr.front;
					while(ptr != null){						//moves horizontally through friends
						if(!zoo[ptr.friendNum].visited){	//get the first friend that isn't already visited
							Person currFriend = zoo[ptr.friendNum];
							currFriend.visited =true;
							dfsStack.push(currFriend);		//moving forward in graph
							break;
						}
						ptr = ptr.next;
					}
					if(ptr == null){		//no more friends
						Person backwards = dfsStack.pop();
					}
					
				}
			}
		}
		
	}
	
}	//end GraphTest class