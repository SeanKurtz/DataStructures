/**
 * Fold shifting, coded as stringToInt will be the the preprocessing algorithm. The client will specify the
 * maximum number of nodes to be stored in the structure length. This will be adjusted by the loading factor
 * and sent into the method fourKPlus3 to determine a 4k + 3 prime to use for the size of the primary storage area, N.
 * We will use be using the Linear Quotient Collision algorithm for dealing with collisions. This is largely based off the textbook.
 * 
 * 
 * @author Sean Kurtz
 * @version 1.0
 */
public class LQHashed {
	// The number of locations allocated to the primary storage array.
	private int N;
	
	// A default 4k + 3 prime.
	private int defaultQuotient = 9967;
	
	// Max number of nodes to store / Number of storage locations.
	private double loadingFactor = 0.75;
	
	// Maximum number of nodes to be stored in the structure. VERY DIFFERENT from N.
	private int size;
	
	// Dummy node v2. This indicates that there was once a node, but now it has been deleted. Helps prevent problems with delete algorithm.
	private String deleted;
	
	// Primary storage array
	private String[] data;
	
	
	
	/**
	 * Constructs a LQHashed data structure that can hold length nodes.
	 * @param length= The number of nodes to be stored in the data structure.
	 */
	public LQHashed(int length) {
		
		// 33% larger than storage array.
		int pct = (int)((1.0 / loadingFactor - 1) * 100.0);
		
		// Find the optimal size of the array, this will be a 4k + 3 prime number to minimize collisions.
		N = fourKPlus3(length, pct);
		
		// Initialize the primary storage array with optimal amount of space given by fourKPlus3 algorithm.
		data = new String[N];
		
		// Dummy node for deletion algorithm.
		deleted = new String("");
		
		// The given max number of nodes to be stored in the structure.
		size = length;
		
		// Everything else is null.
		for (int i = 0; i < N; i++) {
			data[i] = null;
		}
		
	}
	/**
	 * Attempts to insert newListing into the structure.
	 * @param The new listing to insert into the structure.
	 * @return
	 */
	public boolean insert(String newListing) {
		// Whether we have had an error.
		boolean noError;
		// No, we haven't 
		boolean hit = false;
		int pass, q, offset, ip;
		// Pre-process the key
		int pk = stringToInt(newListing);
		// If the structure is not full to loading factor
		if ((((double) size) / N) < loadingFactor) {
			pass = 0;
			q = pk / N;
			offset = q;
			// Division hashing.
			ip = pk % N;
			if (q % N == 0) {
				offset = defaultQuotient;
			}
			while (pass < N) {
				if (data[ip] == null || data[ip] == deleted) {
					hit = true;
					break;
				}
				ip = (ip + offset) % N;
				pass++;
			}
			if (hit == true) {
				data[ip] = newListing;
				size++;
				noError = true;
				return noError;
			}
			else {
				noError = false;
				return noError;
			}
		}
		// Otherwise, the structure is full to loading factor.
		else {
			// Return false since no insert was performed.
			noError = false;
			return noError;
		}
	}
	/**
	 * Retrieves the node with key-field given by targetKey from the structure.
	 * @param targetKey = The key-field of the node we wish to retrieve.
	 * @return The Node whose key-field matches the targetKey, null if we couldn't find it.
	 */
	public String fetch(String targetKey) {
		boolean hit = false;
		int pass, q, offset, ip;
		// Convert the non-numeric key into an integer pseudo-key.
		int pk = stringToInt(targetKey);
		pass = 0;
		// Initially, the quotient is just the pseudo-key divided by the number of locations in data storage area.
		q = pk / N;
		// Our offset will be the quotient.
		offset = q;
		// Division hashing.
		ip = pk % N;
		// q / N has remainder 0
		if (q % N == 0) {
			// Then the offset will instead be the default 4k + 3 prime (other than N).
			offset = defaultQuotient;
		}
		while (pass  < N) {
			// If the node is not in the structure
			if (data[ip] == null) {
				break;
			}
			// If the key at ip is the same as the target key
			if (data[ip].compareTo(targetKey) == 0) {
				// We found the node
				hit = true;
				break;
			}
			// If we haven't found the key at the node, and we haven't found null, then a collision has occurred.
			ip = (ip + offset) % N;
			// We are going to have to loop again so increment number of passes.
			pass++;
		}
		// If we found the node that has the target key
		if (hit == true) {
			// Return the node.
			return data[ip];
		}
		// Otherwise
		else {
			// Return null.
			return null;
		}
	}
	/**
	 * Deletes the target item and inserts a new item to replace it.
	 * @param targetKey = The listing we wish to update
	 * @param newListing = The listing to replace the targeted listing with
	 * @return True if we deleted the old listing and inserted the new one, false otherwise.
	 */
	public boolean update(String targetKey, String newListing) {
		// If we cannot delete
		if (delete(targetKey) == false) {
			// We cannot update so return false
			return false;
		}
		// If we cannot insert the new listing
		else if(insert(newListing) == false) {
			// We cannot update so return false
			return false;
		}
		// Return true since we successfully updated
		return true;
			
	}
	/**
	 * Deletes the node given by the targetKey from the structure.
	 * @param targetKey = The key of the node we wish to delete
	 * @return True if we deleted the node, false otherwise.
	 */
	public boolean delete(String targetKey) {
		// Whether or not we have had an error.
		boolean noError;
		// Not hits yet.
		boolean hit = false;
		// The pass number, quotient, offset(either quotient or default-quotient), and the index.
		int pass, q, offset, ip;
		// Preprocess the key into a pseudo-key.
		int pk = stringToInt(targetKey);
		// We have not done any passes yet.
		pass = 0;
		// The quotient.
		q = pk / N;
		// The offset is just the quotient.
		offset = q;
		// division hashing
		ip = pk % N;
		// If q / N has remainder 0
		if (q % N == 0) {
			// We use our default quotient instead
			offset = defaultQuotient;
		}
		// While we haven't checked all the locations
		while (pass < N) {
			// If the node at the probing index is null
			if (data[ip] == null) {
				break;
			}
			// If we found the node whose key matches the targetKey.
			if (data[ip].compareTo(targetKey) == 0) {
				// We have a HIT !
				hit = true;
				break;
			}
			// If the node we are probing wasn't null, and it wasn't the targetKey, then we have had a collision. Adjust the probing index by our offset.
			ip = (ip + offset) % N;
			// We are going to have to probe again.
			pass++;
		}
		// If we found our node
		if (hit == true) {
			// Set the node to our deleted node instead of null.
			data[ip] = deleted;
			// Decrement the number of nodes stored.
			size--;
			// We did not have an error so return true.
			noError = true;
			return noError;
		}
		// Otherwise, if we went through all the passes and still didn't find the node
		else {
			// Return false since we did not delete the node.
			noError = false;
			return noError;
		}
	}
	/**
	 * Calculates the next highest 4k + 3 prime given a pct determined from loadingFactor and above a given integer n, 
	 * which will be the actual number of nodes we wish to store. Tests higher and higher odd candidate integers to determine if they are prime,
	 * and then if they are 4k + 3 prime.
	 * @param n
	 * @param pct
	 * @return
	 */
	private static int fourKPlus3(int n, int pct) {
		
		// We haven't found a 4k + 3 prime yet.
		boolean fkp3 = false;
		// We haven't even found a prime yet.
		boolean aPrime = false;
		// Our prime number that we are looking for.
		int prime;
		// The highest divisor of whatever number we are checking.
		int highDivisor;
		// 
		int divisor;
		double pctd = pct;
		prime = (int)(n * (1.0 + (pctd / 100.0)));
		
		// If prime is even, change it to odd.
		if (prime % 2 == 0) {	
			prime = prime + 1;
		}
		
		// While its not a 4k + 3 prime
		while(!fkp3) {	
			// While its not a prime at all
			while (!aPrime) {
				highDivisor = (int)(Math.sqrt(prime) + 0.5);
				// For int d is the highest divisor all the way down to the lowest possible divisor which is 2
				for (divisor = highDivisor; divisor > 1; divisor--) {
					// If the prime is divisible by d
					if (prime % divisor == 0) {
						// We have found a divisor
						break;
					}
				}
				// If the divisor wasn't 1, then the number cannot be a prime number by the definition of prime number.
				if (divisor != 1) {
					// Change prime to the next odd number.
					prime = prime + 2;
				}
				// Otherwise, the divisor is one
				else {
					// Thus we have found a prime number.
					aPrime = true;
				}
			} // End of searching for a prime number.
			
			// If three less than the prime number is evenly divisible by 4
			if ((prime - 3) % 4 == 0) {
				// We have found a 4k + 3 prime number.
				fkp3 = true;
			}
			// Otherwise, we do not have a 4k + 3 prime number
			else {
				// Advance to the next odd number
				prime = prime + 2;
				// We no longer know if the number is prime
				aPrime = false;
				
			}
		} // Completed our search for a 4k + 3 prime number.
		
		// Return the number we have found.
		return prime;
	}
	/**
	 * Performs a fold-shifting pre-processing algorithm on a string into a 32 bit integer pseudo-key.
	 * This is both whitespace and case sensitive. Sometimes different keys WILL produce the same pseudo-key, so we will need to deal with collisions.
	 * @param key = the key we wish to convert into a pseudo-key.
	 * @return A non-negative integer pseudo-key.
	 */
	private static int stringToInt(String key) {
		// Our pseudo-key is initialized to the non-negative integer 0.
		int pseudoKey = 0;
		int charNum = 1;
		// character we are looking at.
		int cn = 0;
		// Convert our string into a character array.
		char c[] = key.toCharArray();
		// The grouping that we will fold about.
		int grouping = 0;
		// While there are still more characters in the key
		while (cn < key.length()) {
			// Shift left 8. This advances grouping 4 characters.
			grouping = grouping << 8;
			grouping = grouping + c[cn];
			cn = cn + 1;
			// If four characters have been processed or we have processed all the characters.
			if (charNum == 4 || cn == key.length()) {
				// Add the grouping to the pseudo key.
				pseudoKey = pseudoKey + grouping;
				// Reset n and the grouping.
				charNum = 0;
				grouping = 0;
			}
			charNum++;
		}
		// Return the absolute value of the calculated pseudo-key.
		return Math.abs(pseudoKey);
	}
	/**
	 * Outputs the nodes in the structure to the console.
	 */
	public void output() {
		// For every location in the primary storage area
		for (int i = 0; i < N; i++) {
			// If the data at the location is non null and it is not the dummy node
			if (data[i] != null && data[i] != deleted) {
				// Output the node
				System.out.println(data[i]);
			}
		}
	}
}
