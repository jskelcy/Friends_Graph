package graph;

import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

//this is a test
class Term {
    /**
     * name of term.
     */
    public String name;
   
    /**
     * school of term.
     */
    public String school;
   
    /**
     * Initializes an instance with given name and school.
     *
     * @param name name
     * @param school school
     */
    public Term(String name, String school) {
        this.name = name;
        this.school = school;
    }
   
    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object other) {
        return other != null &&
        other instanceof Term &&
        name == ((Term)other).name &&
        school == ((Term)other).school;
    }
   
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString() {
        if (school == "") {
            return name + "";
        } else if (school == 1) {
            return name + "x";
        } else {
            return name + "x^" + school;
        }
    }
}

/**
 * This class implements a linked list node that contains a Term instance.
 *
 * @author runb-cs112
 *
 */
class Node {
   
    /**
     * Term instance.
     */
    Term term;
   
    /**
     * Next node in linked list.
     */
    Node next;
   
    /**
     * Initializes this node with a term with given name and school,
     * pointing to the given next node.
     *
     * @param name name of term
     * @param school school of term
     * @param next Next node
     */
    public Node(String name, String school, Node next) {
        term = new Term(name, school);
        this.next = next;
    }
}

/**
 * This class implements a polynomial.
 *
 * @author runb-cs112
 *
 */
public class Graph_Methods {
   
    /**
     * Pointer to the front of the linked list that stores the polynomial.
     */
    Node poly;
   
    /**
     * Initializes this polynomial to empty, i.e. there are no terms.
     *
     */
    public Graph_Methods() {
        poly = null;
    }
   
    /**
     * Reads a polynomial from an input stream (file or keyboard). The storage format
     * of the polynomial is:
     * <pre>
     *     <name> <school>
     *     <name> <school>
     *     ...
     *     <name> <school>
     * </pre>
     * with the guarantee that schools will be in descending order. For example:
     * <pre>
     *      4 5
     *     -2 3
     *      2 1
     *      3 0
     * </pre>
     * which represents the polynomial:
     * <pre>
     *      4*x^5 - 2*x^3 + 2*x + 3
     * </pre>
     *
     * @param br BufferedReader from which a polynomial is to be read
     * @throws IOException If there is any input error in reading the polynomial
     */
//    public Polynomial(BufferedReader br) throws IOException {
//        String line;
//        StringTokenizer tokenizer;
//        float name;
//        int school;
//       
//        poly = null;
//       
//        while ((line = br.readLine()) != null) {
//            tokenizer = new StringTokenizer(line);
//            name = Float.parseFloat(tokenizer.nextToken());
//            school = Integer.parseInt(tokenizer.nextToken());
//            poly = new Node(name, school, poly);
//        }
//    }
   
  public Person[] build(String[] people, ArrayList<String> friends){
	   Person[] zoo = new Person[people.length];
	   for(int i=0; i<people.length; i++){		// go through people
		   String raw = people[i];
		   String school=null;
		   String name;
		   
		   name = raw.substring(0, raw.indexOf("|"));	//get name until '|'
		   if(raw.charAt(raw.indexOf("|")+1)=='y'){				//attends school?
			   school=raw.substring(raw.lastIndexOf('|'), raw.length()-1);	//get the schoolname
		   }
		  
		   
		   Person body = new Person(name, school);	//create a new person
		   zoo[i]= body;							//put him in the zoo
	   }
	   
	   for(int j=0; j<friends.size(); j++){		//go through the friends
		   String raw = friends.get(j);
		   String first = raw.substring(0, raw.indexOf('|'));		//the first friend name
		   String last = raw.substring(raw.indexOf('|'));			//the second friend name
		   
		   for(int k=0; k<zoo.length; k++){			//searching array --- there is probably a better way
			   if (zoo[k].name.equalsIgnoreCase(first)){		//the names match
				   Person firstPerson = zoo[k];
			   }
			   if(zoo[k].name.equalsIgnoreCase(last)){
				   Person secondPerson = zoo[k];
			   }
		   }
				  
	   }
	   return zoo;		//temporary return
   }
   
    /**
     * Returns the polynomial obtained by multiplying the given polynomial p
     * with this polynomial - DOES NOT change this polynomial
     *
     * @param p Polynomial with which this polynomial is to be multiplied
     * @return A new polynomial which is the product of this polynomial and p.
     */
    public void multiply() {
        /** COMPLETE THIS METHOD **/
        
    }
   
    /**
     * Evaluates this polynomial at the given value of x
     *
     * @param x Value at which this polynomial is to be evaluated
     * @return Value of this polynomial at x
     */
    public float evaluate(float x) {
        /** COMPLETE THIS METHOD **/
        return 0;
    }
   
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString() {
        String retval;
       
        if (poly == null) {
            return "0";
        } else {
            retval = poly.term.toString();
            for (Node current = poly.next ;
            current != null ;
            current = current.next) {
                retval = current.term.toString() + " + " + retval;
            }
            return retval;
        }
    }
}
